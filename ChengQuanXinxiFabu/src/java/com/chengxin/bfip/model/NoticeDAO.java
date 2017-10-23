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
public class NoticeDAO extends BaseDataAccessObject {

    private static String VIEW = "Notices_v";
    
    // 系统消息类型
    public static final int NOTICE_TYPE_FEEDBACK = 1;    // 评价
    public static final int NOTICE_TYPE_AUTH = 2;  // 认证

    // 系统消息状态
    public static final int NOTICE_ST_NEW = 1;      // 未读
    public static final int NOTICE_ST_READ = 2;    // 已读
    
    public void insert(Notice notice) {
        DBModelUtil.processSecure(Notice.class.getName(), notice, DBModelUtil.C_SECURE_TYPE_ENCRYPT);
        
        this.getHibernateTemplate().save(notice);
    }

    public void update(Notice notice) {
        DBModelUtil.processSecure(Notice.class.getName(), notice, DBModelUtil.C_SECURE_TYPE_ENCRYPT);
        
        this.getHibernateTemplate().update(notice);
    }
    
    public void delete(Notice notice) {
        DBModelUtil.processSecure(Notice.class.getName(), notice, DBModelUtil.C_SECURE_TYPE_ENCRYPT);
        
        this.getHibernateTemplate().delete(notice);
    }
    
    public void delete(int id) {
        this.getHibernateTemplate().delete(get(id));
    }
    
    public Notice get(int id) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("FROM Notice ");
        stringBuffer.append("WHERE id = :id ");

        List list = (List)this.getHibernateTemplate().findByNamedParam(
                    stringBuffer.toString(),
                    new String[]{"id"},
                    new Object[]{id});

        stringBuffer = null;

        if (list.size() > 0) {
        	Notice notice = (Notice)list.get(0);
            
            DBModelUtil.processSecure(Notice.class.getName(), notice, DBModelUtil.C_SECURE_TYPE_DECRYPT);
            
            return notice;
        }

        return null;
    }
    
    public Notice getDetail(int id) {
        
        JSONObject filterParamObject = new JSONObject();
        JSONArray equalParamArray = new JSONArray();
        JSONObject equalParam = new JSONObject();
        
        equalParam.put("id", id);
        equalParamArray.add(equalParam);
        filterParamObject.put("equal_param", equalParamArray);
        
        List<Notice> resultList = this.search(filterParamObject);
        
        if(resultList.size() > 0) {
        	return resultList.get(0);
        }
        else {
        	return null;	
        }
    }
    
    public Notice getDetail(String where) {
        
        List<Notice> resultList = this.search(null, where);
        
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
        
        if(filterParamObject != null) {
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
        }
                
        if(isCountSQL) {
            sql.appendSQL("SELECT COUNT(*)");
        } else {
            sql.appendSQL("SELECT id, kind, kind_name, content, occur_time, status, status_name ");
        }
        
        sql.appendSQL(" FROM " + VIEW);
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
    
    public List<Notice> search(JSONObject filterParamObject) {
    	return search(filterParamObject, "");
    }
    
    public List<Notice> search(JSONObject filterParamObject, String extraWhere) {
    
        Session session = SessionFactoryUtils.getNewSession(this.getHibernateTemplate().getSessionFactory());
        Transaction transaction = session.beginTransaction();
        
        SQLWithParameters sql = _makeSearchQuery(false, filterParamObject, extraWhere);

        Query query = session.createSQLQuery(sql.getSQL());
        
        DBModelUtil.fillParameter(sql, query);

        List queryList = null; try { queryList = query.list(); } catch(Exception e) {e.printStackTrace();}
        List<Notice> resultList = new ArrayList<Notice>();
        
        if (queryList != null) {
            int listSize = queryList.size();
            
            for(int i = 0; i < listSize; i++) {
                Object[] objectArray = (Object[])queryList.get(i);
                
                Notice row = new Notice();
                
                row.setId(CommonUtil.toIntDefault(objectArray[0]));
                row.setKind(CommonUtil.toIntDefault(objectArray[1]));
                row.setKindName(CommonUtil.toStringDefault(objectArray[2]));
                row.setContent(CommonUtil.toStringDefault(objectArray[3]));
                row.setOccurTime(CommonUtil.toStringDefault(objectArray[4]));
                row.setStatus(CommonUtil.toIntDefault(objectArray[5]));
                row.setStatusName(CommonUtil.toStringDefault(objectArray[6]));
                
                DBModelUtil.processSecure(Notice.class.getName(), row, DBModelUtil.C_SECURE_TYPE_DECRYPT);

                resultList.add(row);
            }
        }
        
        transaction.commit();
        
        if(session.isOpen()) {session.close();}
        
        return resultList;
    }
    
}