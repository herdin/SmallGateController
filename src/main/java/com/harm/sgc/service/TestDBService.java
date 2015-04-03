package com.harm.sgc.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harm.bean.CardBean;

@Service
public class TestDBService {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<HashMap<String, String>> getTestList() throws Exception {
		return sqlSession.selectList("testSqlMapper.getCardList");
	}
 	
	public List<CardBean> getCardList(CardBean param) throws Exception {
		return sqlSession.selectList("CardMapper.selectCard", param);
	}
	
	public int regCard(CardBean param) throws Exception {
		return sqlSession.insert("CardMapper.insertCard", param);
	}
	
	public int modCard(CardBean param) throws Exception {
		return sqlSession.update("CardMapper.updateCard", param);
	}
	
	public int delCard(CardBean param) throws Exception {
		return sqlSession.delete("CardMapper.deleteCard", param);
	}
	
}//END OF CLASS