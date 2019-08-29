package com.enixlin.jmrc.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.stereotype.Service;

import com.enixlin.jmrc.service.BaseService;

@Service
//这里一定要是抽象类，否则构造函数里this调用的就是子类实例
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	private Class<T> clazz=null;
	


	public BaseServiceImpl() {
		//通过反射来取得T的真正类型	
		ParameterizedType pt=  (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz=(Class<T>) pt.getActualTypeArguments()[0];
		
		// TODO Auto-generated constructor stub
	}



	@Override
	public T add(T obj) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public T update(T obj) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public T delete(T obj) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public T getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public T getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Boolean isExist(T obj) {
		// TODO Auto-generated method stub
		return null;
	}


	


}
