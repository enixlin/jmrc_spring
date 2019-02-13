package com.enixlin.jmrc.policydocument;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.ASObject;
import flex.messaging.io.amf.Amf3Input;

/**
 * 外汇管理政策数据文件读写类<br>
 * 这个类的
 * 
 * @author linzhenhuan
 *
 */
public class DataFileAccess {

	public String DataVer;// 数据文件包的版本号
	public java.util.ArrayList<PolicyFileEntity> PolicyFileRecordArr = new java.util.ArrayList<>();// 所有的政策文件，文件的正文内容是去HTML标记的
	public Map<String, String> StructionMap;// 所有的发文单位
	public Map<String, ASObject> FileSubTypeMap; // 所有文件的分类列表
	public java.util.ArrayList<HtmlFile> HtmlFileArr = new java.util.ArrayList<>(); // 数据文件的html编码文件列表，以文件的Did为索引


	public void createDBTable(String Host, String Port, String DBName, String user, String Password) {
		String url = this.createDBUrl(Host, Port, DBName);
		try {
			Connection cnn = DriverManager.getConnection(url, user, Password);
			PreparedStatement ps;
			String sql_drop = "DROP TABLE IF EXISTS `policydocument_list`";
			ps = cnn.prepareStatement(sql_drop);
			ps.execute();
			
			sql_drop = "DROP TABLE IF EXISTS `policydocument_html`";
			ps = cnn.prepareStatement(sql_drop);
			ps.execute();
			sql_drop = "DROP TABLE IF EXISTS `policydocument_filetype`";
			ps = cnn.prepareStatement(sql_drop);
			ps.execute();
			sql_drop = "DROP TABLE IF EXISTS `policydocument_struction`";
			ps = cnn.prepareStatement(sql_drop);
			ps.execute();
			
			
			
			String sql = "CREATE TABLE `policydocument_list` (\r\n" + 
					"  `wordsCount` int(11) NOT NULL,\r\n" + 
					"  `docNum` varchar(255) DEFAULT NULL,\r\n" + 
					"  `link` varchar(255) DEFAULT NULL,\r\n" + 
					"  `sTitle` varchar(255) DEFAULT NULL,\r\n" + 
					"  `title` varchar(255) NOT NULL,\r\n" + 
					"  `postMechanism` varchar(255) DEFAULT NULL,\r\n" + 
					"  `tid` int(11) DEFAULT NULL,\r\n" + 
					"  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,\r\n" + 
					"  `postTime` double DEFAULT NULL,\r\n" + 
					"  `effTime` double DEFAULT NULL,\r\n" + 
					"  `state` int(11) DEFAULT NULL,\r\n" + 
					"  `dId` int(11) NOT NULL,\r\n" + 
					"  `cId` int(11) DEFAULT NULL,\r\n" + 
					"  FULLTEXT KEY `DID` (`content`)\r\n" + 
					") ENGINE=MyISAM DEFAULT CHARSET=utf8";
			ps = cnn.prepareStatement(sql);
			ps.execute();
			
			sql = "CREATE TABLE `policydocument_filetype` (\r\n" + 
					"  `id` varchar(255) NOT NULL,\r\n" + 
					"  `fileSubTypeName` varchar(255) DEFAULT NULL,\r\n" + 
					"  PRIMARY KEY (`id`)\r\n" + 
					") ENGINE=InnoDB DEFAULT CHARSET=utf8";
			ps = cnn.prepareStatement(sql);
			ps.execute();
			
			 sql = "CREATE TABLE `policydocument_html` (\r\n" + 
			 		"  `did` varchar(255) NOT NULL,\r\n" + 
			 		"  `html` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not NULL,\r\n" + 
			 		"  PRIMARY KEY (`did`)\r\n" + 
			 		") ENGINE=InnoDB DEFAULT CHARSET=utf8";
			ps = cnn.prepareStatement(sql);
			ps.execute();
			
			 sql = "CREATE TABLE `policydocument_struction` (\r\n" + 
			 		"  `id` varchar(255) NOT NULL,\r\n" + 
			 		"  `name` varchar(255) DEFAULT NULL,\r\n" + 
			 		"  PRIMARY KEY (`id`)\r\n" + 
			 		") ENGINE=InnoDB DEFAULT CHARSET=utf8";
			ps = cnn.prepareStatement(sql);
			ps.execute();
			
			ps.close();
			cnn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String createDBUrl(String Host, String Port, String DBName) {
		return "jdbc:mysql://" + Host + ":" + Port + "/" + DBName;
	}

	@SuppressWarnings({ "unchecked" })
	public void fileToObject(String filePath) {

		// 读取整个升级包文件，以字节数组型式保存
		byte[] objBytes = this.getByteArrayFromFile(filePath);

		SerializationContext context = new SerializationContext();

		Amf3Input amf = new Amf3Input(context);
		Amf3Input amf_temp = new Amf3Input(context);
		byte[] bt = new byte[20000000];
		int len = 0;

		// 读取数据文件的版本号
		ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
		amf.setInputStream(bais);
		try {
			this.DataVer = amf.readUTF();
			System.out.println("开始读写数据文件：" + filePath);
			System.out.println("数据文件版本号：" + this.DataVer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("读取文件版本号完成");
		// 读取政策文件
		try {
			len = amf.readInt();
			amf.read(bt, 0, len);
			amf_temp.setInputStream(new ByteArrayInputStream(this.decompress(bt)));
			Object[] ba = (Object[]) amf_temp.readObject();

			for (int i = 0; i < ba.length; i++) {
				ASObject object = (ASObject) ba[i];

				PolicyFileEntity pfe = new PolicyFileEntity();
				pfe.setWordsCount((int) object.get("wordsCount"));
				pfe.setDocNum((String) object.get("docNum"));
				pfe.setLink((String) object.get("link"));
				pfe.setsTitle((String) object.get("sTitle"));
				pfe.setTitle((String) object.get("title"));
				pfe.setPostMechanism((String) object.get("postMechanism"));
				pfe.setTid((int) object.get("tid"));
				pfe.setContent(new String(object.get("content").toString()));
				pfe.setPostTime(new Double(object.get("postTime").toString()));
				pfe.setEffTime(new Double(object.get("effTime").toString()));
				pfe.setState((int) object.get("state"));
				pfe.setdId((int) object.get("dId"));
				if (object.containsKey("cId")) {
					pfe.setcId((int) object.get("cId"));
				}
				// if(object.containsKey("attaches")) {
				// pfe.setAttaches((Object[]) object.get("attaches"));
				// }

				this.PolicyFileRecordArr.add(pfe);
			}
			amf.close();
			amf_temp.close();
			System.out.println("读取文件列表完成");
			System.out.println("共有外汇政策文件：" + ba.length + "份");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 读取发布机构列表
		try {
			len = amf.readInt();
			amf.read(bt, 0, len);
			amf_temp.setInputStream(new ByteArrayInputStream(this.decompress(bt)));
			this.StructionMap = (Map<String, String>) amf_temp.readObject();

			// System.out.println(" get struction map complete! ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("读取机构信息完成");

		// 读取政策文件分类列表
		try {
			len = amf.readInt();
			amf.read(bt, 0, len);
			amf_temp.setInputStream(new ByteArrayInputStream(this.decompress(bt)));
			// Object d=amf_temp.readObject();
			this.FileSubTypeMap =  (Map<String, ASObject>) amf_temp.readObject();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("读取文件分类列表完成");

		System.out.println("开始读取并写入独立文件");

		// 读取图片文件和附件分类列表
		try {
			int totalFile = amf.readInt(); // 所有政策文件的总数
			int n = 0;
			while (n < totalFile) {

				// 文件夹ID
				int did = amf.readInt();
				int did_len = amf.readInt();
				amf.read(bt, 0, did_len);
				this.HtmlFileArr.add(new HtmlFile(did, new String(this.decompress(bt), "gb2312")));

				int imageArrayLen = amf.readInt();
				if (imageArrayLen > 0) {
					int m = 0;
					
					File f = new File("src/main/resources/static/Img/" + did + ".files/");
					if (!f.exists()) {
						f.mkdirs();
					}

					while (m < imageArrayLen) {

						String imageName = amf.readUTF();
						// 读出图片内容的字节
						int imageSize = amf.readInt();
						amf.read(bt, 0, imageSize);

						FileOutputStream fos = new FileOutputStream(new File("src/main/resources/static/Img/" + did + ".files/" + imageName));
						fos.write(this.decompress(bt));
						fos.close();

						// 解压图片内容字节数组
						m++;
					}
				}
				// System.out.println("从数据文件读取独立图片文件，并输出到指定文件夹完成");

				int AttachArrayLen = amf.readInt();
				if (AttachArrayLen > 0) {
					int l = 0;
					File f1 = new File("src/main/resources/static/Attach/" + did + "/");
					if (!f1.exists()) {
						f1.mkdirs();
					}

					while (l < AttachArrayLen) {
						String attachName = amf.readUTF(); // 读出附件内容的字节
						int attachSize = amf.readInt();
						amf.read(bt, 0, attachSize); // 保存文件到指定的目录
						FileOutputStream fos = new FileOutputStream(new File("src/main/resources/static/Attach/" + did + "/" + attachName));
						fos.write(this.decompress(bt));
						fos.close();
						l++;
					}
				}
				n++;
			}

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("从数据文件读取独立附件，并输出到指定文件夹完成");

	}

	/**
	 * 将本类的属性保存到数据库中
	 */
	public void saveObjectToDB(String Host, String Port, String DBName, String DBUser, String DBPassword) {
		

		try {
			String url = this.createDBUrl(Host, Port, DBName);
			this.createDBTable(Host, Port, DBName, DBUser, DBPassword);
			String user = DBUser;
			String Password = DBPassword;
			Connection cnn = DriverManager.getConnection(url, user, Password);
			PreparedStatement ps;

			// 清空原业的policydocument_list表
			String sql_delete = "truncate policydocument_list";
			ps = cnn.prepareStatement(sql_delete);
			ps.execute();
			// 插入policydocument_list记录
			String sql = "insert into policydocument_list value(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = cnn.prepareStatement(sql);
			for (int i = 0, len = this.PolicyFileRecordArr.size(); i < len; i++) {
				PolicyFileEntity pfe = this.PolicyFileRecordArr.get(i);
				ps.setInt(1, pfe.getWordsCount());
				ps.setString(2, pfe.getDocNum());
				ps.setString(3, pfe.getLink());
				ps.setString(4, pfe.getsTitle());
				ps.setString(5, pfe.getTitle());
				ps.setString(6, pfe.getPostMechanism());
				ps.setInt(7, pfe.getTid());
				ps.setString(8, pfe.getContent());
				ps.setDouble(9, pfe.getPostTime());
				ps.setDouble(10, pfe.getEffTime());
				ps.setInt(11, pfe.getState());
				ps.setInt(12, pfe.getdId());
				ps.setInt(13, pfe.getcId());
				ps.execute();
			}

			// 清空原业的policydocument_struction表
			sql_delete = "truncate policydocument_struction";
			ps = cnn.prepareStatement(sql_delete);
			ps.execute();
			// 插入policydocument_struction记录
			sql = "insert into policydocument_struction value(?,?)";
			ps = cnn.prepareStatement(sql);
			// ps.setInt(1,this.StructionMap.get("id"));
			for (Map.Entry<String, String> entry : this.StructionMap.entrySet()) {
				ps.setString(1, entry.getKey());
				ps.setString(2, entry.getValue());
				ps.execute();
			}

			// 清空原业的policydocument_html表
			sql_delete = "truncate policydocument_html";
			ps = cnn.prepareStatement(sql_delete);
			ps.execute();
			// 插入policydocument_struction记录
			sql = "insert into policydocument_html value(?,?)";
			ps = cnn.prepareStatement(sql);
			// ps.setInt(1,this.StructionMap.get("id"));
			for (int i = 0; i < this.HtmlFileArr.size(); i++) {
				HtmlFile hf = this.HtmlFileArr.get(i);
				ps.setInt(1, hf.getDid());
				ps.setString(2, hf.getHtmlContent());
				ps.execute();
			}

			// 清空原业的policydocument_filetype表
			sql_delete = "truncate policydocument_filetype";
			ps = cnn.prepareStatement(sql_delete);
			ps.execute();
			// 插入policydocument_filetype记录
			sql = "insert into policydocument_filetype value(?,?)";
			ps = cnn.prepareStatement(sql);
			int b = 0;
			for (Map.Entry<String, ASObject> entry : this.FileSubTypeMap.entrySet()) {
				ps.setString(1, String.valueOf(b++));
				ps.setString(2, (String) entry.getValue().get("cId"));
				ps.execute();
			}

			// 关闭数据查询语句和数据连接
			ps.close();
			cnn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public DataFileAccess() {
		super();
		// TODO Auto-generated constructor stub
	}

	public byte[] getByteArrayFromFile(String filePath) {

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(fis);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int c;
		try {
			c = bis.read();
			while (c != -1) {

				baos.write(c);

				c = bis.read();

			}

			bis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte retArr[] = baos.toByteArray();

		return retArr;
	}

	/**
	 * 压缩
	 * 
	 * @param data
	 *            待压缩数据
	 * @return byte[] 压缩后的数据
	 */
	public static byte[] compress(byte[] data) {
		byte[] output = new byte[0];

		Deflater compresser = new Deflater();

		compresser.reset();
		compresser.setInput(data);
		compresser.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!compresser.finished()) {
				int i = compresser.deflate(buf);
				bos.write(buf, 0, i);
			}
			output = bos.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		compresser.end();
		return output;
	}

	/**
	 * 压缩
	 * 
	 * @param data
	 *            待压缩数据
	 * 
	 * @param os
	 *            输出流
	 */
	public static void compress(byte[] data, OutputStream os) {
		DeflaterOutputStream dos = new DeflaterOutputStream(os);

		try {
			dos.write(data, 0, data.length);

			dos.finish();

			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压缩
	 * 
	 * @param data
	 *            待压缩的数据
	 * @return byte[] 解压缩后的数据
	 */
	public byte[] decompress(byte[] data) {
		byte[] output = new byte[0];

		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);

		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
				o.write(buf, 0, i);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		decompresser.end();
		return output;
	}

	/**
	 * 解压缩
	 * 
	 * @param is
	 *            输入流
	 * @return byte[] 解压缩后的数据
	 */
	public byte[] decompress(InputStream is) {
		InflaterInputStream iis = new InflaterInputStream(is);
		ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
		try {
			int i = 10240;
			byte[] buf = new byte[i];

			while ((i = iis.read(buf, 0, i)) > 0) {
				o.write(buf, 0, i);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return o.toByteArray();
	}

}
