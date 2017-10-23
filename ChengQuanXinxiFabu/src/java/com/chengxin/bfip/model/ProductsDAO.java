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
public class ProductsDAO extends BaseDataAccessObject {

	private static String VIEW = "Products_v";

	//张号类型
	public static final int SERVICE_BOOKTYPE_ENTERPRISE = 1;
	public static final int SERVICE_BOOKTYPE_PERSONAL = 2;


	public void insert(Products member) {
		DBModelUtil.processSecure(Products.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);

		this.getHibernateTemplate().save(member);
	}

	public void update(Products member) {
		DBModelUtil.processSecure(Products.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);

		this.getHibernateTemplate().update(member);
	}

	public void delete(Products member) {
		DBModelUtil.processSecure(Products.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);

		this.getHibernateTemplate().delete(member);
	}

	public void delete(int id) {
		this.getHibernateTemplate().delete(get(id));
	}

	public Products get(int id) {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("FROM Products ");
		stringBuffer.append("WHERE id = :id ");

		List list = (List)this.getHibernateTemplate().findByNamedParam(
				stringBuffer.toString(),
				new String[]{"id"},
				new Object[]{id});

		stringBuffer = null;

		if (list.size() > 0) {
			Products member = (Products)list.get(0);

			DBModelUtil.processSecure(Products.class.getName(), member, DBModelUtil.C_SECURE_TYPE_DECRYPT);

			return member;
		}

		return null;
	}

	public Products getDetail(int id) {

		JSONObject filterParamObject = new JSONObject();
		JSONArray equalParamArray = new JSONArray();
		JSONObject equalParam = new JSONObject();

		equalParam.put("id", id);
		equalParamArray.add(equalParam);
		filterParamObject.put("equal_param", equalParamArray);

		List<Products> resultList = this.search(filterParamObject);

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
		JSONArray dateParamArray = new JSONArray();
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
		if(filterParamObject.has("date_search_param")) {
			dateParamArray = filterParamObject.getJSONArray("date_search_param");	
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
			sql.appendSQL("SELECT id , num , booknum , enter_name  , status , name , price , pleixing_id , comment , weburl , sale_weburl , up_time , down_time "
					+ ", img_path1 , img_path2 , img_path3 , img_path4 , img_path5 ,account_id , products_write_time , status_name , akind_name , type_name ");
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

		for(int i=0; i<dateParamArray.size(); i++) {
			JSONObject oneParam = dateParamArray.getJSONObject(i);
			Iterator<String> itr = oneParam.keys();
			while(itr.hasNext()) {
				String key = itr.next();
				JSONObject val = oneParam.getJSONObject(key);
				if(val.has("from")) {
					sql.appendSQL(" AND " + key + ">='" + val.get("from") + "'");
				}
				if(val.has("to")) {
					sql.appendSQL(" AND " + key + "<='" + val.get("to") + "'");
				}
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

	public List<Products> search(JSONObject filterParamObject) {
		return search(filterParamObject, "");
	}

	public List<Products> search(JSONObject filterParamObject, String extraWhere) {

		Session session = SessionFactoryUtils.getNewSession(this.getHibernateTemplate().getSessionFactory());
		Transaction transaction = session.beginTransaction();

		SQLWithParameters sql = _makeSearchQuery(false, filterParamObject, extraWhere);

		Query query = session.createSQLQuery(sql.getSQL());

		DBModelUtil.fillParameter(sql, query);

		List queryList = null; try { queryList = query.list(); } catch(Exception e) {e.printStackTrace();}
		List<Products> resultList = new ArrayList<Products>();

		if (queryList != null) {
			int listSize = queryList.size();

			for(int i = 0; i < listSize; i++) {
				Object[] objectArray = (Object[])queryList.get(i);

				Products row = new Products();

				row.setId(CommonUtil.toIntDefault(objectArray[0]));
				row.setNum(CommonUtil.toStringDefault(objectArray[1]));
				row.setBooknum(CommonUtil.toStringDefault(objectArray[2]));
				row.setEnter_name(CommonUtil.toStringDefault(objectArray[3]));
				row.setStatus(CommonUtil.toIntDefault(objectArray[4]));
				row.setName(CommonUtil.toStringDefault(objectArray[5]));
				row.setPrice(CommonUtil.toDoubleDefault(objectArray[6]));
				row.setPleixing_id(CommonUtil.toIntDefault(objectArray[7]));
				row.setComment(CommonUtil.toStringDefault(objectArray[8]));
				row.setWeburl(CommonUtil.toStringDefault(objectArray[9]));
				row.setSale_weburl(CommonUtil.toStringDefault(objectArray[10]));
				row.setUp_time(CommonUtil.toStringDefault(objectArray[11]));
				row.setDown_time(CommonUtil.toStringDefault(objectArray[12]));
				row.setImg_path1(CommonUtil.toStringDefault(objectArray[13]));
				row.setImg_path2(CommonUtil.toStringDefault(objectArray[14]));
				row.setImg_path3(CommonUtil.toStringDefault(objectArray[15]));
				row.setImg_path4(CommonUtil.toStringDefault(objectArray[16]));
				row.setImg_path5(CommonUtil.toStringDefault(objectArray[17]));
				row.setAccount_id(CommonUtil.toIntDefault(objectArray[18]));
				row.setProduct_write_time(CommonUtil.toStringDefault(objectArray[19]));
				row.setStatus_name(CommonUtil.toStringDefault(objectArray[20]));
				row.setAkind_name(CommonUtil.toStringDefault(objectArray[21]));
				row.setType_name(CommonUtil.toStringDefault(objectArray[22]));


				DBModelUtil.processSecure(Products.class.getName(), row, DBModelUtil.C_SECURE_TYPE_DECRYPT);

				resultList.add(row);
			}
		}

		transaction.commit();

		if(session.isOpen()) {session.close();}

		return resultList;
	}

}