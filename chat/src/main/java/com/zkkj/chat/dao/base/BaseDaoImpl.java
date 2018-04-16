package com.zkkj.chat.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.zkkj.chat.util.MybatisUtil;
import com.zkkj.chat.util.Page;

public class BaseDaoImpl<T, PK extends Serializable> implements IBaseDao<T, PK> {
	
	@Override
	public int insert(String statementName, T obj) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		int count = 0;
		try {
			count = session.insert(statementName, obj);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
			
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return count;
	}
	@Override
	public int delete(String statementName, PK primaryKey) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		int count  = 0;
		try {
			count = session.delete(statementName, primaryKey);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return count;
	}
	@Override
	public int deleteByParam(String statementName, Map obj) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		int count  = 0;
		try {
			count = session.delete(statementName, obj);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return count;
	}


	@Override
	public int update(String statementName, T entity) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		int count  = 0;
		try {
			count = session.update(statementName, entity);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return count;
	}
	@Override
	public int updateByParam(String statementName, Object obj) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		int count  = 0;
		try {
			count = session.update(statementName, obj);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return count;
	}


	@Override
	public T get(String statementName, PK primaryKey) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		T object = null;
		try {
			object =  session.selectOne(statementName, primaryKey);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return object;
	}
	
	@Override
	public T selectOneByParam(String statementName, Map obj) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		T object = null;
		try {
			object =  session.selectOne(statementName, obj);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return object;
	}

	@Override
	public List<T> selectList(String statementName) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		List<T> list  = null;
		try {
			list = session.selectList(statementName);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return list;
	}
	@Override
	public List<T> selectList(String statementName, Map obj) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		List<T> list  = null;
		try {
			list = session.selectList(statementName, obj);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return list;
	}

	@Override
	public List<T> selectList(String statementName, Map obj,
			Page page) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		List<T> list  = null;
		try {
			list = session.selectList(statementName, obj, new RowBounds(page.getStartItem(),page.getNumPerPage()));
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return list;
	}

	@Override
	public int getCount(String statementName, Object obj) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		int count = 0;
		try {
			count = session.selectOne(statementName,obj);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return count;
	}

	@Override
	public int insertBatch(String statementName, List<T> list) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		int count  = 0;
		try {
			count = session.insert(statementName, list);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return count;
	}

	@Override
	public int updateBatch(String statementName, List<T> list) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		int count  = 0;
		try {
			count = session.update(statementName, list);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return count;
	}

	@Override
	public int deleteBatch(String statementName, List<PK> list) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.getSqlSession();
		int count  = 0;
		try {
			count = session.delete(statementName, list);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			MybatisUtil.closeSqlSession();
		}
		return count;
	}

}
