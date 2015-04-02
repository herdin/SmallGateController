package com.harm.sgc;

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
	
	public List<HashMap<String, String>> getCardList() throws Exception {
		return sqlSession.selectList("testSqlMapper.getCardList");
	}//END OF getCardList()
 	
	public List<CardBean> getCardList2(CardBean param) throws Exception {
		return sqlSession.selectList("CardMapper.getCardList", param);
	}
	
	public int regCard(CardBean param) throws Exception {
		return sqlSession.insert("CardMapper.regCard", param);
	}
	
	public int modCard(CardBean param) throws Exception {
		return sqlSession.update("CardMapper.modCard", param);
	}
	
	public int delCard(CardBean param) throws Exception {
		return sqlSession.delete("CardMapper.delCard", param);
	}
	
}//END OF CLASS