package com.enixlin.jmrc.service;

import java.util.List;

/*
 * 通用业务处理接口
 * */
public interface BaseService<T> {
    public T add(T obj);

    public T update(T obj);

    public T delete(T obj);

    public List<T> getAll();

    public T getById(Long id);

    public T getByName(String name);
    
    public Boolean isExist(T obj);
}
