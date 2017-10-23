package com.chengxin.bfip.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.chengxin.bfip.CommonUtil;
import com.chengxin.common.BaseDataAccessObject;
import com.chengxin.common.DBModelUtil;
import com.chengxin.common.SQLWithParameters;

/**
 *
 * @author Genesys Framework
 */
public class ErrorsDAO extends BaseDataAccessObject {

	private static String VIEW = "Errors_v";

	//状态
	public static final int ERROR_ST_PROCESSING = 1;
	public static final int ERROR_ST_PASS = 2;
	public static final int ERROR_ST_NOPASS = 3;



	public void insert(Errors member) {
		DBModelUtil.processSecure(Errors.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);

		this.getHibernateTemplate().save(member);
	}

	public void update(Errors member) {
		DBModelUtil.processSecure(Errors.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);

		this.getHibernateTemplate().update(member);
	}

	public void delete(Errors member) {
		DBModelUtil.processSecure(Errors.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);

		this.getHibernateTemplate().delete(member);
	}

	public void delete(int id) {
		this.getHibernateTemplate().delete(get(id));
	}

	public Errors get(int id) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("FROM Errors ");
		stringBuffer.append("WHERE id = :id ");

		List list = (List)this.getHibernateTemplate().findByNamedParam(
				stringBuffer.toString(),
				new String[]{"id"},
				new Object[]{id});

		stringBuffer = null;

		if (list.size() > 0) {
			Errors member = (Errors)list.get(0);

			DBModelUtil.processSecure(Errors.class.getName(), member, DBModelUtil.C_SECURE_TYPE_DECRYPT);

			return member;
		}

		return null;
	}

	public Errors getDetail(int id) {

		JSONObject filterParamObject = new JSONObject();
		JSONArray equalParamArray = new JSONArray();
		JSONObject equalParam = new JSONObject();

		equalParam.put("id", id);
		equalParamArray.add(equalParam);
		filterParamObject.put("equal_param", equalParamArray);

		List<Errors> resultList = this.search(filterParamObject);

		if(resultList.size() > 0) {
			return resultList.get(0);
		}
		else {
			return null;	
		}
	}

	private SQLWithParameters _makeSearchQuery(boolean isCountSQL, JSONObject filterParamObject, String extraWhere) {

		SQLWithParameters sql = new SQLWithParameters("");
		JSONArray likeParamArray = new JSONArray();
		JSONArray equalParamArray = new JSONArray();
		int offset = -1;
		int limit = -1;
		String orderCol = "";
		String orderDir = "asc";

		if(filterParamObject.has("like_param")) {
			likeParamArray = filterParamObject.getJSONArray("like_param");	
		}
		if(filterParamObject.has("equal_param")) {
			equalParamArray = filterParamObject.getJSONArray("equal_param");	
		}
		if(filterParamObject.has("order_col")) {
			orderCol = filterParamObject.getString("order_col");	
		}
		if(filterParamObject.has("order_dir")) {
			orderDir = filterParamObject.getString("order_dir");	
		}
		if(filterParamObject.has("start")) {
			offset = filterParamObject.getInt("start");	
		}
		if(filterParamObject.has("length")) {
			limit = filterParamObject.getInt("length");	
		}

		if(isCountSQL) {
			sql.appendSQL("SELECT COUNT(*)");
		} else {
			sql.appendSQL("SELECT id , booknum , name , no_name , real_name , content , reason , status , whyis , update_time , account_id , write_time , status_name ");
		}

		sql.appendSQL(" FROM " +  VIEW);
		sql.appendSQL(" WHERE 1 ");

		for(int i=0; i<likeParamArray.size(); i++) {
			JSONObject oneParam = likeParamArray.getJSONObject(i);
			Iterator<String> itr = oneParam.keys();
			while(itr.hasNext()) {
				String key = itr.next();
				sql.appendSQL(" AND " + key + " LIKE '%" + oneParam.get(key) + "%' ");
			}
		}

		for(int i=0; i<equalParamArray.size(); i++) {
			JSONObject oneParam = equalParamArray.getJSONObject(i);
			Iterator<String> itr = oneParam.keys();
			while(itr.hasNext()) {
				String key = itr.next();
				sql.appendSQL(" AND " + key + "=" + oneParam.get(key));
			}
		}

		if(!extraWhere.isEmpty()) {
			sql.appendSQL(" AND " + extraWhere);
		}

		if(!isCountSQL && !orderCol.isEmpty()) {
			sql.appendSQL(" ORDER BY " + orderCol + " " + orderDir);
		}

		if(!isCountSQL && offset != -1 && limit != -1) {
			sql.appendSQL(" LIMIT " + offset + "," + limit);	
		}

		return sql;
	}

	public int count(JSONObject filterParamObject) {
		return count(filterParamObject, "");
	}

	public int count(JSONObject filterParamObject, String extraWhere) {

		Session session = SessionFactoryUtils.getNewSession(this.getHibernateTemplate().getSessionFactory());
		Transaction transaction = session.beginTransaction();

		SQLWithParameters sql = _makeSearchQuery(true, filterParamObject, extraWhere);

		Query query = session.createSQLQuery(sql.getSQL());

		DBModelUtil.fillParameter(sql, query);

		List list = null; try { list = query.list(); } catch(Exception e) {e.printStackTrace();}

		int result = list ==  null ? 0 : Integer.parseInt("" + list.get(0));

		transaction.commit();

		if(session.isOpen()) {session.close();}

		return result;
	}

	public List<Errors> search(JSONObject filterParamObject) {
		return search(filterParamObject, "");
	}

	public List<Errors> search(JSONObject filterParamObject, String extraWhere) {

		Session session = SessionFactoryUtils.getNewSession(this.getHibernateTemplate().getSessionFactory());
		Transaction transaction = session.beginTransaction();

		SQLWithParameters sql = _makeSearchQuery(false, filterParamObject, extraWhere);

		Query query = session.createSQLQuery(sql.getSQL());

		DBModelUtil.fillParameter(sql, query);

		List queryList = null; try { queryList = query.list(); } catch(Exception e) {e.printStackTrace();}
		List<Errors> resultList = new ArrayList<Errors>();

		if (queryList != null) {
			int listSize = queryList.size();

			for(int i = 0; i < listSize; i++) {
				Object[] objectArray = (Object[])queryList.get(i);

				Errors row = new Errors();

				row.setId(CommonUtil.toIntDefault(objectArray[0]));
				row.setBooknum(CommonUtil.toStringDefault(objectArray[1]));
				row.setName(CommonUtil.toStringDefault(objectArray[2]));
				row.setNo_name(CommonUtil.toStringDefault(objectArray[3]));
				row.setReal_name(CommonUtil.toStringDefault(objectArray[4]));
				row.setContent(CommonUtil.toStringDefault(objectArray[5]));
				row.setReason(CommonUtil.toStringDefault(objectArray[6]));
				row.setStatus(CommonUtil.toIntDefault(objectArray[7]));
				row.setWhyis(CommonUtil.toStringDefault(objectArray[8]));
				row.setUpdate_time(CommonUtil.toStringDefault(objectArray[9]));
				row.setAccount_id(CommonUtil.toIntDefault(objectArray[10]));
				row.setWrite_time(CommonUtil.toStringDefault(objectArray[11]));
				row.setStatus_name(CommonUtil.toStringDefault(objectArray[12]));



				DBModelUtil.processSecure(Errors.class.getName(), row, DBModelUtil.C_SECURE_TYPE_DECRYPT);

				resultList.add(row);
			}
		}

		transaction.commit();

		if(session.isOpen()) {session.close();}

		return resultList;
	}

}