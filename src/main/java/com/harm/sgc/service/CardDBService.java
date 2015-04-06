package com.harm.sgc.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.harm.bean.CardBean;

//@Service
@Repository
public class CardDBService implements DBService<CardBean> {

	private static final Logger logger = LoggerFactory.getLogger(CardDBService.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<CardBean> select(CardBean param) {
		List<CardBean> results = null;
		try {
			results = sqlSession.selectList("CardMapper.selectCard", param);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return results;
	}//END OF FUNCTION

	@Override
	public int insert(CardBean param) {
		int result = -1;
		try {
			result = sqlSession.insert("CardMapper.insertCard", param);
		} catch (DataAccessException e) {
			if(e instanceof DuplicateKeyException) {
				logger.error(e.getMessage(), e);
			} // ADD MORE EXCEPTION
		}
		return result; 
	}//END OF FUNCTION

	@Override
	public int update(CardBean param) {
		int result = -1;
		try {
			result = sqlSession.update("CardMapper.updateCard", param);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}//END OF FUNCTION

	@Override
	public int delete(CardBean param) {
		int result = -1;
		try {
			result = sqlSession.delete("CardMapper.deleteCard", param);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}//END OF FUNCTION

}//END OF CLASS