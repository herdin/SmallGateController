package com.harm.sgc.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.harm.bean.AccessHistoryBean;
import com.harm.bean.GateBean;

@Repository
public class GateDBService implements DBService<GateBean> {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<GateBean> select(GateBean param) {
		List<GateBean> results = null;
		try {
			results = sqlSession.selectList("GateMapper.selectGate", param);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return results;
	}//END OF FUNCTION

	@Override
	public int insert(GateBean param) {
		int result = -1;
		try {
			result = sqlSession.insert("GateMapper.insertGate", param);
		} catch (DataAccessException e) {
			if(e instanceof DuplicateKeyException) {
				logger.error(e.getMessage(), e);
			} // ADD MORE EXCEPTION
		}
		return result; 
	}//END OF FUNCTION

	@Override
	public int update(GateBean param) {
		int result = -1;
		try {
			result = sqlSession.update("GateMapper.updateGate", param);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}//END OF FUNCTION

	@Override
	public int delete(GateBean param) {
		int result = -1;
		try {
			result = sqlSession.delete("GateMapper.deleteGate", param);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}//END OF FUNCTION

	public boolean isAccessable(AccessHistoryBean accessBean) {
		return false;
	}//END OF FUNCTION
	
}//END OF CLASS
