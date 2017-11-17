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
import com.chengxin.common.DateTimeUtil;
import com.chengxin.common.SQLWithParameters;
        
/**
 *
 * @author Genesys Framework
 */
public class HotsDAO extends BaseDataAccessObject {

    private static String VIEW = "Hots_v";
    
    //张号类型
    public static final int HOTS_ST_UP = 0;
    public static final int HOTS_ST_DOWN = 2;
    
    
    public void insert(Hots member) {
        DBModelUtil.processSecure(Hots.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);
        
        this.getHibernateTemplate().save(member);
    }

    public void update(Hots member) {
        DBModelUtil.processSecure(Hots.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);
        
        this.getHibernateTemplate().update(member);
    }
    
    public void delete(Hots member) {
        DBModelUtil.processSecure(Hots.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);
        
        this.getHibernateTemplate().delete(member);
    }
    
    public void delete(int id) {
        this.getHibernateTemplate().delete(get(id));
    }
    
    public Hots get(int id) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("FROM Hots ");
        stringBuffer.append("WHERE id = :id ");

        List list = (List)this.getHibernateTemplate().findByNamedParam(
                    stringBuffer.toString(),
                    new String[]{"id"},
                    new Object[]{id});

        stringBuffer = null;

        if (list.size() > 0) {
        	Hots member = (Hots)list.get(0);
            
            DBModelUtil.processSecure(Hots.class.getName(), member, DBModelUtil.C_SECURE_TYPE_DECRYPT);
            
            return member;
        }

        return null;
    }
    
    public Hots getDetail(int id) {
        
        JSONObject filterParamObject = new JSONObject();
        JSONArray equalParamArray = new JSONArray();
        JSONObject equalParam = new JSONObject();
        
        equalParam.put("id", id);
        equalParamArray.add(equalParam);
        filterParamObject.put("equal_param", equalParamArray);
        
        List<Hots> resultList = this.search(filterParamObject);
        
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
            sql.appendSQL("SELECT id , title , kind , visit_cnt , comment_cnt , remark_cnt , share_cnt , up_time , down_time "
            		+ ", content , st , account_id , write_time , kind_name , st_name, xyleixing_level1_id, xyleixing_level1_name ");
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
    
    public List<Hots> search(JSONObject filterParamObject) {
    	return search(filterParamObject, "");
    }
    
    public List<Hots> search(JSONObject filterParamObject, String extraWhere) {
    
        Session session = SessionFactoryUtils.getNewSession(this.getHibernateTemplate().getSessionFactory());
        Transaction transaction = session.beginTransaction();
        
        SQLWithParameters sql = _makeSearchQuery(false, filterParamObject, extraWhere);

        Query query = session.createSQLQuery(sql.getSQL());
        
        DBModelUtil.fillParameter(sql, query);

        List queryList = null; try { queryList = query.list(); } catch(Exception e) {e.printStackTrace();}
        List<Hots> resultList = new ArrayList<Hots>();
        
        if (queryList != null) {
            int listSize = queryList.size();
            
            for(int i = 0; i < listSize; i++) {
                Object[] objectArray = (Object[])queryList.get(i);
                
                Hots row = new Hots();
                
                row.setId(CommonUtil.toIntDefault(objectArray[0]));
                row.setTitle(CommonUtil.toStringDefault(objectArray[1]));
                row.setKind(CommonUtil.toIntDefault(objectArray[2]));
                row.setVisitCnt(CommonUtil.toIntDefault(objectArray[3]));
                row.setCommentCnt(CommonUtil.toIntDefault(objectArray[4]));
                row.setRemarkCnt(CommonUtil.toIntDefault(objectArray[5]));
                row.setShareCnt(CommonUtil.toIntDefault(objectArray[6]));
                row.setUpTime(DateTimeUtil.stringToDate(CommonUtil.toStringDefault(objectArray[7])));
                row.setDownTime(DateTimeUtil.stringToDate(CommonUtil.toStringDefault(objectArray[8])));
                row.setContent(CommonUtil.toStringDefault(objectArray[9]));
                row.setSt(CommonUtil.toIntDefault(objectArray[10]));
                row.setAccountId(CommonUtil.toIntDefault(objectArray[11]));
                row.setWriteTime(DateTimeUtil.stringToDate(CommonUtil.toStringDefault(objectArray[12])));
                row.setKindName(CommonUtil.toStringDefault(objectArray[13]));
                row.setStName(CommonUtil.toStringDefault(objectArray[14]));
                row.setXyleixingLevel1Id(CommonUtil.toIntDefault(objectArray[15]));
                row.setXyleixingLevel1Name(CommonUtil.toStringDefault(objectArray[16]));
                
                DBModelUtil.processSecure(Hots.class.getName(), row, DBModelUtil.C_SECURE_TYPE_DECRYPT);

                resultList.add(row);
            }
        }
        
        transaction.commit();
        
        if(session.isOpen()) {session.close();}
        
        return resultList;
    }
    
}