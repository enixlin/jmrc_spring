package com.enixlin.jmrc.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.IndexPerformance;
import com.enixlin.jmrc.entity.MonthPerformace;
import com.enixlin.jmrc.entity.Product;
import com.enixlin.jmrc.entity.SettleRange;
import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.entity.Unit;
import com.enixlin.jmrc.entity.UnitPerformance;
import com.enixlin.jmrc.mapper.SettleRecordMapper;
import com.enixlin.jmrc.service.SettleRecordService;
import com.google.gson.JsonArray;

@Service
public class SettleRecordServiceImpl extends BaseServiceImpl<SettleRecord>
		implements SettleRecordService {

	@Autowired
	SettleRecordMapper settleRecordMapper;

	@Override
	public SettleRecord add(SettleRecord reocrd) {
		settleRecordMapper.insert(reocrd);
		return reocrd;
	}

	@Override
	public ArrayList<UnitPerformance> getBranchPerformance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<IndexPerformance> getAllBusyTypeProformance(String start,
			String end) {
		// TODO Auto-generated method stub
		ArrayList<String> products = new ArrayList<String>();
		// products.add("汇入汇款");
		products.add("汇出汇款");
		products.add("出口信用证");

		return settleRecordMapper.getAllBusyTypeProformance(start, end,
				products);
	}

	public String compareDate(String date) {
		String s = "";
		int year = Integer.parseInt(date.substring(0, 4));
		s = String.valueOf(year - 1) + date.substring(4);
		return s;
	}

	public int getSeason(String date) {
		String s = "";
		int month = Integer.parseInt(date.substring(4, 6));
		if (month >= 1 && month <= 3) {
			return 1;
		}
		if (month >= 4 && month <= 6) {
			return 2;
		}
		if (month >= 7 && month <= 9) {
			return 3;
		}
		if (month >= 10 && month <= 12) {
			return 4;
		}
		return 0;

	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getAllUnitPerformance(
			String start,
			String end) {
		// 取得国际结算的产品口径
		ArrayList<Product> products = settleRecordMapper
				.getSettleRangeProduct();
		String expiry = end.substring(0, 4);
		int season = getSeason(end);
		// 取得各经营单位的任务
		// 全年任务
		ArrayList<HashMap<String, Object>> unitTasks_year = settleRecordMapper
				.getUnitTasks(expiry);
		// 季度任务
		ArrayList<HashMap<String, Object>> unitTasks_season = settleRecordMapper
				.getUnitTasks_season(expiry, season);

		ArrayList<HashMap<String, Object>> all = settleRecordMapper
				.getUnitPerformance(start, end, products);
		ArrayList<HashMap<String, Object>> all_pre = settleRecordMapper
				.getUnitPerformance(compareDate(start), compareDate(end),
						products);

		ArrayList<LinkedHashMap<String, Object>> all_unit = new ArrayList<>();

		float amount_sum = 0;
		float amount_pre_sum = 0;
		int times_sum = 0;
		int times_pre_sum = 0;
		float task_year_sum = 0;
		float task_season_sum = 0;

		// 以全部经营单位的任务数组为基础，分别插入经营单位的当年实绩和去年同期实绩
		for (int i = 0, len2 = unitTasks_year.size(); i < len2; i++) {
			LinkedHashMap<String, Object> unitPerformance = new LinkedHashMap();
			DecimalFormat df = new DecimalFormat("0.00");
			float amount = 0;
			int times = 0;
			float amount_pre = 0;
			int times_pre = 0;

			// 第一步插入全年任务数据，应该将这个放在历遍的最外层,因为不是所有的经营单位都有实绩，但
			// 所有的经营单位都会设定一个任务
			unitPerformance.put("行号",
					unitTasks_year.get(i).get("branch_code"));
			unitPerformance.put("行名",
					unitTasks_year.get(i).get("branch_name"));
			unitPerformance.put("年度任务",
					unitTasks_year.get(i).get("task_amount"));

			// 季度任务比例
			int percent = (int) unitTasks_season.get(0).get("percent");
			// 年度任务
			int task_year = (int) unitTasks_year.get(i)
					.get("task_amount");
			// 季度任务数
			int task_season = task_year * percent / 100;
			// 插入季度任务数
			unitPerformance.put("季度任务", task_season);

			task_year_sum = task_year_sum + task_year;
			task_season_sum = task_season_sum + task_season;

			// 第二步，历遍当期业绩数组，如果机构号相同的话，就插入当期机构业绩
			for (int n = 0, len = all.size(); n < len; n++) {
				if (all.get(n).get("branchCode")
						.equals(unitTasks_year.get(i).get("branch_code"))) {
					// 当期实绩
					amount = Float
							.parseFloat(all.get(n).get("amount").toString());
					times = (int) Float
							.parseFloat(all.get(n).get("times").toString());
					amount_sum = amount_sum + amount;
					times_sum = times_sum + times;
					break;
				}
			}

			// 插入同比数据
			for (int m = 0, len1 = all_pre.size(); m < len1; m++) {
				if (unitTasks_year.get(i).get("branch_code")
						.equals(all_pre.get(m).get("branchCode"))) {
					amount_pre = Float.parseFloat(
							all_pre.get(m).get("amount").toString());
					times_pre = (int) Float
							.parseFloat(all_pre.get(m).get("times").toString());
					amount_pre_sum = amount_pre_sum + amount_pre;
					times_pre_sum = times_pre_sum + times_pre;

				}
			}

			unitPerformance.put("任务完成率（年）",
					(float) amount / (float) task_year * 100);
			unitPerformance.put("任务完成率（季）",
					(float) amount / (float) task_season * 100);
			unitPerformance.put("笔数", times);
			unitPerformance.put("金额", amount);
			unitPerformance.put("笔数（去年）", times_pre);
			unitPerformance.put("金额（去年）", amount_pre);
			unitPerformance.put("笔数同比", times - times_pre);
			unitPerformance.put("金额同比", amount - amount_pre);

			// 将单个经营单位的数据写入
			all_unit.add(unitPerformance);

		}

		LinkedHashMap<String, Object> unitPerformance = new LinkedHashMap();
		DecimalFormat df = new DecimalFormat("0.00");
		unitPerformance.put("行号",
				"");
		unitPerformance.put("行名",
				"全行合计");
		unitPerformance.put("年度任务",
				task_year_sum);
		unitPerformance.put("季度任务",
				task_season_sum);
		unitPerformance.put("任务完成率（年）",
				(float) amount_sum / (float) task_year_sum * 100);
		unitPerformance.put("任务完成率（季）",
				(float) amount_sum / (float) task_season_sum * 100);
		unitPerformance.put("笔数", times_sum);
		unitPerformance.put("金额", amount_sum);
		unitPerformance.put("笔数（去年）", times_pre_sum);
		unitPerformance.put("金额（去年）", amount_pre_sum);
		unitPerformance.put("笔数同比", times_sum - times_pre_sum);
		unitPerformance.put("金额同比", amount_sum - amount_pre_sum);
		all_unit.add(unitPerformance);

		return all_unit;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.enixlin.jmrc.service.SettleRecordService#getMonthPerformance(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public List<IndexPerformance> getMonthPerformance(String start,
			String end) {
		// TODO Auto-generated method stub

		ArrayList<Product> products = settleRecordMapper
				.getSettleRangeProduct();

		List<IndexPerformance> mi = settleRecordMapper
				.getMonthPerformance(start, end, products);
		return mi;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.enixlin.jmrc.service.SettleRecordService#getsettleRange()
	 */
	@Override
	public List<SettleRange> getsettleRange() {
		// TODO Auto-generated method stub
		return settleRecordMapper.getsettleRange();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.enixlin.jmrc.service.SettleRecordService#getAllProduct()
	 */
	@Override
	public ArrayList<String> getAllProduct() {
		// TODO Auto-generated method stub
		return settleRecordMapper.getAllProdects();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.enixlin.jmrc.service.SettleRecordService#saveSettleRangeProduct(java.
	 * lang.String[])
	 */
	@Override
	public int saveSettleRangeProduct(ArrayList<String> products) {
		// TODO Auto-generated method stub
		return settleRecordMapper.saveSettleRangeProduct(products);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.enixlin.jmrc.service.SettleRecordService#
	 * getAllProductFromSettleRecord()
	 */
	@Override
	public ArrayList<String> getAllProductFromSettleRecord() {
		// TODO Auto-generated method stub
		return settleRecordMapper.getAllProductFromSettleRecord();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.enixlin.jmrc.service.SettleRecordService#getSettleRangeProduct()
	 */
	@Override
	public ArrayList<Product> getSettleRangeProduct() {
		// TODO Auto-generated method stub
		return settleRecordMapper.getSettleRangeProduct();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.enixlin.jmrc.service.SettleRecordService#
	 * getAllBusySettleTypeProformance(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<IndexPerformance> getAllBusySettleTypeProformance(
			String start, String end) {
		ArrayList<Product> product = settleRecordMapper.getSettleRangeProduct();
		ArrayList<String> productName = new ArrayList<String>();
		for (int n = 0, len = product.size(); n < len; n++) {
			productName.add(product.get(n).getName());
		}
		ArrayList<IndexPerformance> mi = settleRecordMapper
				.getAllBusySettleTypeProformance(start, end, productName);
		return mi;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.enixlin.jmrc.service.SettleRecordService#getAllUnitName()
	 */
	@Override
	public ArrayList<String> getAllUnitName() {
		// TODO Auto-generated method stub
		return settleRecordMapper.getAllUnitName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.enixlin.jmrc.service.SettleRecordService#getAllUnitInfo()
	 */
	@Override
	public ArrayList<Unit> getAllUnitInfo() {
		// TODO Auto-generated method stub
		return settleRecordMapper.getAllUnitInfo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.enixlin.jmrc.service.SettleRecordService#
	 * getSettleTypeProformanceByDate(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<IndexPerformance> getSettleTypeProformanceByDate(
			String start, String end) {
//        String year_s=start.substring(0, 4);
//        String year_e=end.substring(0,4);
//        String monthday_s=start.substring(4,8);
//        String monthday_e=start.substring(4,8);
//        ArrayList<IndexPerformance> performance=this.getAllBusySettleTypeProformance(start, end);
//        ArrayList<IndexPerformance> performance_pre=this.getAllBusySettleTypeProformance(start, end);

		return this.getAllBusySettleTypeProformance(start, end);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.enixlin.jmrc.service.SettleRecordService#getDateRangeDetail(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public ArrayList<SettleRecord> getDateRangeDetail(String start,
			String end, String product) {
		// TODO Auto-generated method stub
		return settleRecordMapper.getDateRangeDetail(start, end, product);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.enixlin.jmrc.service.SettleRecordService#allUnitSettlePerformance(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public HashMap<String, ArrayList<IndexPerformance>> allUnitSettlePerformance(
			String start, String end) {
		// TODO Auto-generated method stub
		ArrayList<Unit> units = this.getAllUnitInfo();
		HashMap<String, ArrayList<IndexPerformance>> UnitSettlePerformance = new HashMap<String, ArrayList<IndexPerformance>>();

		return null;
	}

	@Override
	public IndexPerformance getPerformanceByUnitNameAndIndex(
			String start,
			String end, Unit unit, String UnitType,
			ArrayList<Product> products) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.enixlin.jmrc.service.SettleRecordService#getUnitIndexPerformance(java
	 * .lang.String, java.lang.String, com.enixlin.jmrc.entity.Unit,
	 * java.util.ArrayList, java.lang.String)
	 */
	@Override
	public ArrayList<IndexPerformance> getUnitIndexPerformance(
			String start, String end, Unit unit, ArrayList<Product> products) {
		// TODO Auto-generated method stub

		// return
		// settleRecordMapper.getClientIndexPerformance(unit,products,start,end);
		return settleRecordMapper.getUnitIndexPerformance(unit, products, start,
				end);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.enixlin.jmrc.service.SettleRecordService#getUnitMonthPerformace(com.
	 * enixlin.jmrc.entity.Unit, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<MonthPerformace> getUnitMonthPerformace(Unit unit,
			String start, String end) {
		ArrayList<Product> products = getSettleRangeProduct();
		if (unit.getType().equals("经营单位")) {
			return settleRecordMapper.getUnitMonthPerformace(unit, start, end,
					products);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.enixlin.jmrc.service.SettleRecordService#getAllClientPerformance(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<LinkedHashMap<String, Object>> getAllClientPerformance(
			String start, String end, String clientType) {

		// 取得国际结算的产品口径
		ArrayList<Product> products = settleRecordMapper
				.getSettleRangeProduct();
		String expiry = end.substring(0, 4);

		ArrayList<HashMap<String, Object>> Preformance_current = settleRecordMapper
				.getClientPerformance(start, end, products);
		ArrayList<HashMap<String, Object>> Preformance_pre = settleRecordMapper
				.getClientPerformance(compareDate(start), compareDate(end),
						products);
		// 从start-1,到end取得所有的客户的列表
		ArrayList<HashMap<String, Object>> clients = settleRecordMapper
				.getClients(compareDate(start), end, products, clientType);

		ArrayList<LinkedHashMap<String, Object>> all_client = new ArrayList<>();

		for (int i = 0, len = clients.size(); i < len; i++) {
//			HashMap<String, Object> client = new HashMap<>();
			LinkedHashMap<String, Object> client = new LinkedHashMap();
			String custCode = (String) clients.get(i).get("custCode");
			String custName = (String) clients.get(i).get("custName");
			String branchCode = (String) clients.get(i).get("branchCode");
			String branchName = (String) clients.get(i).get("branchName");
			DecimalFormat df = new DecimalFormat("0.00");
			float amount = 0;
			int times = 0;
			float amount_pre = 0;
			int times_pre = 0;

			for (int j = 0, lenj = Preformance_current.size(); j < lenj; j++) {
				String custCode_current = (String) Preformance_current.get(j)
						.get("custCode");
				if (custCode.equals(custCode_current)) {
					amount = (float) Float.parseFloat(Preformance_current.get(j)
							.get("amount").toString());
					times = (int) Float
							.parseFloat(Preformance_current.get(j).get("times")
									.toString());
					break;
				}
			}

			for (int k = 0, lenk = Preformance_pre.size(); k < lenk; k++) {
				String custCode_pre = (String) Preformance_pre.get(k)
						.get("custCode");
				if (custCode.equals(custCode_pre)) {
					amount_pre = (float) Float.parseFloat(Preformance_pre.get(k)
							.get("amount").toString());
					times_pre = (int) Float
							.parseFloat(Preformance_pre.get(k).get("times")
									.toString());
					break;
				}
			}
			// 客户有同期业绩或同期业绩
			if (times != 0 || times_pre != 0) {
				client.put("xuhao", i);
				client.put("custCode", custCode);
				client.put("custName", custName);
				client.put("branchCode", branchCode);
				client.put("branchName", branchName);
				client.put("amount", amount);
				client.put("times", times);
				client.put("amount_pre", amount_pre);
				client.put("times_pre", times_pre);
				client.put("times_compare", times - times_pre);
				client.put("amount_compare", amount - amount_pre);
				all_client.add(client);
			}

		}

		return all_client;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.enixlin.jmrc.service.SettleRecordService#getUnitSettleMonthPerformace
	 * (com.enixlin.jmrc.entity.Unit, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ArrayList<LinkedHashMap<String, Object>> getUnitSettleMonthPerformance(
			Unit unit,
			String start, String end) {
		// TODO Auto-generated method stub
		// 分两种情况，一是单个经营单位，二是全行
		if (unit.getType().equals("unit")) {
			ArrayList<Product> products = this.getSettleRangeProduct();
			return settleRecordMapper.getUnitSettleMonthPerformance(unit,
					products, start, end);
		} else {
			ArrayList<Product> products = this.getSettleRangeProduct();
			return settleRecordMapper.getTotalSettleMonthPerformance(unit,
					products, start, end);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enixlin.jmrc.service.SettleRecordService#
	 * getClientSettleMonthPerformance(com.enixlin.jmrc.entity.Unit,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<LinkedHashMap<String, Object>> getClientSettleMonthPerformance(
			Unit unit, String start, String end) {
		ArrayList<Product> products = this.getSettleRangeProduct();
		return settleRecordMapper.getClientSettleMonthPerformance(unit,
				products, start, end);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enixlin.jmrc.service.SettleRecordService#fixedSettleRecord()
	 */
	@Override
	public void fixedSettleRecord() {
		// TODO Auto-generated method stub
		settleRecordMapper.fixedSettleRecord_input();
		System.out.println("更正跨境人民币汇入");
		settleRecordMapper.fixedSettleRecord_output();
		System.out.println("更正跨境人民币汇出");
		settleRecordMapper.fixedSettleRecord_delete_1();
		settleRecordMapper.fixedSettleRecord_delete_2();
		System.out.println("删除多余跨境人民币记录");

	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getAllProductsFromSettleRecord() {
		ArrayList<LinkedHashMap<String, Object>> products = settleRecordMapper
				.getAllProductsFromSettleRecord();
		ArrayList<LinkedHashMap<String, Object>> p = new ArrayList<>();
		int n = 0;
		for (LinkedHashMap<String, Object> product : products) {
			product.put("id", n);

			p.add(product);
			n++;
		}
		return p;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.enixlin.jmrc.service.SettleRecordService#saveRangeProducts(java.util.
	 * ArrayList)
	 */
	@Override
	public int saveRangeProducts(
			ArrayList<Product> rangeProducts) {
		// TODO Auto-generated method stub
		settleRecordMapper.deleteAllRangeProducts();
		return settleRecordMapper.saveRangeProducts(rangeProducts);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.enixlin.jmrc.service.SettleRecordService#getUnitProductPerformance(
	 * com.enixlin.jmrc.entity.Unit, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<LinkedHashMap<String, Object>> getUnitProductPerformance(
			Unit unit, String start, String end) {
		// TODO Auto-generated method stub
		ArrayList<Product> products = this.getSettleRangeProduct();
		String start_pre = this.compareDate(start);
		String end_pre = this.compareDate(end);

		ArrayList<LinkedHashMap<String, Object>> detail_current = settleRecordMapper
				.getUnitProductPerformance(unit, start, end,products);
		ArrayList<LinkedHashMap<String, Object>> detail_pre = settleRecordMapper
				.getUnitProductPerformance(unit, start_pre, end_pre,products);
		
		
//		历遍当期的产品列表
			for (LinkedHashMap<String, Object> element_currenct : detail_current) {
				int exist = 0;
//				历遍前期的客户列表
				for (LinkedHashMap<String, Object> element_pre : detail_pre) {
					if (element_currenct.get("product_name")
							.equals(element_pre.get("product_name"))) {
						element_currenct.put("amount_pre",
								element_pre.get("amount"));
						element_currenct.put("times_pre", element_pre.get("times"));
						exist = 1;
					}
				}
				if (exist == 0) {
					element_currenct.put("amount_pre", new BigDecimal(0));
					element_currenct.put("times_pre", 0);
				}
			}
			for (LinkedHashMap<String, Object> element_pre : detail_pre) {
				int exist = 0;
//				历遍前期的客户列表
				for (LinkedHashMap<String, Object> element_currenct : detail_current) {
					if (element_currenct.get("product_name")
							.equals(element_pre.get("product_name"))) {
						exist = 1;
					}
				}
				if (exist == 0) {
					LinkedHashMap<String, Object> new_element = new LinkedHashMap<>();
					// 注意插入的字段要按顺序，否则会影响Excel文件的列顺序
					new_element.put("product_name", element_pre.get("product_name"));
					new_element.put("amount", new BigDecimal(0));
					new_element.put("times", 0);
					new_element.put("amount_pre", element_pre.get("amount"));
					new_element.put("times_pre", element_pre.get("times"));
					detail_current.add(new_element);

				}
			}

			for (LinkedHashMap<String, Object> element_current : detail_current) {
				BigDecimal amount = (BigDecimal) element_current.get("amount");
				BigDecimal amount_pre = (BigDecimal) element_current
						.get("amount_pre");
				long times = Long
						.parseLong(element_current.get("times").toString());
				long times_pre = Long
						.parseLong(element_current.get("times_pre").toString());

				element_current.put("amount_compare", amount.subtract(amount_pre));
				element_current.put("times_compare", times - times_pre);
			}
			return detail_current;
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.enixlin.jmrc.service.SettleRecordService#getUnitClientPerformance(com
	 * .enixlin.jmrc.entity.Unit, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<LinkedHashMap<String, Object>> getUnitClientPerformance(
			Unit unit, String start, String end) {
		// TODO Auto-generated method stub
		ArrayList<Product> products = this.getSettleRangeProduct();
		
		String start_pre = this.compareDate(start);
		String end_pre = this.compareDate(end);

		ArrayList<LinkedHashMap<String, Object>> detail_current = settleRecordMapper
				.getUnitClientPerformance(unit, start, end,products);
		ArrayList<LinkedHashMap<String, Object>> detail_pre = settleRecordMapper
				.getUnitClientPerformance(unit, start_pre, end_pre,products);
		
		
//		历遍当期的产品列表
			for (LinkedHashMap<String, Object> element_currenct : detail_current) {
				int exist = 0;
//				历遍前期的客户列表
				for (LinkedHashMap<String, Object> element_pre : detail_pre) {
					if (element_currenct.get("cust_number")
							.equals(element_pre.get("cust_number"))) {
						element_currenct.put("amount_pre",
								element_pre.get("amount"));
						element_currenct.put("times_pre", element_pre.get("times"));
						exist = 1;
					}
				}
				if (exist == 0) {
					element_currenct.put("amount_pre", new BigDecimal(0));
					element_currenct.put("times_pre", 0);
				}
			}
			for (LinkedHashMap<String, Object> element_pre : detail_pre) {
				int exist = 0;
//				历遍前期的客户列表
				for (LinkedHashMap<String, Object> element_currenct : detail_current) {
					if (element_currenct.get("cust_number")
							.equals(element_pre.get("cust_number"))) {
						exist = 1;
					}
				}
				if (exist == 0) {
					LinkedHashMap<String, Object> new_element = new LinkedHashMap<>();
					// 注意插入的字段要按顺序，否则会影响Excel文件的列顺序
					new_element.put("cust_number", element_pre.get("cust_number"));
					new_element.put("cust_name", element_pre.get("cust_name"));
					new_element.put("amount", new BigDecimal(0));
					new_element.put("times", 0);
					new_element.put("amount_pre", element_pre.get("amount"));
					new_element.put("times_pre", element_pre.get("times"));
					detail_current.add(new_element);

				}
			}

			for (LinkedHashMap<String, Object> element_current : detail_current) {
				BigDecimal amount = (BigDecimal) element_current.get("amount");
				BigDecimal amount_pre = (BigDecimal) element_current
						.get("amount_pre");
				long times = Long
						.parseLong(element_current.get("times").toString());
				long times_pre = Long
						.parseLong(element_current.get("times_pre").toString());

				element_current.put("amount_compare", amount.subtract(amount_pre));
				element_current.put("times_compare", times - times_pre);
			}
			return detail_current;
		
	
	}

	@Override
	public String getLastUpdateDate() {
		// TODO Auto-generated method stub
		return settleRecordMapper.getLastUpdateDate();
	}

	@Override
	public String getTotalSettlePerformance(String start, String end) {
		// TODO Auto-generated method stub
		ArrayList<Product> products = this.getSettleRangeProduct();
		return settleRecordMapper.getTotalSettlePerformance(start, end,
				products);
	}

	@Override
	public void updatelog(String datatime) {
		// TODO Auto-generated method stub
		settleRecordMapper.truncateUpdateLog();
		settleRecordMapper.updatelog(datatime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enixlin.jmrc.service.SettleRecordService#getTotalTask(java.lang.
	 * String)
	 */
	@Override
	public String getTotalTask(String end) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> tasks_year = settleRecordMapper
				.getUnitTasks(end.substring(0, 4));
		int season = this.getSeason(end);
		ArrayList<HashMap<String, Object>> tasks_season = settleRecordMapper
				.getUnitTasks_season(end.substring(0, 4), season);
		int task_year = 0;
		int task_seaon = 0;
		for (HashMap<String, Object> e : tasks_year) {
			task_year = task_year + (int) e.get("task_amount");
		}
//		for(HashMap<String, Object> e : tasks_season) {
//			task_seaon=task_seaon+(int)e.get("task_amount");
//		}
		return String.valueOf(task_year);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getProductMonthPerformance(
			String product, String start, String end) {
		// TODO Auto-generated method stub
		return settleRecordMapper.getProductMonthPerformance(product, start,
				end);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getProductClientDetail(
			String product, String start, String end) {
		String start_pre = this.compareDate(start);
		String end_pre = this.compareDate(end);

		ArrayList<LinkedHashMap<String, Object>> detail_current = settleRecordMapper
				.getProductClientDetail(product, start, end);
		ArrayList<LinkedHashMap<String, Object>> detail_pre = settleRecordMapper
				.getProductClientDetail(product, start_pre, end_pre);

//	历遍当期的客户列表
		for (LinkedHashMap<String, Object> element_currenct : detail_current) {
			int exist = 0;
//			历遍前期的客户列表
			for (LinkedHashMap<String, Object> element_pre : detail_pre) {
				if (element_currenct.get("clientId")
						.equals(element_pre.get("clientId"))) {
					element_currenct.put("amount_pre",
							element_pre.get("amount"));
					element_currenct.put("times_pre", element_pre.get("times"));
					exist = 1;
				}
			}
			if (exist == 0) {
				element_currenct.put("amount_pre", new BigDecimal(0));
				element_currenct.put("times_pre", 0);
			}
		}
		for (LinkedHashMap<String, Object> element_pre : detail_pre) {
			int exist = 0;
//			历遍前期的客户列表
			for (LinkedHashMap<String, Object> element_currenct : detail_current) {
				if (element_currenct.get("clientId")
						.equals(element_pre.get("clientId"))) {
					exist = 1;
				}
			}
			if (exist == 0) {
				LinkedHashMap<String, Object> new_element = new LinkedHashMap<>();
				// 注意插入的字段要按顺序，否则会影响Excel文件的列顺序
				new_element.put("clientId", element_pre.get("clientId"));
				new_element.put("clientName", element_pre.get("clientName"));
				new_element.put("amount", new BigDecimal(0));
				new_element.put("times", 0);
				new_element.put("amount_pre", element_pre.get("amount"));
				new_element.put("times_pre", element_pre.get("times"));
				detail_current.add(new_element);

			}
		}

		for (LinkedHashMap<String, Object> element_current : detail_current) {
			BigDecimal amount = (BigDecimal) element_current.get("amount");
			BigDecimal amount_pre = (BigDecimal) element_current
					.get("amount_pre");
			long times = Long
					.parseLong(element_current.get("times").toString());
			long times_pre = Long
					.parseLong(element_current.get("times_pre").toString());

			element_current.put("amount_compare", amount.subtract(amount_pre));
			element_current.put("times_compare", times - times_pre);
		}
		return detail_current;
	}

	@Override
	public ArrayList<HashMap<String, Object>> exportProductClientDetailExcel(
			String product, String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getProductDetail(
			String product, String start, String end) {
		// TODO Auto-generated method stub
		return settleRecordMapper.getProductDetail(product, start, end);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getAllProductDetail(
			String start, String end) {

		ArrayList<Product> products = this.getSettleRangeProduct();

		String pre_start = this.compareDate(start);
		String pre_end = this.compareDate(end);

		ArrayList<LinkedHashMap<String, Object>> productPerformance_pre = settleRecordMapper
				.getAllProductDetail(products, pre_start, pre_end);
		ArrayList<LinkedHashMap<String, Object>> productPerformance_current = settleRecordMapper
				.getAllProductDetail(products, start, end);
		ArrayList<LinkedHashMap<String, Object>> productPerformance = new ArrayList<>();

		for (LinkedHashMap<String, Object> element : productPerformance_current) {
			for (LinkedHashMap<String, Object> element_p : productPerformance_pre) {
				if (element.get("product_name")
						.equals(element_p.get("product_name"))) {
					BigDecimal amount = (BigDecimal) element.get("amount");
					BigDecimal amount_pre = (BigDecimal) element_p
							.get("amount");

//					BigDecimal times=(BigDecimal) element.get("times");
//					BigDecimal times_pre=(BigDecimal) element_p.get("times");

					element.put("amount_pre", amount.subtract(amount_pre));
					element.put("times_pre", (long) element.get("times")
							- (long) element_p.get("times"));
					productPerformance.add(element);	
				}
			}
		}

		return productPerformance;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getClientProductPerformance(
			Unit unit, String start, String end) {
		// TODO Auto-generated method stub
		ArrayList<Product> products = this.getSettleRangeProduct();
		String start_pre = this.compareDate(start);
		String end_pre = this.compareDate(end);

		ArrayList<LinkedHashMap<String, Object>> detail_current = settleRecordMapper
				.getClientProductPerformance(unit, start, end,products);
		ArrayList<LinkedHashMap<String, Object>> detail_pre = settleRecordMapper
				.getClientProductPerformance(unit, start_pre, end_pre,products);

//	历遍当期的客户列表
		for (LinkedHashMap<String, Object> element_currenct : detail_current) {
			int exist = 0;
//			历遍前期的客户列表
			for (LinkedHashMap<String, Object> element_pre : detail_pre) {
				if (element_currenct.get("product_name")
						.equals(element_pre.get("product_name"))) {
					element_currenct.put("amount_pre",
							element_pre.get("amount"));
					element_currenct.put("times_pre", element_pre.get("times"));
					exist = 1;
				}
			}
			if (exist == 0) {
				element_currenct.put("amount_pre", new BigDecimal(0));
				element_currenct.put("times_pre", 0);
			}
		}
		for (LinkedHashMap<String, Object> element_pre : detail_pre) {
			int exist = 0;
//			历遍前期的客户列表
			for (LinkedHashMap<String, Object> element_currenct : detail_current) {
				if (element_currenct.get("product_name")
						.equals(element_pre.get("product_name"))) {
					exist = 1;
				}
			}
			if (exist == 0) {
				LinkedHashMap<String, Object> new_element = new LinkedHashMap<>();
				// 注意插入的字段要按顺序，否则会影响Excel文件的列顺序
				new_element.put("product_name", element_pre.get("product_name"));
				new_element.put("amount", new BigDecimal(0));
				new_element.put("times", 0);
				new_element.put("amount_pre", element_pre.get("amount"));
				new_element.put("times_pre", element_pre.get("times"));
				detail_current.add(new_element);

			}
		}

		for (LinkedHashMap<String, Object> element_current : detail_current) {
			BigDecimal amount = (BigDecimal) element_current.get("amount");
			BigDecimal amount_pre = (BigDecimal) element_current
					.get("amount_pre");
			long times = Long
					.parseLong(element_current.get("times").toString());
			long times_pre = Long
					.parseLong(element_current.get("times_pre").toString());

			element_current.put("amount_compare", amount.subtract(amount_pre));
			element_current.put("times_compare", times - times_pre);
		}
		return detail_current;
		
	}

	@Override
	public String getLastBusyDate() {
		// TODO Auto-generated method stub
		return settleRecordMapper.getLastBusyDate();
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getClientDetail(
			String client, String start, String end) {
		// TODO Auto-generated method stub
		ArrayList<Product> products=this.getSettleRangeProduct();
		return settleRecordMapper.getClientDetail(client, start, end,products);
	}

	@Override
	public int addTF(JsonArray ja) {
		// TODO Auto-generated method stub
		
		
		return settleRecordMapper.addTF(ja);
		
	}


}
