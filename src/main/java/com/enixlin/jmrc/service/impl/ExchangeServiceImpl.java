package com.enixlin.jmrc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.enixlin.jmrc.mapper.ExchangeMapper;
import com.enixlin.jmrc.service.ExchangeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeServiceImpl implements ExchangeService {

	@Autowired
	ExchangeMapper em;

	
	public String compareDate(String date) {
		String s = "";
		int year = Integer.parseInt(date.substring(0, 4));
		s = String.valueOf(year - 1) + date.substring(4);
		return s;
	}

	@Override
	public void getExchangeReportType() {

	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getTotalExchangePerformance(String start, String end) {
		// TODO Auto-generated method stub
		return em.getTotalExchangePerformance(start, end);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getTypeTotal(String start, String end) {
		// TODO Auto-generated method stub
		String start_pre = this.compareDate(start);
		String end_pre = this.compareDate(end);
		ArrayList<LinkedHashMap<String, Object>> arr_current = em.getTypeTotal(start, end);
		ArrayList<LinkedHashMap<String, Object>> arr_pre = em.getTypeTotal(start_pre, end_pre);
		for (LinkedHashMap<String, Object> element_current : arr_current) {
			int existFlag = 0;
			for (LinkedHashMap<String, Object> element_pre : arr_pre) {
				String n_pre=(String) element_pre.get("product_name");
				String n_current=(String) element_current.get("product_name");
				if(n_current.equals(n_pre)) {
					long times_compare=(long)element_current.get("times")-(long)element_pre.get("times");
					float amount_compare=Float.parseFloat(element_current.get("amount").toString())-Float.parseFloat(element_pre.get("amount").toString());
					element_current.put("times_pre", element_pre.get("times"));
					element_current.put("amount_pre", element_pre.get("amount"));
					element_current.put("times_compare", times_compare);
					element_current.put("amount_compare", amount_compare);
					existFlag=1;
				}
				
			}
			if(existFlag==0) {
				element_current.put("times_pre", 0);
				element_current.put("amount_pre", 0);
				element_current.put("times_compare", element_current.get("times"));
				element_current.put("amount_compare", element_current.get("amount"));
			}
		}
		
		for (LinkedHashMap<String, Object> element_pre : arr_pre) {
			int existFlag = 0;
			for (LinkedHashMap<String, Object> element_current : arr_current) {
				String n_pre=(String) element_pre.get("product_name");
				String n_current=(String) element_current.get("product_name");
				if(n_current.equals(n_pre)) {
					existFlag=1;
				}
				
			}
			if(existFlag==0) {
				LinkedHashMap<String, Object> newHM=new LinkedHashMap<>();
				long times_compare=0-Long.parseLong((String) element_pre.get("times"));
				float amount_compare=0-Float.parseFloat((String) element_pre.get("amount"));
				newHM.put("product_name", element_pre.get("product_name"));
				newHM.put("times", 0);
				newHM.put("amount", 0);
				newHM.put("times_pre", element_pre.get("times"));
				newHM.put("amount_pre", element_pre.get("amount"));
				newHM.put("times_compare", times_compare);
				newHM.put("amount_compare", amount_compare);
				arr_current.add(newHM);
			}
		}
		
		
		
		
		return arr_current;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getTypeTotalMonth(
			String start, String end) {
		// TODO Auto-generated method stub
		return em.getTypeTotalMonth(start,end);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getProductMonthPerformance(String product, String start,
			String end) {
		// TODO Auto-generated method stub
		return em.getProductMonthPerformance(product,start,end);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getProductClientDetail(String product, String start, String end) {
		// TODO Auto-generated method stub
		String start_pre=this.compareDate(start);
		String end_pre=this.compareDate(end);
		ArrayList<LinkedHashMap<String, Object>> arr_cur=em.getProductClientDetail(product,start,end);
		ArrayList<LinkedHashMap<String, Object>> arr_pre=em.getProductClientDetail(product,start_pre,end_pre);
		for(LinkedHashMap<String, Object> e_cur : arr_cur) {
			int existFlag=0;
			for(LinkedHashMap<String, Object> e_pre : arr_pre) {
				if(e_cur.get("clientId").equals(e_pre.get("clientId"))) {
					long times_compare=(long)e_cur.get("times")-(long)e_pre.get("times");
					float amount_compare=Float.parseFloat(e_cur.get("amount").toString())-Float.parseFloat(e_pre.get("amount").toString());
					e_cur.put("times_pre", e_pre.get("times"));
					e_cur.put("amount_pre", e_pre.get("amount"));
					e_cur.put("times_compare", times_compare);
					e_cur.put("amount_compare", amount_compare);
					existFlag=1;
				}
			}
			if(existFlag==0) {
				e_cur.put("times_pre", 0);
				e_cur.put("amount_pre",0);
				e_cur.put("times_compare", e_cur.get("times"));
				e_cur.put("amount_compare", e_cur.get("amount"));
			}
		}
		
		
		for(LinkedHashMap<String, Object> e_pre : arr_pre) {
			int existFlag=0;
			for(LinkedHashMap<String, Object> e_cur : arr_cur) {
				if(e_cur.get("clientId").equals(e_pre.get("clientId"))) {
					existFlag=1;
				}
			}
			if(existFlag==0) {
				long times_compare=0-(long)e_pre.get("times");
				float amount_compare=0-Float.parseFloat(e_pre.get("amount").toString());
				LinkedHashMap<String, Object> newHM=new LinkedHashMap<>();
				newHM.put("branchId", e_pre.get("branchId"));
				newHM.put("branchName", e_pre.get("branchName"));
				newHM.put("clientId", e_pre.get("clientId"));
				newHM.put("clientName", e_pre.get("clientName"));
				newHM.put("product_name", e_pre.get("product_name"));
				newHM.put("times", 0);
				newHM.put("amount", 0);
				newHM.put("times_pre", e_pre.get("times"));
				newHM.put("amount_pre", e_pre.get("amount"));
				newHM.put("times_compare", times_compare);
				newHM.put("amount_compare", amount_compare);
				arr_cur.add(newHM);
			}
		}
		return arr_cur;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getUnitDetail(
			String start, String end) {
		// TODO Auto-generated method stub
		 ArrayList<String> products = this.getExchangeProduct();
		String start_pre = this.compareDate(start);
		String end_pre = this.compareDate(end);

		ArrayList<LinkedHashMap<String, Object>> arr_cur = em.getUnitDetail(products, start, end);
		ArrayList<LinkedHashMap<String, Object>> arr_pre = em.getUnitDetail(products, start_pre, end_pre);
		for(LinkedHashMap<String, Object> e_cur : arr_cur) {
			int existFlag=0;
			for(LinkedHashMap<String, Object> e_pre : arr_pre) {
				if(e_cur.get("branchId").equals(e_pre.get("branchId"))) {
					long times_compare=(long)e_cur.get("times")-(long)e_pre.get("times");
					float amount_compare=Float.parseFloat(e_cur.get("amount").toString())-Float.parseFloat(e_pre.get("amount").toString());
					e_cur.put("times_pre", e_pre.get("times"));
					e_cur.put("amount_pre", e_pre.get("amount"));
					e_cur.put("times_compare", times_compare);
					e_cur.put("amount_compare", amount_compare);
					existFlag=1;
				}
			}
			if(existFlag==0) {
				e_cur.put("times_pre", 0);
				e_cur.put("amount_pre",0);
				e_cur.put("times_compare", e_cur.get("times"));
				e_cur.put("amount_compare", e_cur.get("amount"));
			}
		}
		
		
		for(LinkedHashMap<String, Object> e_pre : arr_pre) {
			int existFlag=0;
			for(LinkedHashMap<String, Object> e_cur : arr_cur) {
				if(e_cur.get("branchId").equals(e_pre.get("branchId"))) {
					existFlag=1;
				}
			}
			if(existFlag==0) {
				long times_compare=0-(long)e_pre.get("times");
				float amount_compare=0-Float.parseFloat(e_pre.get("amount").toString());
				LinkedHashMap<String, Object> newHM=new LinkedHashMap<>();
				newHM.put("branchId", e_pre.get("branchId"));
				newHM.put("branchName", e_pre.get("branchName"));
				newHM.put("times", 0);
				newHM.put("amount", 0);
				newHM.put("times_pre", e_pre.get("times"));
				newHM.put("amount_pre", e_pre.get("amount"));
				newHM.put("times_compare", times_compare);
				newHM.put("amount_compare", amount_compare);
				arr_cur.add(newHM);
			}
		}
		return arr_cur;
	}
	
	

	
	@Override
	public ArrayList<String> getExchangeProduct() {
		// TODO Auto-generated method stub
		return em.getExchangeProduct();
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getUnitMonth(String unit, String start, String end) {
		// TODO Auto-generated method stub
		ArrayList<String> products = this.getExchangeProduct();
		return em.getUnitMonth(unit,products,start,end);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getUnitProduct(String unit, String start, String end) {
		// TODO Auto-generated method stub
		ArrayList<String> products = this.getExchangeProduct();
		this.getTotalExchangePerformance(start, end);
		ArrayList<LinkedHashMap<String, Object>> arr_em = em.getUnitProduct(unit,products,start,end);
		float total=0;
		for(LinkedHashMap<String, Object> element: arr_em) {
			BigDecimal bd= (BigDecimal) element.get("amount");
			total=total+ bd.floatValue();
		}
		int n=0;
		for(LinkedHashMap<String, Object> element: arr_em) {
			BigDecimal bd= (BigDecimal) element.get("amount");
			element.put("percent",  bd.floatValue()/total*100);
//			element.put("id",  n++);
		}
		return arr_em;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getUnitClientList(String unit, String start, String end) {
		// TODO Auto-generated method stub
		 ArrayList<String> products = this.getExchangeProduct();
		String start_pre = this.compareDate(start);
		String end_pre = this.compareDate(end);

		ArrayList<LinkedHashMap<String, Object>> arr_cur = em.getUnitClientList(unit,products, start, end);
		ArrayList<LinkedHashMap<String, Object>> arr_pre = em.getUnitClientList(unit,products, start_pre, end_pre);
		for(LinkedHashMap<String, Object> e_cur : arr_cur) {
			int existFlag=0;
			for(LinkedHashMap<String, Object> e_pre : arr_pre) {
				if(e_cur.get("clientId").equals(e_pre.get("clientId"))) {
					long times_compare=(long)e_cur.get("times")-(long)e_pre.get("times");
					float amount_compare=Float.parseFloat(e_cur.get("amount").toString())-Float.parseFloat(e_pre.get("amount").toString());
					e_cur.put("times_pre", e_pre.get("times"));
					e_cur.put("amount_pre", e_pre.get("amount"));
					e_cur.put("times_compare", times_compare);
					e_cur.put("amount_compare", amount_compare);
					existFlag=1;
				}
			}
			if(existFlag==0) {
				e_cur.put("times_pre", 0);
				e_cur.put("amount_pre",0);
				e_cur.put("times_compare", e_cur.get("times"));
				e_cur.put("amount_compare", e_cur.get("amount"));
			}
		}
		
		
		for(LinkedHashMap<String, Object> e_pre : arr_pre) {
			int existFlag=0;
			for(LinkedHashMap<String, Object> e_cur : arr_cur) {
				if(e_cur.get("clientId")!=null && e_cur.get("clientId").equals(e_pre.get("clientId"))) {
					existFlag=1;
				}
			}
			if(existFlag==0) {
				long times_compare=0-(long)e_pre.get("times");
				float amount_compare=0-Float.parseFloat(e_pre.get("amount").toString());
				LinkedHashMap<String, Object> newHM=new LinkedHashMap<>();
				newHM.put("branchId", e_pre.get("branchId"));
				newHM.put("branchName", e_pre.get("branchName"));
				newHM.put("clientId", e_pre.get("clientId"));
				newHM.put("clientName", e_pre.get("clientName"));
				newHM.put("product", e_pre.get("product"));
				newHM.put("times", 0);
				newHM.put("amount", 0);
				newHM.put("times_pre", e_pre.get("times"));
				newHM.put("amount_pre", e_pre.get("amount"));
				newHM.put("times_compare", times_compare);
				newHM.put("amount_compare", amount_compare);
				arr_cur.add(newHM);
			}
		}
		return arr_cur;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getClientDetail(String start, String end) {
		// TODO Auto-generated method stub
		ArrayList<String> products = this.getExchangeProduct();
		String start_pre = this.compareDate(start);
		String end_pre = this.compareDate(end);

		ArrayList<LinkedHashMap<String, Object>> arr_cur = em.getClientDetail(products, start, end);
		ArrayList<LinkedHashMap<String, Object>> arr_pre = em.getClientDetail(products, start_pre, end_pre);
		for(LinkedHashMap<String, Object> e_cur : arr_cur) {
			int existFlag=0;
			for(LinkedHashMap<String, Object> e_pre : arr_pre) {
				if(e_cur.get("clientId").equals(e_pre.get("clientId"))) {
					long times_compare=(long)e_cur.get("times")-(long)e_pre.get("times");
					float amount_compare=Float.parseFloat(e_cur.get("amount").toString())-Float.parseFloat(e_pre.get("amount").toString());
					e_cur.put("times_pre", e_pre.get("times"));
					e_cur.put("amount_pre", e_pre.get("amount"));
					e_cur.put("times_compare", times_compare);
					e_cur.put("amount_compare", amount_compare);
					existFlag=1;
				}
			}
			if(existFlag==0) {
				e_cur.put("times_pre", 0);
				e_cur.put("amount_pre",0);
				e_cur.put("times_compare", e_cur.get("times"));
				e_cur.put("amount_compare", e_cur.get("amount"));
			}
		}
		
		//
		for(LinkedHashMap<String, Object> e_pre : arr_pre) {
			int existFlag=0;
			for(LinkedHashMap<String, Object> e_cur : arr_cur) {
				if(e_cur.get("clientId")!=null && e_cur.get("clientId").equals(e_pre.get("clientId"))) {
					existFlag=1;
				}
			}
			if(existFlag==0) {
				long times_compare=0-(long)e_pre.get("times");
				float amount_compare=0-Float.parseFloat(e_pre.get("amount").toString());
				LinkedHashMap<String, Object> newHM=new LinkedHashMap<>();
				newHM.put("branchId", e_pre.get("branchId"));
				newHM.put("branchName", e_pre.get("branchName"));
				newHM.put("clientId", e_pre.get("clientId"));
				newHM.put("clientName", e_pre.get("clientName"));
				newHM.put("product", e_pre.get("product"));
				newHM.put("times", 0);
				newHM.put("amount", 0);
				newHM.put("times_pre", e_pre.get("times"));
				newHM.put("amount_pre", e_pre.get("amount"));
				newHM.put("times_compare", times_compare);
				newHM.put("amount_compare", amount_compare);
				arr_cur.add(newHM);
			}
		}
		return arr_cur;
	}
	


	

}
