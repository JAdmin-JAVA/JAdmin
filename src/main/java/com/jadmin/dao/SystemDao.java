package com.jadmin.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jadmin.modules.controller.base.BaseAbstractController;
import com.jadmin.modules.dao.base.BaseBusinessDao;
import com.jadmin.modules.exception.BusinessException;
import com.jadmin.modules.util.DictinfoUtils;
import com.jadmin.modules.util.encode.Encode;
import com.jadmin.vo.entity.base.ConfigVO;
import com.jadmin.vo.entity.base.DictinfoVO;
import com.jadmin.vo.entity.base.DictkindVO;
import com.jadmin.vo.entity.base.UserVO;
import com.jadmin.vo.enumtype.YesNo;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:系统dao
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Transactional @Repository
@Slf4j
public class SystemDao extends BaseBusinessDao{
    
    /**
     * 系统设置通过code获得值
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ConfigVO> getALLConfigVO() {
        Query query = getEntityManager().createQuery("from ConfigVO");
        List<ConfigVO> list = query.getResultList();
        return list;
    }
    
    /**
     * 更新登陆信息
     * @param ip
     * @param userId
     * @return
     */
    public int upLastLogin(String ip, String userId) {
        String sql = "update sys_user set lastLoginIp = ?, lastLoginTime = NOW(), loginCount = (loginCount + 1) where userId= ? ";
        return getJdbcTemplate().update(sql, new Object[] {ip, userId});
    }

    /**
     * 获取所有的字典
     *
     * @param student
     */
    @SuppressWarnings("unchecked")
    public List<DictkindVO> getDictkinds() {
        List<DictkindVO> dictionfos = getEntityManager().createQuery(" from DictkindVO where billStatus = 1 ").getResultList();
        // 給据level将数据词典弄成树形结构
        DictinfoUtils.initDictkindByLevel(dictionfos);
        return dictionfos;
    }

    /**
     * 給据hql或者sql缓存数据
     *
     * @param hsql
     * @param type
     * @return
     */
    public List<?> getCacheDataByHSql(String hsql, String type) {
        if (StringUtils.isBlank(type)) {
            type = hsql.toLowerCase().contains("select") ? "sql" : "hql";
        }
        if ("hql".equals(type)) {
            return getEntityManager().createQuery(hsql).getResultList();
        }
        return getJdbcTemplate().queryForList(hsql);
    }

    /**
     * 登陆的具体操作
     *
     * @param account
     * @param password
     * @return
     */
    @SuppressWarnings("unchecked")
    public UserVO login(String account, String password) {
        log.info("用户尝试登陆：" + account);
        password = Encode.encode(password, true);
        Query query = getEntityManager().createQuery(" from UserVO where account = ? and billStatus = 1 and isDelete = 0 ");
        query.setParameter(0, account);
        List<UserVO> list = query.getResultList();
        if (list.isEmpty()) {
            throw new BusinessException("账号不存在！");
        }
        UserVO vo = list.get(0);
        if (!password.equals(vo.getPassword())) {
            throw new BusinessException("用户名密码错误！");
        }
        if (vo.getRole() == null) {
            throw new BusinessException("该用户未分配角色，无法登陆！");
        }
        if (vo.getRole().getBillStatus().equals(YesNo.NO.getShort().toString())) {
            throw new BusinessException("该用户分配的角色已被停用，无法登陆！");
        }
        return vo;
    }

    /**
     * 系统设置通过code获得值
     *
     * @param code
     * @return
     */
    @SuppressWarnings("unchecked")
    public ConfigVO getConfigVO(String code) {
        Query query = getEntityManager().createQuery("from ConfigVO where code = ?");
        query.setParameter(0, code);
        List<ConfigVO> list = query.getResultList();
        if (list.isEmpty()) {
            throw new BusinessException("code不存在：" + code);
        }
        ConfigVO configVo = list.get(0);
        return configVo;
    }

    /**
     * 动态获取数据词典的值
     *
     * @param sql
     */
    public List<DictinfoVO> getDictinfosBysql(String sql, HttpServletRequest request) {
        if (request != null) {
            UserVO user = BaseAbstractController.getCurUser(request.getSession());
            if (user != null) {
                sql = sql.replace("${org.seq}", user.getOrg().getSeq());
                sql = sql.replace("${orgId}", user.getOrg().getOrgId());
            }
        }
        List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql);
        List<DictinfoVO> dictinfos = new ArrayList<DictinfoVO>();
        for (Map<String, Object> map : list) {
        	String rId = (String) map.get("rId");
            String id = (String) map.get("id");
            String nocheck = (String) map.get("nocheck");
            DictinfoVO vo = new DictinfoVO();
            vo.setName((String) map.get("name"));
            vo.setDictinfoFid((String) map.get("pId"));
            if(StringUtils.isBlank(rId)) {
            	rId = id;
            }
            vo.setCode(rId);
            vo.setPrimaryKey(id);
            vo.setNocheck("true".equals(nocheck) || "1".equals(nocheck));
            dictinfos.add(vo);
        }
        return dictinfos;
    }

    /**
     * 通过userId查询用户的密码
     */
    @SuppressWarnings("unchecked")
    public UserVO queryPwdForUserId(String userId, String password) {
        Query query = getEntityManager().createQuery(" from UserVO where userId = ? ");
        query.setParameter(0, userId);
        List<UserVO> list = query.getResultList();
        UserVO vo = list.get(0);
        return vo;
    }

    /**
     * 获取最大的机构编号
     * @return
     */
    public String getOrgMaxCode() {
        String sql = "SELECT MAX(CODE) FROM SYS_ORG WHERE BILLSTATUS = '1'";
        return getJdbcTemplate().queryForObject(sql, String.class);
    }
	
	/**
	 * 获取备忘笺
	 * @param userId
	 * @param count
	 */
	public List<?> getMemorandum(String userId, int first, int count) {
		String hql = " from MemorandumVO where operatorId = '" + userId + "' and isDelete = 0 order by operateTime desc ";
		return queryPageList(hql, first, count);
	}
}