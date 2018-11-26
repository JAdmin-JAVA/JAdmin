package com.jadmin.modules.dao.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.jadmin.modules.annotation.column.SearchColumn;
import com.jadmin.modules.annotation.list.DeleteMode;
import com.jadmin.modules.exception.BusinessException;
import com.jadmin.modules.itf.GeneralOperatUtils;
import com.jadmin.modules.itf.RunnableWithReturn;
import com.jadmin.modules.util.SpringContext;
import com.jadmin.vo.fundation.base.AbstractValueObject;
import com.jadmin.vo.fundation.base.QueryArgVO;
import com.jadmin.vo.fundation.tool.Page;

/** 
 * @Title:web框架
 * @Description:业务最终父接口实现类，提供所有系统级通用的公共方法
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Repository("baseDao")
public class BaseBusinessDao extends GeneralOperatUtils{

    /** 日志对象 */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    
    /**
     * 获取 jdbcTemplate
     * @return
     */
    public JdbcTemplate getJdbcTemplate() {
    	return jdbcTemplate;
	}
    
    /**
     * 获取 Session（增删改操作严禁在控制层直接调用该方法使用）
     * @return
     */
    public EntityManager getEntityManager() {
    	return entityManager;
	}
    
	/**
	 * 删除数据
	 * @param object
	 * @return 
	 */
	@Transactional
	public void delete(AbstractValueObject object) {
		getEntityManager().remove(object);
		getEntityManager().flush();
	}
    
	/**
	 * 添加数据
	 * @param object
	 * @return 
	 */
	@Transactional
	public Serializable save(AbstractValueObject object) {
		return getEntityManager().merge(object);
	}
    
	/**
	 * 添加或修改数据
	 * @param object
	 * @return 
	 */
	@Transactional
	public void saveOrUpdate(AbstractValueObject object) {
		if(StringUtils.isEmpty(object.getPrimaryKey())){
			object.setPrimaryKey(null);
			getEntityManager().persist(object);
		}else {
			getEntityManager().merge(object);
		}
	}
	
	/**
	 * 删除数据
	 * @param object
	 */
	@Transactional
	public String deleteAll(DeleteMode deleteMode, Class<? extends AbstractValueObject> clazz, String ids) {
		List<AbstractValueObject> list = new ArrayList<AbstractValueObject>();
		for (String id : ids.split(",")) {
			AbstractValueObject abstractValueObject;
			// 如果还是删除的话，不查询这个对象
			if(deleteMode.value() == DeleteMode.DELETE){
				try {
					abstractValueObject = (AbstractValueObject) getEntityManager().find(clazz, id);
					list.add(abstractValueObject);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			// 如果需要修改字段的话，先查询出来这个对象
			}else{
				abstractValueObject = (AbstractValueObject) getEntityManager().find(clazz, id);
				abstractValueObject.set(deleteMode.updateKey(), deleteMode.updateValue());
			}
		}
		for (AbstractValueObject abstractValueObject : list) {
			if(deleteMode.value() == DeleteMode.DELETE){
				getEntityManager().remove(abstractValueObject);
			}else{
				getEntityManager().merge(abstractValueObject);
			}
		}
		getEntityManager().flush();
		return list.size() + "个";
	}
	
	/**
	 * 检查某个表某个字段的唯一性
	 * @param student
	 */
	@Transactional
	public void checkUniqueByHql(Class<?> clazz, String column, Object value, String id, String memo) {
		if(value == null){
			return;
		}
		// 判断是vo是否有isDelete属性
		String hql = " from " + clazz.getName() + " where " + column + " = ? ";
		try {
			Field filed = clazz.getDeclaredField("isDelete");
			if(filed != null){
				hql += " and isDelete = 0 ";
			}
		} catch (NoSuchFieldException | SecurityException e) {
			// 吃掉异常
		}
		Query queryObject = getEntityManager().createQuery(hql);
		queryObject.setParameter(0, value.toString());
		List<?> list = queryObject.getResultList();
		if(list.isEmpty()){
			return;
		}
		if(list.size() == 1){
			AbstractValueObject vo = (AbstractValueObject) list.get(0);
			if(StringUtils.isNotBlank(id) && id.equals(vo.getPrimaryKey())){
				return;
			}
		}
		throw new BusinessException(memo);
	}
    
	/**
	 * 修改数据
	 * @param object
	 */
	@Transactional
	public void update(AbstractValueObject object) {
		getEntityManager().merge(object);
	}
    
    /**
     * getOID 取值对象的pk
     * @return String
     */
    public String getOID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 将map转换成对象
     * @param args
     * @return
     */
    public List<QueryArgVO> getQueryArgVOs(Map<String, Object> args){
    	List<QueryArgVO> argVOs = new ArrayList<QueryArgVO>();
    	for(Map.Entry<String, Object> entry : args.entrySet()){
    		argVOs.add(new QueryArgVO(entry.getKey(), entry.getValue()));
    	}
    	return argVOs;
    }
    
    /**
     * 查询某个对象的个数
     * @param clazz
     * @param hql
     * @return
     */
    public int queryCount(String hql) {
    	return queryCount(hql, new ArrayList<QueryArgVO>());
    }
    public int queryCount(String hql, Map<String, Object> args) {
    	return queryCount(hql, getQueryArgVOs(args));
    }
    public int queryCount(String hql, List<QueryArgVO> args) {
        if(hql.toLowerCase().startsWith("select ")){
        	hql = "select count(*) " + hql.substring(hql.toLowerCase().indexOf("from"));
        }else{
        	hql = "select count(*) " + hql;
        }
        // 去掉 order by
        if(hql.toLowerCase().contains("order by")){
        	hql = hql.substring(0, hql.toLowerCase().indexOf("order by"));
        }
        List<?> list = getQuery(hql, args).getResultList();
        if(list.size() == 0){
            return 0;
        }
        return ((Long) list.get(0)).intValue();
    }

    /**
     * pageQuery
     * 通过起始行数进行分页查询
     * @param hql String
     * @param args Map<String, Object>  模拟mybates的 where 语句的if，else标签
     * @param first int
     * @param max int
     * @return List
     */
    public List<?> queryPageList(final String hql, final int first, final int max) {
        return queryPageList(hql, new ArrayList<QueryArgVO>(), first, max);
    }
    public List<?> queryPageList(final String hql, final Map<String, Object> args, final int first, final int max) {
        return queryPageList(hql, getQueryArgVOs(args), first, max);
    }
    public List<?> queryPageList(final String hql, final List<QueryArgVO> args, final int first, final int max) {
    	Query queryObject = getQuery(hql, args);
        queryObject.setFirstResult(first);
        queryObject.setMaxResults(max);
        return queryObject.getResultList();
    }

    /**
     * queryList
     * @param hql String
     * @return List
     */
    public List<?> queryList(final String hql) {
    	Query queryObject = getEntityManager().createQuery(hql);
        return queryObject.getResultList();
    }

    /**
     * 获取分页
     * @param pageNo
     * @param pageSize
     * @param hql
     * @param args
     * @return
     */
    protected <T> Page<T> getPage(int pageNo, int pageSize, String hql) {
    	return getPage(pageNo, pageSize, hql, new ArrayList<QueryArgVO>());
	}
    
    /**
     * 获取分页
     * @param pageNo
     * @param pageSize
     * @param hql
     * @param args
     * @return
     */
    protected <T> Page<T> getPage(int pageNo, int pageSize, String hql, Map<String, Object> args) {
    	return getPage(pageNo, pageSize, hql, getQueryArgVOs(args));
    }
    protected <T> Page<T> getPage(int pageNo, int pageSize, String hql, List<QueryArgVO> args) {
    	Page<T> page = new Page<T>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		queryPage(page, hql, args);
		return page;
	}
	
	/**
	 * mysql sql分页
	 * @param pageNo
	 * @param pageSize
	 * @param sql
	 * @param totalCount
	 * @param args
	 * @return
	 */
	public Page<Map<String, Object>> queryMysqlPage(int pageNo, int pageSize, String sql, boolean totalCount, Object[] args) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize);
		page.setPageNo(pageNo);

		if(totalCount){
			int querySqlCount = querySqlCount(sql.toString(), args);
			page.setTotalCount(querySqlCount);
		}
		
    	sql += " LIMIT ?, ? ";

		Object[] mark = new Object[args.length+2];
		for (int i = 0; i < args.length; i++) {
			mark[i]=args[i];
		}
		mark[mark.length-1] = pageSize;
		mark[mark.length-2] = (pageNo-1) * pageSize;
		
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql.toString(), mark);
		page.setResult(list);
		
		return page;
	}

    /**
     * pageQuery
     * 通过起始行数进行分页查询
     * @param hql String
     * @param args Map<String, Object>  模拟mybates的 where 语句的if，else标签
     * @param first int
     * @param max int
     * @return List
     */
	public void queryPage(Page<?> page, final String hql) {
    	queryPage(page, hql, new ArrayList<QueryArgVO>());
    }
	public void queryPage(Page<?> page, final String hql, final Map<String, Object> args) {
    	queryPage(page, hql, getQueryArgVOs(args));
    }
	@SuppressWarnings("unchecked")
	public void queryPage(Page<?> page, String hql, final List<QueryArgVO> args) {
		// TODO 临时处理
		hql = hql.replaceAll("uName1", "uName").replaceAll("mobileUserUName1", "mobileUser.uName").replaceAll("mobileUserUName2", "mobileUser.uName");
    	Query queryObject = getQuery(hql, args);
        queryObject.setFirstResult(page.getFirst());
        queryObject.setMaxResults(page.getPageSize());
        logger.info("first :" + page.getFirst() + ", result :" + page.getPageSize());
        page.setResult(queryObject.getResultList());
        if(page.isAutoCount()){
        	page.setTotalCount(queryCount(hql, args));
        }
    }
    
    /**
     * 模拟mybates的 where 语句的if，else标签，只负责简单的拼装，复杂的拼装请自行写hql
     * @param hql
     * @param args
     * @return
     */
    public  Query getQuery(final String hql, final List<QueryArgVO> args){
    	String finalHql = hql;
    	List<Object> argsVal = new ArrayList<Object>();
    	if(args != null && args.size() != 0){
        	String groupBy = "";
        	if(hql.contains("order by")){
        		groupBy = hql.substring(hql.indexOf("order by"));
        		finalHql = finalHql.replace(groupBy, "");
        	}
    		String where = "";
    		for (QueryArgVO entry : args) {
        		if((entry.getValue() != null && !"".equals(entry.getValue()))
        				|| entry.getType() == SearchColumn.NOT_NULL){
        			String pType = null;
        			if(entry.getType() == SearchColumn.DA){
        				pType = " > ";
        			}else if(entry.getType() == SearchColumn.DA_DENG){
        				pType = " >= ";
        			}else if(entry.getType() == SearchColumn.XIAO){
        				pType = " < ";
        			}else if(entry.getType() == SearchColumn.XIAO_DENG){
        				pType = " <= ";
        			}else if(entry.getType() == SearchColumn.EQUAL){
        				pType = " = ";
        			}else if(entry.getType() == SearchColumn.NOT_NULL){
        				pType = " <> '' ";
            			where += entry.getKey() + pType + " and ";
        			}else{
        				pType = " like ";
        			}
        			if(entry.getType() != SearchColumn.NOT_NULL){
        				where += entry.getKey() + pType + " ? and ";
            			Object value = entry.getValue();
            			if(entry.getType() == SearchColumn.LEFT){
            				value = value + "%";
            			}else if(entry.getType() == SearchColumn.RIGHT){
            				value = "%" + value;
            			}else if(entry.getType() == SearchColumn.LIKE){
            				value = "%" + value + "%";
            			}
                		argsVal.add(value);
        			}
        		}
			}
        	if(StringUtils.isNotBlank(where)){
        		if(!hql.contains("where")){
        			finalHql += " where ";
            	}else{
            		finalHql += " and ";
            	}
        		finalHql += where.substring(0, where.length() - 4);
        	}
        	finalHql += groupBy;
    	}
    	logger.info("分页查询的hql为：" + finalHql + "，传入的数据：" + args);
    	Query queryObject = getEntityManager().createQuery(finalHql);
    	for (int i = 0; i < argsVal.size(); i++) {
    		queryObject.setParameter(i, argsVal.get(i));
		}
    	return queryObject;
    }
    
    /**
	 * 通过起始行数分页查询数据,sql没有？号
	 * @param pageSize
	 * @param pageNo
	 * @param sql
	 * @return
	 */
	public Page<Map<String, Object>> querySqlPage(int pageSize, int pageNo, String sql) {
		return querySqlPage(pageSize, pageNo, sql, true, new Object[]{});
	}
	
	/**
	 * 通过起始行数分页查询数据
	 * @param pageSize 
	 * @param pageNo 
	 * @param sql 
	 * @param autoCount 是否查总数
	 * @param args sql中的？号
	 * @return
	 */
	public Page<Map<String, Object>> querySqlPage(int pageSize, int pageNo, String sql, boolean autoCount, Object[] args) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize);
		page.setPageNo(pageNo);
		StringBuffer finalSql = new StringBuffer();
		finalSql.append("SELECT * FROM (");
		finalSql.append(" SELECT a.*, ROWNUM RN FROM ( ");
		finalSql.append(sql);
		finalSql.append("  ) a WHERE ROWNUM <= ? ) WHERE RN > ? ");
		Object[] mark = new Object[args.length+2];
		for (int i = 0; i < args.length; i++) {
			mark[i]=args[i];
		}
		mark[mark.length-2] = (pageNo == 0 ?  1 : pageNo)*pageSize;
		mark[mark.length-1] = pageNo == 0 ?  1 : (pageNo-1)*pageSize;
		List<Map<String, Object>> obj = getJdbcTemplate().queryForList(finalSql.toString(),mark);
		page.setResult(obj);
		if(autoCount){
			page.setTotalCount(querySqlCount(sql,args));
		}
		return page;
	}

	/**
	 * 通过起始行数分页查询数据
	 * @param pageSize 
	 * @param pageNo 
	 * @param sql 
	 * @param autoCount 是否查总数
	 * @param args sql中的？号
	 * @return
	 */
	public Page<Map<String, Object>> querySqlSeverPage(int pageSize, int pageNo, String sql, boolean autoCount, Object[] args,String order) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize);
		page.setPageNo(pageNo);
		StringBuffer finalSql = new StringBuffer();
		
		finalSql.append(" SELECT * FROM (  ");
		finalSql.append(" SELECT *, ROW_NUMBER() OVER(ORDER BY "+order+" DESC) AS ROWID FROM (  ");
		finalSql.append(sql);
		finalSql.append(" ) AS A ) AS B ");
		finalSql.append(" WHERE ROWID BETWEEN ? AND ? ");
		Object[] mark = new Object[args.length+2];
		for (int i = 0; i < args.length; i++) {
			mark[i]=args[i];
		}
		mark[mark.length-1] = (pageNo == 0 ?  1 : pageNo)*pageSize;
		mark[mark.length-2] = pageNo == 0 ?  1 : (pageNo-1)*pageSize;
		List<Map<String, Object>> obj = getJdbcTemplate().queryForList(finalSql.toString(),mark);
		page.setResult(obj);
		if(autoCount){
			page.setTotalCount(querySqlCount(sql,args));
		}
		return page;
	}
	
	
    public int querySqlCount(String sql, Object[] args) {
    	sql = "select count(*) " + sql.substring(sql.toLowerCase().indexOf(" from"));
        // 去掉 order by
        if(sql.toLowerCase().contains("order by")){
        	sql = sql.substring(0, sql.toLowerCase().indexOf("order by"));
        }
        return  getJdbcTemplate().queryForObject(sql, args, Integer.class);
    }

    /**
     * 纯JDBC分页，与hibernate无关,传回map
     */
    protected List<Map<String, Object>> findByJdbcTemplatePagination(JdbcTemplate template, String sql, final Object[] args, int first, int max) {

        StringBuffer sb = new StringBuffer(sql);
        sb.append(" limit ");
        sb.append(first);
        sb.append(" , ");
        sb.append(max);
        sql = sb.toString();
        // logger.info("查询sql: " + sql + "------参数" + args);
        return template.queryForList(sql, args);
    }
    
    /**
     * 校验时间戳
     * @param sourceBill
     * @param ts
     * @param tableName
     * @param filed
     * @return
     */
    protected Date checkTs(String sourceBill, Date ts, String tableName, String filed) {
        String sql = " SELECT TS, NOW() NOW_DATE FROM " + tableName + " WHERE " + filed + " = ? ";
        Map<String, Object> map = getJdbcTemplate().queryForMap(sql, new Object[]{sourceBill});
        String dateStr = (String) map.get("TS");
        Date date = null;
        try {
            date = SDF.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Assert.notNull(date, "元数据错误，没有TS信息！");
        if (date.after(ts)){
        	logger.info("数据已经被修改，请刷新后重新操作！传入时间：" + SDF.format(ts) + "  查询当前时间：" + SDF.format(date));
            throw new BusinessException("数据已经被修改，请刷新后重新操作！");
        }
        Date now = (Date) map.get("NOW_DATE");
        return now;
    }
    
    /**
     * genCode 取编码
     * 这里并没有业务锁，通过表的唯一索引来进行处理。同时也可以提高查询效率。
     * @param tablename String
     * @param col String
     * @param length  initCode
     * @return String
     */
    public String genCode(final String tablename, final String col, final int initCode) {

        String sql = "select max(cast(" + col + " as signed)) from " + tablename;
        String max = (String) getJdbcTemplate().queryForObject(sql, String.class);
        int imax = (max == null ? initCode : Integer.parseInt(max) + 1);
        return imax+"";
    }

  
    /**
     * lockProcess
     * 业务锁(悲观锁)
     *
     * @param lockpk String
     * @param pk_user String
     * @param runnable Runnable
     */
    public Object lockProcessBusiness(String lockpk, String pk_user, RunnableWithReturn runnable) {

        boolean lock = false;
        try {
            lock = acquireLockBusiness(lockpk, pk_user);
            if (!lock) {
                throw new BusinessException("业务锁，请稍后重试！");
            }
            return runnable.run();
        } finally {
            if (lock) {
                freeLockBusiness(lockpk);
            }
        }
    }
    
    /**
     * freeLock 释放业务锁(悲观锁)
     * 
     * @param lockpk
     *            String
     */
    public void freeLockBusiness(final String lockpk) {
        independenceTrasactionBusiness(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus status) {
                getJdbcTemplate().update("delete from sm_lock where pk_lock='" + lockpk + "'");
                return null;
            }
        });
    }
    
    /**
     * acquireLock
     * 加业务锁(悲观锁)
     *
     * @param lockpk String
     * @param pk_user String
     * @return boolean
     */
    public boolean acquireLockBusiness(final String lockpk, final String pk_user) {
        Boolean block = (Boolean)independenceTrasactionBusiness(new TransactionCallback<Object>(){
            public Object doInTransaction(TransactionStatus status) {
                if (lockpk == null || lockpk.trim().length() == 0) {
                    throw new NullPointerException("lockpk不能为空");
                }
                @SuppressWarnings("rawtypes")
				List count = getJdbcTemplate().queryForList("select pk_lock from sm_lock where pk_lock='" + lockpk + "'");
                if (count.size() > 0) {
                    return Boolean.valueOf(false);
                }
                String ts = getTs();
                getJdbcTemplate().update("insert into sm_lock(pk_lock,pk_user,ts) values(?,?,?)", new Object[] {lockpk, pk_user, ts});
                return Boolean.valueOf(true);
            }
        });        
        return block.booleanValue();
    }

    /**
     * 进行独立事务
     * @param callback
     * @return
     */
    public Object independenceTrasactionBusiness(TransactionCallback<Object> callback) {

        PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager) SpringContext.getBean("transactionManager");
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate.execute(callback);
    }
}
