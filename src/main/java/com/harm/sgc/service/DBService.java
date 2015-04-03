package com.harm.sgc.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DBService <T> {
	public static final Logger logger = LoggerFactory.getLogger(DBService.class);
	public List<T> select(T param);
	public int insert(T param);
	public int update(T param);
	public int delete(T param);
}
