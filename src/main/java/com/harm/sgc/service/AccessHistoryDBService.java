package com.harm.sgc.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.harm.bean.AccessHistoryBean;

@Repository
public class AccessHistoryDBService implements DBService<AccessHistoryBean> {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<AccessHistoryBean> select(AccessHistoryBean param) {
		List<AccessHistoryBean> results = null;
		try {
			results = sqlSession.selectList("AccessHistoryMapper.selectAccessHistory", param);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return results;
	}//END OF FUNCTION
	
	@Override
	public int insert(AccessHistoryBean param) {
		int result = -1;
		try {
			result = sqlSession.insert("AccessHistoryMapper.insertAccessHistory", param);
		} catch (DataAccessException e) {
			if(e instanceof DuplicateKeyException) {
				logger.error(e.getMessage(), e);
			} // ADD MORE EXCEPTION
		}
		return result; 
	}//END OF FUNCTION

	@Override
	public int update(AccessHistoryBean param) {
		try {
			throw new DataAccessException("update is not allowed in AccessHistoryDBService.") { private static final long serialVersionUID = 1L;};
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return -1;
	}//END OF FUNCTION

	@Override
	public int delete(AccessHistoryBean param) {
		int result = -1;
		try {
			result = sqlSession.delete("AccessHistoryMapper.deleteAccessHistory", param);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}//END OF FUNCTION

}//END OF CLASS