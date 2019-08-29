package com.enixlin.jmrc.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ArrayList<HashMap<String, Object>> getAllUnitPerformance(
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

		ArrayList<HashMap<String, Object>> all_unit = new ArrayList<>();
		// 以全部经营单位的任务数组为基础，分别插入经营单位的当年实绩和去年同期实绩
		for (int i = 0, len2 = unitTasks_year.size(); i < len2; i++) {
			HashMap<String, Object> unitPerformance = new HashMap<>();
			DecimalFormat df = new DecimalFormat("0.00");
			int amount = 0;
			int times = 0;
			int amount_pre = 0;
			int times_pre = 0;

			// 第一步插入全年任务数据，应该将这个放在历遍的最外层,因为不是所有的经营单位都有实绩，但
			// 所有的经营单位都会设定一个任务
			unitPerformance.put("branchCode",
					unitTasks_year.get(i).get("branch_code"));
			unitPerformance.put("branchName",
					unitTasks_year.get(i).get("branch_name"));
			unitPerformance.put("year_task",
					unitTasks_year.get(i).get("task_amount"));

			// 季度任务比例
			int percent = (int) unitTasks_season.get(0).get("percent");
			// 年度任务
			int task_year = (int) unitTasks_year.get(i)
					.get("task_amount");
			// 季度任务数
			int task_season = task_year * percent / 100;
			// 插入季度任务数
			unitPerformance.put("season_task", task_season);

			// 第二步，历遍当期业绩数组，如果机构号相同的话，就插入当期机构业绩
			for (int n = 0, len = all.size(); n < len; n++) {
				if (all.get(n).get("branchCode")
						.equals(unitTasks_year.get(i).get("branch_code"))) {
					// 当期实绩
					amount = (int) Float
							.parseFloat(all.get(n).get("amount").toString());
					times = (int) Float
							.parseFloat(all.get(n).get("times").toString());
					break;
				}
			}

			// 插入同比数据
			for (int m = 0, len1 = all_pre.size(); m < len1; m++) {
				if (unitTasks_year.get(i).get("branch_code")
						.equals(all_pre.get(m).get("branchCode"))) {
					amount_pre = (int) Float.parseFloat(
							all_pre.get(m).get("amount").toString());
					times_pre = (int) Float
							.parseFloat(all_pre.get(m).get("times").toString());

				}
			}
			unitPerformance.put("amount", amount);
			unitPerformance.put("times", times);
			unitPerformance.put("taskCompleteYear",
					(float) amount / (float) task_year * 100);
			unitPerformance.put("taskCompleteSeason",
					(float) amount / (float) task_season * 100);
			unitPerformance.put("amount_pre", amount_pre);
			unitPerformance.put("times_pre", times_pre);
			unitPerformance.put("amount_compare", amount - amount_pre);
			unitPerformance.put("times_compare", times - times_pre);

			// 将单个经营单位的数据写入
			all_unit.add(unitPerformance);

		}

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
		// TODO Auto-generated method stub
		// ArrayList<Product>
		// products=settleRecordMapper.getSettleRangeProduct();
		// return
		// settleRecordMapper.getSettleTypeProformanceByDate(start,end,products);
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
	public ArrayList<HashMap<String, Object>> getAllClientPerformance(
			String start, String end,String clientType) {

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
				.getClients(compareDate(start), end, products,clientType);

		ArrayList<HashMap<String, Object>> all_client = new ArrayList<>();

		for (int i = 0, len = clients.size(); i < len; i++) {
			HashMap<String, Object> client = new HashMap<>();
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
			
			for(int k =0,lenk=Preformance_pre.size();k<lenk;k++) {
				String custCode_pre = (String) Preformance_pre.get(k)
						.get("custCode");
				if(custCode.equals(custCode_pre)) {
					amount_pre = (float) Float.parseFloat(Preformance_pre.get(k)
							.get("amount").toString());
					times_pre = (int) Float
							.parseFloat(Preformance_pre.get(k).get("times")
									.toString());
					break;
				}
			}
			//客户有同期业绩或同期业绩
			if(times!=0 || times_pre!=0) {
			client.put("xuhao",i);
			client.put("custCode",custCode);
			client.put("custName",custName);
			client.put("branchCode",branchCode);
			client.put("branchName",branchName);
			client.put("amount",amount);
			client.put("times",times);
			client.put("amount_pre",amount_pre);
			client.put("times_pre",times_pre);
			client.put("times_compare",times-times_pre);
			client.put("amount_compare",amount-amount_pre);
			all_client.add(client);
			}

		}

		return all_client;
	}
}
