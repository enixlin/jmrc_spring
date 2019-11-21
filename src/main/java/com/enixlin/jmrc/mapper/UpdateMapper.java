/**
 * 
 */
package com.enixlin.jmrc.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.ibatis.annotations.Mapper;

import com.enixlin.jmrc.entity.SettleRecord;
import com.google.gson.JsonArray;

/**
 * @author linzhenhuan
 *
 */
@Mapper
public interface UpdateMapper {

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年11月21日
	 */
	void fixedSettleRecord_input();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年11月21日
	 */
	void fixedSettleRecord_output();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年11月21日
	 */
	void fixedSettleRecord_delete_1();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年11月21日
	 */
	void fixedSettleRecord_delete_2();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @return
	 *String
	 * 创建时间：2019年11月21日
	 */
	String getLastSettleUpdateDate();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @return
	 *String
	 * 创建时间：2019年11月21日
	 */
	String getLastTfUpdateDate();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @return
	 *String
	 * 创建时间：2019年11月21日
	 */
	String getLastSubjectUpdateDate();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param record
	 * @return
	 *int
	 * 创建时间：2019年11月21日
	 */
	int addSubjectBalance(ArrayList<LinkedHashMap<String, Object>> record);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年11月21日
	 */
	void truncateTFMiddle();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param ja
	 * @return
	 *int
	 * 创建时间：2019年11月21日
	 */
	int addTF(JsonArray ja);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param reocrd
	 *void
	 * 创建时间：2019年11月21日
	 */
	void insert(SettleRecord reocrd);

}
