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
public class AccountDAO extends BaseDataAccessObject {

    private static String VIEW = "Accounts_v";
    
    // 账号类型
    public static final int ACCOUNT_TYPE_PERSONAL = 1;    // 个人账户
    public static final int ACCOUNT_TYPE_ENTERPRISE = 2;  // 企业账户

    // 企业类型
    public static final int ENTER_TYPE_PERSONAL = 1;      // 企业
    public static final int ENTER_TYPE_ENTERPRISE = 2;    // 个体户

    // 审核状态
    public static final int TEST_ST_READY = 1;            // 审核中
    public static final int TEST_ST_PASSED = 2;           // 审核通过
    public static final int TEST_ST_UNPASSED = 3;         // 审核未通过

    // 禁用状态
    public static final int BAN_ST_UNBANED = 1;             // 未禁用
    public static final int BAN_ST_BANED = 2;           // 已禁用
    
    
    public void insert(Account member) {
        DBModelUtil.processSecure(Account.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);
        
        this.getHibernateTemplate().save(member);
    }

    public void update(Account member) {
        DBModelUtil.processSecure(Account.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);
        
        this.getHibernateTemplate().update(member);
    }
    
    public void delete(Account member) {
        DBModelUtil.processSecure(Account.class.getName(), member, DBModelUtil.C_SECURE_TYPE_ENCRYPT);
        
        this.getHibernateTemplate().delete(member);
    }
    
    public void delete(int id) {
        this.getHibernateTemplate().delete(get(id));
    }
    
    public Account get(int id) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("FROM Account ");
        stringBuffer.append("WHERE id = :id ");

        List list = (List)this.getHibernateTemplate().findByNamedParam(
                    stringBuffer.toString(),
                    new String[]{"id"},
                    new Object[]{id});

        stringBuffer = null;

        if (list.size() > 0) {
        	Account member = (Account)list.get(0);
            
            DBModelUtil.processSecure(Account.class.getName(), member, DBModelUtil.C_SECURE_TYPE_DECRYPT);
            
            return member;
        }

        return null;
    }
    
    public Account getDetail(int id) {
        
        JSONObject filterParamObject = new JSONObject();
        JSONArray equalParamArray = new JSONArray();
        JSONObject equalParam = new JSONObject();
        
        equalParam.put("id", id);
        equalParamArray.add(equalParam);
        filterParamObject.put("equal_param", equalParamArray);
        
        List<Account> resultList = this.search(filterParamObject);
        
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
            sql.appendSQL("SELECT id, akind, account, code, useful, elect, logo, enter_kind, enter_name, xyleixing_id, addr, weburl, weixin, main_job, cert_image "
            		+ ", comment, recommend, boss_name, boss_job, boss_mobile, boss_weixin"
            		+ ", test_status, ban_status, write_time, test_status_name, ban_status_name, enter_kind_name ");
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
    
    public List<Account> search(JSONObject filterParamObject) {
    	return search(filterParamObject, "");
    }
    
    public List<Account> search(JSONObject filterParamObject, String extraWhere) {
    
        Session session = SessionFactoryUtils.getNewSession(this.getHibernateTemplate().getSessionFactory());
        Transaction transaction = session.beginTransaction();
        
        SQLWithParameters sql = _makeSearchQuery(false, filterParamObject, extraWhere);

        Query query = session.createSQLQuery(sql.getSQL());
        
        DBModelUtil.fillParameter(sql, query);

        List queryList = null; try { queryList = query.list(); } catch(Exception e) {e.printStackTrace();}
        List<Account> resultList = new ArrayList<Account>();
        
        if (queryList != null) {
            int listSize = queryList.size();
            
            for(int i = 0; i < listSize; i++) {
                Object[] objectArray = (Object[])queryList.get(i);
                
                Account row = new Account();
                
                row.setId(CommonUtil.toIntDefault(objectArray[0]));
                row.setAkind(CommonUtil.toIntDefault(objectArray[1]));
                row.setAccount(CommonUtil.toStringDefault(objectArray[2]));
                row.setCode(CommonUtil.toStringDefault(objectArray[3]));
                row.setUseful(CommonUtil.toIntDefault(objectArray[4]));
                row.setElect(CommonUtil.toIntDefault(objectArray[5]));
                row.setLogo(CommonUtil.toStringDefault(objectArray[6]));
                row.setEnterKind(CommonUtil.toIntDefault(objectArray[7]));
                row.setEnterName(CommonUtil.toStringDefault(objectArray[8]));
                row.setXyleixingId(CommonUtil.toIntDefault(objectArray[9]));
                row.setAddr(CommonUtil.toStringDefault(objectArray[10]));
                row.setWeburl(CommonUtil.toStringDefault(objectArray[11]));
                row.setWeixin(CommonUtil.toStringDefault(objectArray[12]));
                row.setMainJob(CommonUtil.toStringDefault(objectArray[13]));
                row.setCertImage(CommonUtil.toStringDefault(objectArray[14]));
                row.setComment(CommonUtil.toStringDefault(objectArray[15]));
                row.setRecommend(CommonUtil.toStringDefault(objectArray[16]));
                row.setBossName(CommonUtil.toStringDefault(objectArray[17]));
                row.setBossJob(CommonUtil.toStringDefault(objectArray[18]));
                row.setBossMobile(CommonUtil.toStringDefault(objectArray[19]));
                row.setBossWeixin(CommonUtil.toStringDefault(objectArray[20]));
                row.setTestStatus(CommonUtil.toIntDefault(objectArray[21]));
                row.setBanStatus(CommonUtil.toIntDefault(objectArray[22]));
                row.setWriteTime(CommonUtil.toStringDefault(objectArray[23]));
                row.setTestStatusName(CommonUtil.toStringDefault(objectArray[24]));
                row.setBanStatusName(CommonUtil.toStringDefault(objectArray[25]));
                row.setEnterKindName(CommonUtil.toStringDefault(objectArray[26]));
                
                DBModelUtil.processSecure(Account.class.getName(), row, DBModelUtil.C_SECURE_TYPE_DECRYPT);

                resultList.add(row);
            }
        }
        
        transaction.commit();
        
        if(session.isOpen()) {session.close();}
        
        return resultList;
    }

    
}