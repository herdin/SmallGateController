package com.harm.sgc.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.harm.bean.GateBean;

public class GateDBService implements DBService<GateBean> {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<GateBean> select(GateBean param) {
		return null;
	}//END OF FUNCTION

	@Override
	public int insert(GateBean param) {
		return 0;
	}//END OF FUNCTION

	@Override
	public int update(GateBean param) {
		return 0;
	}//END OF FUNCTION

	@Override
	public int delete(GateBean param) {
		return 0;
	}//END OF FUNCTION

}//END OF CLASS
