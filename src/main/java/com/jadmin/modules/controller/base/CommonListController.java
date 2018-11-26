package com.jadmin.modules.controller.base;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jadmin.modules.annotation.AdminPage;
import com.jadmin.modules.annotation.MethodLog;
import com.jadmin.modules.annotation.column.FormColunm;
import com.jadmin.modules.annotation.column.InitDefaultColunm;
import com.jadmin.modules.annotation.column.OrderByColumn;
import com.jadmin.modules.annotation.column.SearchColumn;
import com.jadmin.modules.annotation.column.SelectShow;
import com.jadmin.modules.annotation.column.TableColumn;
import com.jadmin.modules.annotation.column.UnEditColunm;
import com.jadmin.modules.annotation.column.UniqueColunm;
import com.jadmin.modules.annotation.list.AdminPageButton;
import com.jadmin.modules.annotation.list.ButtonTablePage;
import com.jadmin.modules.annotation.list.DeleteMode;
import com.jadmin.modules.annotation.list.FileConfig;
import com.jadmin.modules.annotation.list.Power;
import com.jadmin.modules.annotation.list.SearchMode;
import com.jadmin.modules.annotation.list.SinglePage;
import com.jadmin.modules.annotation.list.TableHql;
import com.jadmin.modules.annotation.list.Tree;
import com.jadmin.modules.exception.AlertBusinessException;
import com.jadmin.modules.exception.BusinessException;
import com.jadmin.modules.util.AdminPageUtils;
import com.jadmin.modules.util.ClassUtil;
import com.jadmin.modules.util.DictinfoUtils;
import com.jadmin.modules.util.VOUtil;
import com.jadmin.modules.util.encode.Encode;
import com.jadmin.util.file.ExcelUtil;
import com.jadmin.vo.entity.base.DictkindVO;
import com.jadmin.vo.entity.base.RoleVO;
import com.jadmin.vo.entity.base.UserVO;
import com.jadmin.vo.entity.ref.OrgRefVO;
import com.jadmin.vo.enumtype.JavaType;
import com.jadmin.vo.fundation.base.AbstractValueObject;
import com.jadmin.vo.fundation.base.Null;
import com.jadmin.vo.fundation.base.QueryArgVO;
import com.jadmin.vo.fundation.controller.AdminPageButtonVO;
import com.jadmin.vo.fundation.controller.AdminPageFormColumnVO;
import com.jadmin.vo.fundation.controller.AdminPageSearch2VO;
import com.jadmin.vo.fundation.controller.AdminPageSearchColumnVO;
import com.jadmin.vo.fundation.controller.AdminPageSearchModeVO;
import com.jadmin.vo.fundation.controller.AdminPageSelectShowVO;
import com.jadmin.vo.fundation.controller.AdminPageTableColumnVO;
import com.jadmin.vo.fundation.tool.Page;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:有增删改列表的控制层基类
 * @Copyright:JAdmin (c) 2018年11月26日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Controller
@Slf4j
public class CommonListController<T extends AbstractValueObject> extends BaseAbstractController {

    /**
     * 当数据发生改变后的操作，包括增删改
     */
    public void afterDataChange() {
    	
    }

    /**
     * 保存单据 前的操作
     *
     * @param vo
     * @param request
     */
    public void beforeEditSave(T vo, HttpServletRequest request) {
    	
    }

    /**
     * 保存单据（仅限于新增） 前的操作
     *
     * @param vo
     * @param request
     */
    public void beforeAddSave(T vo, HttpServletRequest request) {

    }

    /**
     * 默认数据保存操作
     *
     * @param colunm
     * @param request
     */
    public String initDefaultColunmValue(String colunm, HttpServletRequest request) {
        return null;
    }

    /**
     * 审核功能保存 前的操作
     *
     * @param vo
     * @param isPass  是否审核通过
     * @param request
     */
    public void beforeCheckSave(T vo, boolean isPass, HttpServletRequest request) {

    }

    /**
     * 保存单据 后的操作
     *
     * @param vo
     * @param request
     */
    public void afterEditSave(T vo, HttpServletRequest request) {

    }

    /**
     * 删除数据 前的操作
     *
     * @param ids
     * @param request
     * @return 返回 false，不再执行删除操作
     */
    public boolean beforeDelete(List<String> ids, HttpServletRequest request) {
        return true;
    }

    /**
     * 删除数据 后的操作
     *
     * @param ids
     * @param request
     */
    public void afterDelete(List<String> ids, HttpServletRequest request) {

    }

    /**
     * 跳转到添加页面前的操作
     *
     * @param vo
     */
    public void beforeToAdd(HttpServletRequest request) {

    }

    /**
     * 跳转到编辑页面前的操作
     *
     * @param vo
     */
    public void beforeToUpdate(T vo, HttpServletRequest request) {

    }

    /**
     * 跳转到详情页面前的操作
     *
     * @param vo
     */
    public void beforeToShow(T vo, HttpServletRequest request) {

    }

    /**
     * 跳转到添加/编辑页面前的操作
     *
     * @param vo
     */
    public void beforeToAddOrUpdate(HttpServletRequest request) {

    }

    /**
     * 列表界面，查询数据前的操作
     *
     * @param request
     */
    public void beforeQueryPage(HttpServletRequest request) {

    }

    /**
     * 給据IDColunm和id获取hql，如有需要可重写
     *
     * @param idColunm
     * @param id
     * @return
     */
    public String getIdColumnHql(String idColunm, String id) {
        return idColunm + " = '" + id + "'";
    }

    /**
     * 获取树形结构点击接点 后的删选条件，如有需要可重新
     * @param id 当前选中节点的id
     * @param level 当前选中节点的level，从0开始
     * @param pId 当前选中节点的父节点id
     * @param tree
     * @return
     */
    public String getTreeHqlWhere(String id, String level, String pId, Tree tree) {
        return tree.fieldKey() + " like '" + id + "%'";
	}
    
    /**
     * 获取tree的json数据
     * Map 里面的字段有：
     *  id 必填 节点id
     *  name 必填 节点名称
     *  pId 选填 所属父节点 如果顶级节点，设置为0
     *  rId 选填 如果rId存在，id只负责和pId对应，选择后保存的值是rId
     *  nocheck 选填 如果为false或者0，该节点不能被选中
     * @param request
     * @return
     */
    public List<Map<String, Object>> getTreeData(HttpServletRequest request) {
		return new ArrayList<>();
	}

    /**
     * 导航栏显示上一个对象的名称，如有需要可重写
     *
     * @param request
     * @param class1
     * @param idColunm
     * @param id
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void getBarLastPageName(Class<? extends CommonListController> class1,
                                    HttpServletRequest request, String idColunm, String id) {
        //TODO:当前不太健壮
        ButtonTablePage btPage = class1.getAnnotation(ButtonTablePage.class);
        if (btPage == null) {
            return;
        }
        Class<?> class2 = getVOClass((Class<? extends CommonListController>) btPage.page());
        AbstractValueObject vo = (AbstractValueObject) systemDao.getEntityManager().find(class2, id);
        if (vo != null) {
            request.setAttribute("mainName", vo.getMainName());
        }
    }

    /**
     * 保存单据
     *
     * @param vo
     */
    public void saveOrUpdate(T vo) {
        systemDao.saveOrUpdate(vo);
    }

    /**
     * 分页查询完的操作
     *
     * @param page
     * @param request
     */
    public void afterQueryPage(Page<T> page, HttpServletRequest request) {

    }

    /**
     * hql的where条件
     *
     * @return
     */
    public String getHqlWhere(HttpServletRequest request) {
        return null;
    }

    /**
     * 处理org的属性
     *
     * @param vo
     * @return
     */
    protected void hiddleOrg(T vo, HttpServletRequest request) {
        OrgRefVO org = new OrgRefVO();
        if (StringUtils.isBlank(vo.getPrimaryKey())) {
            org.setOrgId(getCurUser(request.getSession()).getOrg().getOrgId());
        } else {
            org.setOrgId(request.getParameter("org.orgId"));
        }
        vo.set("org", org);
    }

    /**
     * 添加或修改的具体操作
     *
     * @param vo
     * @param request
     * @return
     */
    @MethodLog(content = {"errorMsg", "数据"})
    @RequestMapping({"/edit"})
//	public String edit(T vo, HttpServletRequest request) {
    public String edit(T vo, MultipartHttpServletRequest request) {
        String url = "";
        try {
            url = request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/"));
            // 记录日志类型
            request.setAttribute(SYS_LOG_TYPE, StringUtils.isBlank(vo.getPrimaryKey()) ? "新增" : "修改");
            // 初始化vo的一些默认值
            if (StringUtils.isBlank(vo.getPrimaryKey())) {
                initDefaultColunm(vo, request);
            }
            // 初始化request中通过baseWhere传输的属性，无论新增或者修改
            String baseWhere = request.getParameter("baseWhere");
            // 从新回到id=?的url中
            String idVal = "";
            if (StringUtils.isNotBlank(baseWhere)) {
                ButtonTablePage btpAn = this.getClass().getAnnotation(ButtonTablePage.class);
                for (String str : baseWhere.split(";")) {
                    String strss[] = str.split("=");
                    vo.set(strss[0], strss[1]);
                    if (btpAn != null && btpAn.idColunm().equals(strss[0])) {
                        idVal = strss[1];
                    }
                }
                idVal = "?id=" + idVal;
            }
            // 效验@UniqueColunm注解中定义的数据唯一性
            checkUniqueColunm((AbstractValueObject) vo.clone());
            // 将声明加密的字段，进行加密
            encodeHiddle(vo);
            //在新增保存之前
            if(StringUtils.isBlank(vo.getPrimaryKey())) {
            	beforeAddSave(vo, request);
            }
            //在编辑保存之前
            beforeEditSave(vo, request);
            // 如果是审核保存，调用beforeCheckSave方法
            String common_check_status = request.getParameter("common_check_status");
            if (StringUtils.isNotBlank(common_check_status)) {
                beforeCheckSave(vo, common_check_status.equals("pass"), request);
            }
//			vo = hiddleUpdateVO(vo); 该功能暂时不弄了
            // 动态获取保存时@FormColunm type为img的字段  上传图片 生成路径  设置字段值为生成的图片路径
            vo = this.uploadFile(this.getClass(), vo, request);
            saveOrUpdate(vo);
            // 如果有编辑页面的表格，设置从属关系
            saveEditTable(vo, request);
            // 当数据发生改变后的操作，包括增删改
            afterEditSave(vo, request);
            afterDataChange();
            request.setAttribute("data", vo);
            request.setAttribute("数据", vo.toJson());
            // 默认跳转到/toAll，如果是单页面的话，跳转到当前页面，如果定义了nrTab为true，跳转到编辑页面
            String urlType = "/getAll";
            SinglePage singlePage = this.getClass().getAnnotation(SinglePage.class);
            String nrTab = request.getParameter("nrTab");
            if (singlePage != null && StringUtils.isNotBlank(singlePage.value())) {
                urlType = "/toUpdate";
            } else if ("true".equals(nrTab)) {
                idVal = "";
                urlType = "/toShow?nrTab=true&closeNowFrame=true&id=" + vo.getPrimaryKey();
            } else if ("true".equals(request.getParameter("hideTitle"))) {
            	return "public/close-layer";
            }
            return redirect(url + urlType + idVal);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            request.setAttribute("errorMsg", getMessage(e));
            request.setAttribute("data", vo);
            request.setAttribute("数据", vo.toJson());
            request.setAttribute("id", vo.getPrimaryKey());
            String urlType = StringUtils.isBlank(vo.getPrimaryKey()) ? "toAdd" : "toUpdate";
            return forward(url + "/" + urlType);
        }
    }

    /**
     * 如果有编辑页面的表格，设置从属关系
     *
     * @param vo
     * @param request
     */
    private void saveEditTable(T vo, MultipartHttpServletRequest request) {
        Field[] fields = ClassUtil.getDeclaredFields(getClass());
        for (Field field : fields) {
            FormColunm foJ = field.getAnnotation(FormColunm.class);
            if (foJ != null && StringUtils.isNotBlank(foJ.idColunm())) {
                // 将临时的外键修改正确
                String tsId = request.getParameter(foJ.idColunm() + "TsId");
                if (StringUtils.isNotBlank(tsId)) {
                    String sql = "update " + VOUtil.getTableNameByVOClass(getVOClass(foJ.table())) + " set " + foJ.idColunm() + " = '" + vo.getPrimaryKey() + "' where " + foJ.idColunm() + " = '" + tsId + "'";
                    systemDao.getJdbcTemplate().update(sql);
                }
            }
        }
    }

    /**
     * 加载需要特殊加载的js
     *
     * @param class1
     * @param request
     */
    private void hiddleJs(@SuppressWarnings("rawtypes") Class<? extends CommonListController> class1,
                          HttpServletRequest request) {
        FileConfig cmJ = class1.getAnnotation(FileConfig.class);
        if (cmJ != null) {
            request.setAttribute("jsEditFile", cmJ.editJs());
            request.setAttribute("jsListFile", cmJ.listJs());
        }
    }

    /**
     * 将声明加密的字段，进行加密
     *
     * @param vo
     */
    private void encodeHiddle(T vo) {
        Field[] fields = ClassUtil.getDeclaredFields(getClass());
        for (Field field : fields) {
            FormColunm foJ = field.getAnnotation(FormColunm.class);
            if (foJ != null && StringUtils.isNotBlank(foJ.encode())) {
                // 如果edit声明了hidden，说明放到hidden的input中了，不要要加密了
                if (StringUtils.isNotBlank(vo.getPrimaryKey()) && foJ.edit().equals("hidden")) {
                    continue;
                }
                String val = (String) vo.get(field.getName());
                if (StringUtils.isNotBlank(val)) {
                    vo.set(field.getName(), Encode.encode(val, true));
                }
            }
        }
    }

    /**
     * 效验@UniqueColunm注解中定义的数据唯一性
     */
    private void checkUniqueColunm(AbstractValueObject vo) {
        Field[] fields = ClassUtil.getDeclaredFields(getClass());
        for (Field field : fields) {
            UniqueColunm unC = field.getAnnotation(UniqueColunm.class);
            if (unC != null) {
                FormColunm foJ = field.getAnnotation(FormColunm.class);
                if (foJ == null) {
                    throw new BusinessException("字段控制层未配置FormColunm注解：" + field.getName());
                }
                systemDao.checkUniqueByHql(vo.getClass(), field.getName(), vo.get(field.getName()), vo.getPrimaryKey(), foJ.value() + "已存在，无法保存！");
            }
        }
    }

    /**
     * 获得初始化vo中的字段
     */
    @SuppressWarnings("rawtypes")
    public List<String> getInitDefaultColunm(Class<? extends CommonListController> class1) {
        Field[] fields = ClassUtil.getDeclaredFields(class1);
        List<String> list = new ArrayList<String>();
        for (Field field : fields) {
            InitDefaultColunm iidc = field.getAnnotation(InitDefaultColunm.class);
            FormColunm fc = field.getAnnotation(FormColunm.class);
            // 如果FormColunm注解中edit声明为hidden，也放到hidden的inputs里面
            if (iidc != null || (fc != null && fc.edit().equals("hidden"))) {
                String name = field.getName();
                if (StringUtils.isNotBlank(iidc.column())) {
                    name = iidc.column();
                }
                list.add(name);
            }
        }
        return list;
    }

    /**
     * 初始化vo的一些默认值
     *
     * @param vo
     */
    public void initDefaultColunm(T vo, HttpServletRequest request) {
        Field[] fields = ClassUtil.getDeclaredFields(getClass());
        for (Field field : fields) {
            InitDefaultColunm iidc = field.getAnnotation(InitDefaultColunm.class);
            if (iidc != null) {
                String fName = field.getName();
                if (StringUtils.isNotBlank(iidc.column())) {
                    fName = iidc.column();
                }
                // 如果声明sysConfigCode，从系统设置中获得该值
                if (StringUtils.isNotBlank(iidc.sysConfigCode())) {
                    vo.set(fName, systemDao.getConfigVO(iidc.sysConfigCode()).getCoValue());
                    continue;
                }
                Object val = null;
                if (iidc.methodGet()) {
                    val = methodGetInvoke(field);
                } else if (iidc.javaType().getCode().equals(JavaType.Integer.getCode())) {
                    val = Integer.parseInt(iidc.value());
                } else if (iidc.javaType().getCode().equals(JavaType.BigDecimal.getCode())) {
                    val = new BigDecimal(iidc.value());
                } else if (!"INITDEFAULTCOLUNM".equals(iidc.value())) {
                    val = iidc.value();
                } else {
                    val = getDefaultColunmValue(fName, request);
                }
                vo.set(fName, val);
            }
        }
    }

    /**
     * methodGet方法获得值
     *
     * @param field
     * @return
     */
    private Object methodGetInvoke(Field field) {
        return ClassUtil.methodGetInvoke(field, this.getClass());
    }

    /**
     * 获得搜索的初始化的属性，包括Java类型和搜索的初始化值等属性
     */
    public List<AdminPageSearchColumnVO> getSearchDefaultValue(HttpServletRequest request) {
        List<AdminPageSearchColumnVO> searchColumnList = new ArrayList<AdminPageSearchColumnVO>();
        Field[] fields = ClassUtil.getDeclaredFields(getClass());
        for (Field field : fields) {
            SearchColumn scAn = field.getAnnotation(SearchColumn.class);
            TableColumn tcAn = field.getAnnotation(TableColumn.class);
            FormColunm fcAn = field.getAnnotation(FormColunm.class);
            if (scAn != null) {
                AdminPageSearchColumnVO searchColumnVO = new AdminPageSearchColumnVO();
                String column = getColumnNameBy3Anno(scAn, tcAn, fcAn, field);
                searchColumnVO.setColumn(column);
                searchColumnVO.setJavaType(scAn.javaType());
                searchColumnVO.setName(scAn.value());
                searchColumnVO.setSelectCode(scAn.selectCode());
                String like = request.getParameter("type.search." + column);
                searchColumnVO.setLike(StringUtils.isBlank(like) ? SearchColumn.INIT : Integer.parseInt(like));
                if (StringUtils.isNotBlank(scAn.selectCode())
                        || (tcAn != null && StringUtils.isNotBlank(tcAn.selectCode()))
                        || (fcAn != null && StringUtils.isNotBlank(fcAn.selectCode()))) {
                    searchColumnVO.setLike(SearchColumn.EQUAL);
                }
                Object val = scAn.initDefault();
                if (scAn.initDefaultMethodGet()) {
                    val = methodGetInvoke(field);
                }
                searchColumnVO.setInitDefaultValue(val);
                if (val != null && StringUtils.isNotBlank(val.toString())) {
                    request.setAttribute("search." + field.getName().toString(), val);
                }
                searchColumnList.add(searchColumnVO);
            } else if (tcAn != null && tcAn.search()) {
                AdminPageSearchColumnVO searchColumnVO = new AdminPageSearchColumnVO();
                String column = getColumnNameBy3Anno(scAn, tcAn, fcAn, field);
                searchColumnVO.setColumn(column);
                searchColumnVO.setJavaType(tcAn.javaType());
                String like = request.getParameter("type.search." + column);
                searchColumnVO.setLike(StringUtils.isBlank(like) ? SearchColumn.INIT : Integer.parseInt(like));
                if (StringUtils.isNotBlank(tcAn.selectCode())) {
                    searchColumnVO.setLike(SearchColumn.EQUAL);
                }
                searchColumnList.add(searchColumnVO);
            }
        }
        return searchColumnList;
    }

    /**
     * 获取列名
     *
     * @param scAn
     * @param tcAn
     * @param fcAn
     * @param field
     * @return
     */
    private String getColumnNameBy3Anno(SearchColumn scAn, TableColumn tcAn, FormColunm fcAn, Field field) {
        String column = null;
        if (scAn != null && StringUtils.isNotBlank(scAn.column())) {
            column = scAn.column();
        } else if (tcAn != null && StringUtils.isNotBlank(tcAn.column())) {
            column = tcAn.column();
        } else if (fcAn != null && StringUtils.isNotBlank(fcAn.column())) {
            column = fcAn.column();
        } else {
            column = field.getName();
        }
        return column;
    }

    /**
     * 給据字段名，获得默认值
     *
     * @param colunm
     * @return
     */
    public String getDefaultColunmValue(String colunm, HttpServletRequest request) {
        colunm = colunm.toUpperCase();
        if (colunm.equals("OPERATETIME")) {
            return getCurDateTime();
        }
        if (colunm.equals("OPERATORID")) {
            return getCurUser(request.getSession()).getAccount();
        }
        if (colunm.equals("ORGID")) {
            return getCurUser(request.getSession()).getOrg().getOrgId();
        }
        if (colunm.equals("USERID")) {
            return getCurUser(request.getSession()).getUserId();
        }
        return initDefaultColunmValue(colunm, request);
    }


    /**
     * 删除
     *
     * @param ceshi
     * @param response
     * @return
     */
    @AdminPageButton(name = "删除", imgChar = "&#xe6e2;", color = "danger", dataType = AdminPageButton.CHECKBOX, showType = AdminPageButton.AJAX)
    @RequestMapping({"/del"})
    @MethodLog(content = {"数量"})
    public @ResponseBody
    String del(String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<String> listIds = new ArrayList<String>();
            for (String one : id.split(",")) {
                listIds.add(one);
            }
            // 处理字段为某些值时，不能修改的情况
            unEditColunmHiddle(listIds);
            // 删除前操作
            String obj = success;
            if (beforeDelete(listIds, request)) {
                DeleteMode deleteMode = this.getClass().getAnnotation(DeleteMode.class);
                if (deleteMode == null) {
                    throw new BusinessException("程序员尚未定义删除策略，无法删除！");
                }
                // 为程序员写的效验功能，TODO：可删除
                if (deleteMode.value() == DeleteMode.UPDATE) {
                    TableHql hql = this.getClass().getAnnotation(TableHql.class);
                    if (hql == null || !hql.value().contains(deleteMode.updateKey())) {
                        log.error("【请修改】配置删除策略为update，缺未在TableHql中过滤已删除的数据");
                    }
                }
                obj = delete(deleteMode, id, getVOClass());
            }
            // 当数据发生改变后的操作，包括增删改
            afterDataChange();
            // 删除数据后的操作
            afterDelete(listIds, request);
            if (obj.equals(ERROR_MSG)) {
                return obj;
            }
            request.setAttribute("数量", obj);
            return success;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return getMessage(e);
        }
    }

    /**
     * 导出功能
     *
     * @return
     */
//    @AdminPageButton(name = "导出", imgChar="&#xe644;", clickJsMethod="toExport")
    @RequestMapping({"/toExport"})
    public String toExport(HttpServletRequest request) {
        request.setAttribute("baseUrl", ClassUtil.getRequestMappingValue(this.getClass(), true));
        return getBaseEditJspUrl() + "/data-edit";
    }

    /**
     * 給据主键,获得一个数据，可处理主键不是String的情况
     *
     * @param id
     * @return
     */
    public Object getOneByIdToString(String id) {
        return getOneByIdToString(getVOClass(), id);
    }

    /**
     * 給据主键,获得一个数据，可处理主键不是String的情况
     *
     * @param voClass vo的类型
     * @param id
     * @return
     */
    public Object getOneByIdToString(Class<? extends AbstractValueObject> voClass, String id) {
        Object finalId = id;
        Class<?> idType = VOUtil.getIdType(voClass);
        if (idType.equals(Integer.class)) {
            finalId = Integer.parseInt(id);
        }
        Object data = systemDao.getEntityManager().find(voClass, (Serializable) finalId);
        return data;
    }
    
	/**
	 * 跳转到查看页面 查看按钮暂时不显示
	 * @param id
	 * @param request
	 * @return
	 */
	@AdminPageButton(name = "详情", imgChar="&#xe685;", dataType = AdminPageButton.RADIO)
	@RequestMapping({ "/toShow" })
	public String toShow(String id, HttpServletRequest request) {
		return toShow(this.getClass(), id, request);
	}
	
	/**
	 * 工具方法，跳转到查看页面 查看按钮暂时不显示
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String toShow(Class<? extends CommonListController> class1, String id, HttpServletRequest request) {
    	// 处理主键不是String类型的
		Object data = getOneByIdToString(getVOClass(class1), id);
		beforeToShow((T) data, request);
		request.setAttribute("data", data);
		request.setAttribute("baseUrl", ClassUtil.getRequestMappingValue(class1, true));
		// 动态获取查看时需要的字段
		getFormColumns(class1, request, "toShow");
		// 加载需要特殊加载的js
		hiddleJs(this.getClass(), request);
		return getBaseEditJspUrl() + "/data-show";
	}
	
	/**
	 * 处理编辑页面的id，如有需求，可重写该方法
	 * @param class1
	 * @param id
	 * @return
	 */
	public String hiddleUpdateId(Class<?> class1, String id, HttpServletRequest request){
		SinglePage singlePageA = class1.getAnnotation(SinglePage.class);
		if(singlePageA != null && StringUtils.isNotBlank(singlePageA.value())){
			if("userId".equals(singlePageA.value())){
				id = getCurUser(request.getSession()).getUserId();
			}else if("orgId".equals(singlePageA.value())){
				id = getCurUser(request.getSession()).getOrg().getOrgId();
			}else{
				id = singlePageA.value();
			}
		}
		return id;
	}
	
    /**
     * 跳转到新增页面
     *
     * @return
     */
    @AdminPageButton(name = "添加", imgChar = "&#xe600;")
    @RequestMapping({"/toAdd"})
    public String toAdd(HttpServletRequest request) {
        request.setAttribute("baseUrl", ClassUtil.getRequestMappingValue(this.getClass(), true));
        // 默认一个时间戳
        request.setAttribute("ts", System.currentTimeMillis() + "");
        // 动态获取添加/编辑时需要的字段
        getFormColumns(this.getClass(), request, "toAdd");
        // 跳转到添加/编辑页面前的操作
        beforeToAddOrUpdate(request);
        // 跳转到添加页面前的操作
        beforeToAdd(request);
        // 加载需要特殊加载的js
        hiddleJs(this.getClass(), request);
        return getBaseEditJspUrl() + "/data-edit";
    }

    /**
     * 跳转到修改页面
     *
     * @param request
     * @return
     */
    @AdminPageButton(name = "编辑", imgChar = "&#xe6df;", dataType = AdminPageButton.RADIO)
    @RequestMapping({"/toUpdate"})
    public String toUpdate(String id, HttpServletRequest request, HttpServletResponse response) {
        return toUpdateOrCheck(this.getClass(), id, "/data-edit", request, response);
    }

    /**
     * 跳转到审核页面
     *
     * @param request
     * @return
     */
    @RequestMapping({"/toCheck"})
    public String toCheck(String id, HttpServletRequest request, HttpServletResponse response) {
        return toUpdateOrCheck(this.getClass(), id, "/data-check", request, response);
    }

    /**
     * 可跳转到某个控制层下面的编辑/审核页面
     *
     * @param class1   控制层的class
     * @param id
     * @param id
     * @param jspName
     * @param response
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public String toUpdateOrCheck(Class<? extends CommonListController> class1, String id, String jspName, HttpServletRequest request, HttpServletResponse response) {
        // 处理主键不是String类型的
        Object data = request.getAttribute("data");
        if (data == null) {
            // 处理编辑页面的id
            id = hiddleUpdateId(class1, id, request);
            data = getOneByIdToString(getVOClass(class1), id);
        }
        request.setAttribute("data", data);
        request.setAttribute("baseUrl", ClassUtil.getRequestMappingValue(class1, true));
        // 动态获取添加/编辑时需要的字段
        getFormColumns(class1, request, "toUpdate");
        // 获取不能编辑的vo中的属性，放到hidden的input里面，再传过来
        request.setAttribute("hiddenInputs", getInitDefaultColunm(class1));
        // 获取当下拉框选中某个值的时候，需要隐藏字段的注解
        request.setAttribute("selectShow", getSelectShow(class1));
        // 加载需要特殊加载的js
        hiddleJs(this.getClass(), request);
        try {
            // 跳转到添加/编辑页面前的操作
            beforeToAddOrUpdate(request);
            // 跳转到编辑页面前的操作
            beforeToUpdate((T) data, request);
            // 处理字段为某些值时，不能修改的情况
            unEditColunmHiddle((T) data);
        } catch (AlertBusinessException abex) {
            // 前台用弹出框的方式处理异常，并返回到当前请求的页面
            AlertBusinessException.hiddleAlertException(request, response, request.getParameter("hideTitle"), abex.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            request.setAttribute("errorMsg", getMessage(e));
        }
        return getBaseEditJspUrl() + jspName;
    }

    /**
     * 获取当下拉框选中某个值的时候，需要显示字段的注解
     *
     * @param class1
     * @return
     */
    private Object getSelectShow(@SuppressWarnings("rawtypes") Class<? extends CommonListController> class1) {
        Field[] fields = ClassUtil.getDeclaredFields(class1);
        List<AdminPageSelectShowVO> list = new ArrayList<AdminPageSelectShowVO>();
        for (Field field : fields) {
            SelectShow sh = field.getAnnotation(SelectShow.class);
            if (sh != null) {
                for (String s : sh.value()) {
                    AdminPageSelectShowVO vo = getAdminPageSelectShowVOBySelectColumnAndValue(list, sh.selectColumn(), s);
                    if (vo == null) {
                        vo = new AdminPageSelectShowVO();
                        vo.setSelectColumn(sh.selectColumn());
                        vo.setValue(s);
                        list.add(vo);
                    }
                    vo.getShowColumn().add(field.getName());
                }
            }
        }
        return list;
    }


    /**
     * 給据selectColumn和value获取AdminPageSelectHiddleVO
     *
     * @param list
     * @param selectColumn
     * @param value
     * @return
     */
    private AdminPageSelectShowVO getAdminPageSelectShowVOBySelectColumnAndValue(
            List<AdminPageSelectShowVO> list, String selectColumn, String value) {
        for (AdminPageSelectShowVO vo : list) {
            if (selectColumn.equals(vo.getSelectColumn()) && value.equals(vo.getValue())) {
                return vo;
            }
        }
        return null;
    }

    /**
     * 处理字段为某些值时，不能修改的情况
     *
     * @param data
     */
    protected void unEditColunmHiddle(T data) {
        unEditColunmHiddle(data, null, 0);
    }

    /**
     * 处理字段为某些值时，不能删除的情况
     *
     * @param listIds
     */
    private void unEditColunmHiddle(List<String> listIds) {
        for (int i = 0; i < listIds.size(); i++) {
            unEditColunmHiddle(null, listIds.get(i), i + 1);
        }
    }

    /**
     * 处理字段为某些值时，不能修改、删除的情况
     *
     * @param data
     */
    private void unEditColunmHiddle(T data, String id, int index) {
        Field[] fields = ClassUtil.getDeclaredFields(getClass());
        for (Field field : fields) {
            UnEditColunm uec = field.getAnnotation(UnEditColunm.class);
            if (uec != null) {
                String filedName = uec.column();
                if (StringUtils.isBlank(filedName)) {
                    filedName = field.getName();
                }
                // data不为空，说明是修改操作
                if (!uec.allowUpdate() && data != null) {
                    Object filedValue = data.get(filedName);
                    unEditColunmCheck(uec, "修改", filedName, filedValue);
                }
                // id不为空，说明是删除操作
                if (!uec.allowDel() && id != null) {
                    data = (T) systemDao.getEntityManager().find(getVOClass(), id);
                    Object filedValue = data.get(filedName);
                    unEditColunmCheck(uec, "删除[第" + index + "列]", filedName, filedValue);
                }
            }
        }
    }

    private void unEditColunmCheck(UnEditColunm uec, String type, String filedName, Object filedValue) {
        if (filedValue instanceof String) {
            for (String string : uec.value()) {
                if (string.equals(filedValue)) {
                    throw new AlertBusinessException("该数据不允许" + type);
                }
            }
        } else {
            throw new BusinessException("UnEditColunm注解使用错误，目前只支持String类型，请检查"
                    + filedName + "的值是否是String类型");
        }
    }

    /**
     * 跳转到tree页面
     *
     * @param request
     * @return
     */
    @RequestMapping({"/getTree"})
    public String getTree(HttpServletRequest request) {
        Tree treeAn = getClass().getAnnotation(Tree.class);
    	// 获取要跳转的list的url
        request.setAttribute("url", request.getRequestURI().replace("/getTree", "/getAll"));
        // 获取tree的json数据
        List<Map<String, Object>> treeList = null;
        if(treeAn.methodCustom()) {
        	treeList = getTreeData(request);
        }else if(StringUtils.isNotBlank(treeAn.selectCode())){
			DictkindVO dictkind = DictinfoUtils.getDictkind(treeAn.selectCode(), request);
			treeList = dictkind.getTreeJsonList();
        }else {
        	treeList = systemDao.getJdbcTemplate().queryForList(treeAn.sql());
        }
        request.setAttribute("msunTreeData", getJsonList(treeList));
        request.setAttribute("msunTreeJsonKey", treeAn.jsonKey());
        if(treeList != null && !treeList.isEmpty()) {
        	request.setAttribute("msunTreeData_firstId", treeList.get(0).get("id"));
        }
        return getBaseListJspUrl() + "/data-tree";
    }

    /**
     * 跳转到所有列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping({"/getAll"})
    public String getAll(String pageNo, String pageSize, HttpServletRequest request, HttpServletResponse response) {
        return getAll(this.getClass(), pageNo, pageSize, request, response);
    }

    /**
     * 可直接跳转到某个控制层的获得所有列表页面
     *
     * @param class1
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String getAll(Class<? extends CommonListController> class1, String pageNo, String pageSize, HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURI();
        request.setAttribute("url", url);
        try {
            beforeQueryPage(request);
            // 获得搜索的初始化的属性，包括Java类型和搜索的初始化值等属性
            List<AdminPageSearchColumnVO> adminPageSearchColumnVOs = getSearchDefaultValue(request);
            // 处理自定义的分页大小
            TableHql tableHql = class1.getAnnotation(TableHql.class);
            if (tableHql != null && tableHql.pageSize() != -1) {
                pageSize = tableHql.pageSize() + "";
            }
            Page<?> page = queryPage(class1, request, pageNo, pageSize, getAllHqlNoOrderBy(class1, request), getOrderByHql(class1, request), adminPageSearchColumnVOs);
            if (page.result.isEmpty()) {
                request.setAttribute("msg", "暂无数据");
            }
            afterQueryPage((Page<T>) page, request);

            // 动态获取表格和搜索和orderby的字段
            getColumns(request, class1);
            // 获取页面刷新时间
            AdminPage apAn = class1.getAnnotation(AdminPage.class);
            if (apAn != null) {
                request.setAttribute("refreshTime", apAn.refreshTime());
            }
            // 获取基础url
            RequestMapping rmAn = class1.getAnnotation(RequestMapping.class);
            String baseUrl = "";
            if (rmAn != null) {
                baseUrl = rmAn.value()[0];
                request.setAttribute("baseUrl", baseUrl);
            }
            // 设置搜索策略
            request.setAttribute("searchMode", getSearchMode(class1));
            // 动态获取页面中的按钮
            List<AdminPageButtonVO> pageButtons = getPageButtons(class1, request);
            request.setAttribute("pageButtons", pageButtons);
            // 处理列表界面 第一个字段的点击url
            request.setAttribute("oneUrl", getOneUrl(pageButtons, baseUrl));
            request.setAttribute("page", page);
            if ("1".equals(request.getParameter("isDownLoad"))) {
                download(class1, request, response);
            }
            // 加载需要特殊加载的js
            hiddleJs(this.getClass(), request);
        } catch (Exception e) {
            request.setAttribute("msg", getMessage(e));
            log.error(e.getMessage(), e);
        }
        String inEdit = "true".equals(request.getParameter("inEdit")) ? "-in-edit" : "";
        return getBaseListJspUrl() + "/data-list" + inEdit;
    }

    /**
     * 获取列表界面 第一个字段的点击url
     *
     * @param pageButtons
     * @return
     */
    private String getOneUrl(List<AdminPageButtonVO> pageButtons, String baseUrl) {
        List<String> list = new ArrayList<String>();
        for (AdminPageButtonVO vo : pageButtons) {
            list.add(vo.getUrl().replace(baseUrl, ""));
        }
        if (list.contains("/toUpdate")) {
            return "/toUpdate";
        }
        // 如果有详情按钮，为详情的url
        if (list.contains("/toShow")) {
            return "/toShow";
        }
        // 如果有编辑按钮，为编辑的url
        return "";
    }

    /**
     * 获取搜索策略
     *
     * @param class1
     * @return
     */
    public AdminPageSearchModeVO getSearchMode(Class<? extends CommonListController> class1) {
        AdminPageSearchModeVO searchMode = new AdminPageSearchModeVO();
        SearchMode smAn = class1.getAnnotation(SearchMode.class);
        if (smAn != null) {
            searchMode.setDateColumn(smAn.dateColumn());
            searchMode.setDateFmt(smAn.dateFmt());
            searchMode.setDefType(smAn.defType());
            searchMode.setShowDate(StringUtils.isNotBlank(smAn.dateColumn()));
        }
        return searchMode;
    }

    /**
     * 获取页面中的按钮
     *
     * @param request
     * @return
     */
    public List<AdminPageButtonVO> getPageButtons(HttpServletRequest request) {
        return getPageButtons(this.getClass(), request);
    }

    /**
     * 获取页面中的按钮
     *
     * @param class1
     * @param request
     * @return
     */
    public List<AdminPageButtonVO> getPageButtons(@SuppressWarnings("rawtypes") Class<? extends CommonListController> class1, HttpServletRequest request) {
        List<AdminPageButtonVO> pageButtons = null;
        ButtonTablePage btPage = class1.getAnnotation(ButtonTablePage.class);
        AdminPage apAn = class1.getAnnotation(AdminPage.class);
        Tree treeAn = class1.getAnnotation(Tree.class);
        String url = request.getRequestURI();
        if(treeAn != null) {
        	url= url.replace("/getAll", "/getTree");
        }
        String className = (String) request.getAttribute("APP_BASE_CLASS_NAME");
        if (StringUtils.isNotBlank(className)) {
            url = className;
        }
        // 如果类声明ButtonTablePage注解，获取该类下面所有的按钮 TODO：目前只有声明了AdminPage页面的才有权限功能
        RoleVO role = getCurUser(request.getSession()).getRole();
        if (btPage != null) {
            request.setAttribute("baseWhere", btPage.idColunm() + "=" + request.getParameter("id"));
            //pageButtons = AdminPageUtils.findAdminPageButtonsByButtonTablePage(class1);
            pageButtons = AdminPageUtils.findAdminPageButtonsByNameAndRole(btPage.name(), role);
        } else if (apAn != null) {
            pageButtons = AdminPageUtils.findAdminPageButtonsByUrlAndRole(url, role);
        } else {
            pageButtons = AdminPageUtils.findAdminPageButtonsByClass(class1);
        }
        if ("true".equals(request.getParameter("inEdit"))) {
            //pageButtons = AdminPageUtils.findAdminPageButtonsByClass(class1);
            pageButtons = AdminPageUtils.findAdminPageButtonsByNameAndRole(btPage.name(), role);
        }
        // 处理AdminPageButton 被隐藏的情况
        List<AdminPageButtonVO> rePageButtons = new ArrayList<>();
        for (AdminPageButtonVO apb : pageButtons) {
        	if(!apb.isHide()) {
        		rePageButtons.add(apb);
        	}
		}
        return rePageButtons;
    }

    /**
     * 导出excel
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public void download(@SuppressWarnings("rawtypes") Class<? extends CommonListController> class1, HttpServletRequest request, HttpServletResponse response) {
        OutputStream os = null;
        try {
            String pageName = class1.getAnnotation(AdminPage.class).name();
            os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(pageName.getBytes("GBK"), "ISO-8859-1") + ".xls");

            // 动态导出
            Page<?> page = (Page<?>) request.getAttribute("page");
            List<AdminPageTableColumnVO> tableColumns = (List<AdminPageTableColumnVO>) request.getAttribute("tableColumns");
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            List<String> titles = new ArrayList<String>();
            List<String> fields = new ArrayList<String>();
            String[] titlesHeader = new String[2];
            for (int i = 0; i < tableColumns.size(); i++) {
                AdminPageTableColumnVO vo = tableColumns.get(i);
                if (vo.isExport()) {
                    titles.add(vo.getName());
                    fields.add(vo.getColumn());
                }
            }
            titlesHeader[0] = pageName;
            titlesHeader[1] = systemDao.getDate();
            for (Object o : page.getResult()) {
                AbstractValueObject vo = (AbstractValueObject) o;
                Map<String, Object> map = new HashMap<String, Object>();
                for (AdminPageTableColumnVO tableColumn : tableColumns) {
                    if (tableColumn.isExport()) {
                        Object d = null;
                        String field = tableColumn.getColumn().replace("o.", "");
                        if (StringUtils.isNotBlank(tableColumn.getExportColumn())) {
                            field = tableColumn.getExportColumn();
                        }
                        if (StringUtils.isBlank(tableColumn.getSelectCode())) {
                            d = vo.get(field);
                        } else {
                            d = DictinfoUtils.getDictkindNameByKey(tableColumn.getSelectCode(), vo.get(field) + "", request);
                        }
                        if (d != null) {
                            d = d + "";
                        }
                        map.put(tableColumn.getColumn(), d);
                    }
                }
                data.add(map);
            }

            byte[] buf = ExcelUtil.expMap(titles.toArray(new String[]{}), titlesHeader, fields.toArray(new String[]{}), data, 3);
            os.write(buf);
            os.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 获得查询所有的hql语句
     *
     * @param class1
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String getAllHql(Class<? extends CommonListController> class1, HttpServletRequest request) {
        return getAllHqlNoOrderBy(class1, request) + getOrderByHql(class1, request);
    }

    /**
     * 获得查询所有的hql语句（除orderby外）
     *
     * @param class1
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String getAllHqlNoOrderBy(Class<? extends CommonListController> class1, HttpServletRequest request) {
        String hql = " from  " + getVOClass(class1).getName() + " o where 1 = 1 ";
        TableHql tableHql = class1.getAnnotation(TableHql.class);
        ButtonTablePage btPage = class1.getAnnotation(ButtonTablePage.class);
        if (tableHql != null && StringUtils.isNotBlank(tableHql.value())) {
            hql += " and " + tableHql.value();
        }
        // 如果重写getHqlWhere方法，获得需要的where条件
        String hqlWhere = getHqlWhere(request);
        if (StringUtils.isNotBlank(hqlWhere)) {
            hql += " and " + hqlWhere;
        }
        // 如果类声明ButtonTablePage注解，自动添加传过来的where条件
        if (btPage != null && StringUtils.isNotBlank(btPage.idColunm())) {
            String id = request.getParameter("id");
            // 导航栏显示上一个对象的名称
            getBarLastPageName(class1, request, btPage.idColunm(), id);
            hql += " and " + getIdColumnHql(btPage.idColunm(), id);
        }
        // 添加机构权限功能
        Power power = class1.getAnnotation(Power.class);
        if (power != null) {
            hql += " and " + getPowerHqlWhere(request, power);
        }
        Tree tree = class1.getAnnotation(Tree.class);
        if (tree != null) {
        	String treeHqlWhere = getTreeHqlWhere(request.getParameter("msun_tree_id"), 
            		request.getParameter("msun_tree_level"), request.getParameter("msun_tree_pId"), tree);
        	if(StringUtils.isNotBlank(treeHqlWhere)) {
        		hql += " and " + treeHqlWhere;
        	}
        }
        // 添加树形结构的数据过滤
        if ("1".equals(request.getParameter("isDownLoad"))) {
            String exportIds = request.getParameter("exportIds");
            if (StringUtils.isNotBlank(exportIds)) {
                String[] ids = exportIds.split(",");
                StringBuffer sb = new StringBuffer();
                for (String id : ids) {
                    sb.append("'" + id + "',");
                }
                sb.deleteCharAt(sb.length() - 1);
                hql += " and " + getVOID(class1) + " in (" + sb + ")";
            }
        }
        return hql;
    }

	/**
     * 获得orderby的hql语句
     *
     * @param class1
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String getOrderByHql(Class<? extends CommonListController> class1, HttpServletRequest request) {
        String hql = "";
        TableHql tableHql = class1.getAnnotation(TableHql.class);
        // 获取用户选择的order by
        String orderBy = request.getParameter("orderBy");
        if (StringUtils.isNotBlank(orderBy)) {
            hql += " order by " + orderBy;
        } else if (tableHql != null && StringUtils.isNotBlank(tableHql.orderBy())) {
            hql += " order by " + tableHql.orderBy();
        }
        return hql;
    }

    /**
     * 获得机构权限的hql
     *
     * @param request
     * @param power
     * @return
     */
    private String getPowerHqlWhere(HttpServletRequest request, Power power) {
        String hql = power.value() == Power.USER ? power.userKey() : power.orgKey();
        UserVO userVO = getCurUser(request.getSession());
        if (power.value() == Power.ORG) {
            hql += " like '" + userVO.getOrg().getSeq() + "%' ";
        } else if (power.value() == Power.SELF_ORG) {
            hql += " = '" + userVO.getOrg().getSeq() + "' ";
        } else if (power.value() == Power.USER) {
            hql += " = '" + userVO.getUserId() + "' ";
        }
        return hql;
    }

    /**
     * 动态获取添加/编辑时需要的字段
     *
     * @param request
     * @param urlType 添加 toAdd，修改 toUpdate，查看 toShow
     * @return
     */
    @SuppressWarnings("rawtypes")
    public void getFormColumns(Class<? extends CommonListController> class1, HttpServletRequest request, String urlType) {
        List<AdminPageFormColumnVO> formColumns = new ArrayList<AdminPageFormColumnVO>();
        Field[] fields = ClassUtil.getDeclaredFields(class1);
        for (Field field : fields) {
            FormColunm foJ = field.getAnnotation(FormColunm.class);
			/*if(field.getName().equals("cars")){
				System.out.println(11);
			}*/
            if (foJ != null) {
                // 如果edit声明了hidden，并且不是添加的时候，不再显示
                if (foJ.edit().equals("hidden") && !urlType.equals("toAdd")) {
                    continue;
                }
                // 如果editShow为false，并且不是详情页面，不再显示
                if (!foJ.editShow() && !urlType.equals("toShow")) {
                    continue;
                }
                formColumns.add(getAdminPageFormColumnVO(field, foJ, urlType));
            }
        }
        request.setAttribute("formColumns", formColumns);
    }

    /**
     * 动态获取表格和搜索的字段
     *
     * @param request
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void getColumns(HttpServletRequest request, Class<? extends CommonListController> class1) {
        List<QueryArgVO> args = (List<QueryArgVO>) request.getAttribute("search");
        List<AdminPageTableColumnVO> searchColumns = new ArrayList<AdminPageTableColumnVO>();
        List<AdminPageTableColumnVO> tableColumns = new ArrayList<AdminPageTableColumnVO>();
        List<AdminPageTableColumnVO> orderByColumns = new ArrayList<AdminPageTableColumnVO>();
        Field[] fields = ClassUtil.getDeclaredFields(class1);
        for (Field field : fields) {
            // 字段包含Search注解，或者是包含TableColumn，并且search属性为true
            SearchColumn seA = field.getAnnotation(SearchColumn.class);
            TableColumn tcA = field.getAnnotation(TableColumn.class);
            FormColunm foJ = field.getAnnotation(FormColunm.class);
            OrderByColumn orA = field.getAnnotation(OrderByColumn.class);
            if (seA != null || (tcA != null && tcA.search())) {
                AdminPageTableColumnVO vo = getAdminPageTableColumnVO(field, foJ, tcA, seA, orA);
                Object val = null;
                for (QueryArgVO qA : args) {
                    if (qA.getKey().replace("o.", "").equals(vo.getColumn())) {
                        val = qA.getValue();
                    }
                }
                if (val != null) {
                    vo.setValue(val.toString());
                }
                String like = request.getParameter("type.search." + vo.getColumn());
                vo.setSearchLike(StringUtils.isBlank(like) ? SearchColumn.INIT : Integer.parseInt(like));
                vo.setColumn("search." + vo.getColumn());
                searchColumns.add(vo);
            }
            if (orA != null) {
                orderByColumns.add(getAdminPageTableColumnVO(field, foJ, tcA, seA, orA));
            }
            if (tcA != null) {
                tableColumns.add(getAdminPageTableColumnVO(field, foJ, tcA, null, null));
            }
        }
        // 2017-3-3优化搜索模块，显示2中搜索模式
        request.setAttribute("search2", new AdminPageSearch2VO(searchColumns));
        request.setAttribute("tableColumns", tableColumns);
        request.setAttribute("orderByColumns", orderByColumns);
    }

    /**
     * 給据 FormJsp注解和TableColumn注解和Search注解获得AdminPageTableColumnVO
     *
     * @param field
     * @param foJ
     * @param tcA
     * @param seA
     * @return
     */
    private static AdminPageFormColumnVO getAdminPageFormColumnVO(Field field, FormColunm foJ, String urlType) {
        AdminPageFormColumnVO vo = new AdminPageFormColumnVO();
        // 获得名字，优先获得Search注解的属性值
        if (StringUtils.isNotBlank(foJ.column())) {
            vo.setColumn(foJ.column());
        } else {
            vo.setColumn(field.getName());
        }

        // 如果selectCode不为空，type设置为select
        if (StringUtils.isNotBlank(foJ.selectCode())) {
            vo.setType("select");
            // 获取数据字典
            vo.setSelectCode(foJ.selectCode());
            // 如果 快速输入带的code，还是input类型
        } else if (StringUtils.isNotBlank(foJ.speedSelectCode())) {
            vo.setType("speedInput");
            vo.setSpeedSelectCode(foJ.speedSelectCode());
            // 如果dateFmt不为空，type设置为date
        } else if (StringUtils.isNotBlank(foJ.dateFmt()) || StringUtils.isNotBlank(foJ.maxDate())) {
            vo.setType("date");
            // 如果x,y不为空，type设置为xy
        } else if (StringUtils.isNotBlank(foJ.x()) && StringUtils.isNotBlank(foJ.y())) {
            vo.setType("xy");
            vo.setX(foJ.x());
            vo.setY(foJ.y());
        } else if (StringUtils.isNotBlank(foJ.picPath())) {
            vo.setType("upFile");
            vo.setPicPath(foJ.picPath());
        } else if (!foJ.table().equals(Null.class)) {
            vo.setType("table");
            vo.setIdColunm(foJ.idColunm());
            vo.setTable(foJ.table());
            vo.setTableUrl(ClassUtil.getRequestMappingValue(foJ.table(), false));
            vo.setColumn(vo.getTableUrl().replace("/", ""));
            vo.setTableHiddleColumn(foJ.tableHiddleColumn());
        } else {
            vo.setType(foJ.type());
        }
        // 初始化date类型的dateFmt属性
        if (foJ.type().equals("date") && StringUtils.isBlank(foJ.dateFmt())) {
            vo.setDateFmt("yyyy-MM-dd");
        }
        // 获取标签等数据
        vo.setWidthScale(foJ.widthScale());
        vo.setName(foJ.value());
        vo.setLength(foJ.length());
        vo.setDatatype(foJ.datatype());
        vo.setRequired(foJ.required());
        vo.setNullmsg(foJ.nullmsg());
        vo.setDateFmt(foJ.dateFmt());
        vo.setMaxDate(foJ.maxDate());
        vo.setEncode(foJ.encode());
        vo.setLable(foJ.lable());
        vo.setInitValue(foJ.initValue());
        vo.setSelectStyle(foJ.selectStyle());
        // edit属性，只有在修改的时候有效   添加 toAdd，修改 toUpdate，查看 toShow
        if (urlType.equals("toAdd")) {
            vo.setEdit("true");
            // 查看暂时允许编辑，没有提及按钮
        } else if (urlType.equals("toShow")) {
            vo.setEdit("true");
        } else {
            vo.setEdit(foJ.edit());
        }

        // 如果是必输字段，当datatype为空时，初始化值
        if (foJ.required()) {
            if (StringUtils.isBlank(foJ.datatype())) {
                vo.setDatatype("*1-1024");
            }
            if (StringUtils.isBlank(foJ.nullmsg())) {
                if (vo.getType().equals("select") || vo.getType().equals("date")) {
                    vo.setNullmsg("请选择" + vo.getName());
                } else {
                    vo.setNullmsg(vo.getName() + "不能为空");
                }
            }
        } else if (StringUtils.isNotBlank(foJ.datatype()) && foJ.datatype().startsWith("/")) {
            vo.setDatatype("/^$|" + foJ.datatype().substring(1, foJ.datatype().length()));
        }
        vo.setAjaxurl(foJ.ajaxurl());
        return vo;
    }

    /**
     * 給据 FormJsp注解和TableColumn注解和Search注解获得AdminPageTableColumnVO
     *
     * @param field
     * @param foJ
     * @param tcA
     * @param seA
     * @return
     */
    private static AdminPageTableColumnVO getAdminPageTableColumnVO(Field field, FormColunm foJ, TableColumn tcA, SearchColumn seA, OrderByColumn orA) {
        // Search注解中没有的属性可以从TableColumn注解中获得，TableColumn注解中没有的属性可以从FormJsp注解中获得
        AdminPageTableColumnVO vo = new AdminPageTableColumnVO();
        // 获得名字，优先获得Search注解的属性值
        if (seA != null && StringUtils.isNotBlank(seA.column())) {
            vo.setColumn(seA.column());
        } else if (tcA != null && StringUtils.isNotBlank(tcA.column())) {
            vo.setColumn(tcA.column());
        } else if (foJ != null && StringUtils.isNotBlank(foJ.column())) {
            vo.setColumn(foJ.column());
        } else {
            vo.setColumn(field.getName());
        }

        // 如果selectCode不为空，type设置为select
        if ((seA != null && StringUtils.isNotBlank(seA.selectCode()))
                || (tcA != null && StringUtils.isNotBlank(tcA.selectCode()))
                || (foJ != null && StringUtils.isNotBlank(foJ.selectCode()))
        ) {
            vo.setType("select");
        } else if (foJ != null && StringUtils.isNotBlank(foJ.type())) {
            vo.setType(foJ.type());
        }

        // 获取数据字典
        if (seA != null && StringUtils.isNotBlank(seA.selectCode())) {
            vo.setSelectCode(seA.selectCode());
        } else if (tcA != null && StringUtils.isNotBlank(tcA.selectCode())) {
            vo.setSelectCode(tcA.selectCode());
        } else if (foJ != null && StringUtils.isNotBlank(foJ.selectCode())) {
            vo.setSelectCode(foJ.selectCode());
        }

        // 获取标签
        if (seA != null && StringUtils.isNotBlank(seA.value())) {
            vo.setName(seA.value());
        } else if (tcA != null && StringUtils.isNotBlank(tcA.value())) {
            vo.setName(tcA.value());
        } else if (foJ != null && StringUtils.isNotBlank(foJ.value())) {
            vo.setName(foJ.value());
        }

        // 获取要显示的长度和是否是图片
        if (tcA != null) {
            vo.setMaxLength(tcA.maxLength());
            vo.setImg(tcA.img());
            vo.setVideoPath(tcA.videoPath());
            vo.setExportColumn(tcA.exportColumn());
            vo.setExport(tcA.export());
        }
        
        // 如果foJ的picPath存在
        if(foJ != null && StringUtils.isNotBlank(foJ.picPath())) {
            vo.setImg(true);
        }

        // 设置order by
        if (orA != null) {
            vo.setOrderBy(orA.value());
        }
        return vo;
    }

    /**
     * 返回 数据的基础vo类的class
     *
     * @return
     */
    public Class<T> getVOClass() {
        return getVOClass(this.getClass());
    }

    /**
     * 返回 数据的基础vo类的主键
     *
     * @param class1 可定义为其他控制类的类型
     * @return
     */
    public String getVOID(Class<? extends CommonListController> class1) {
        Field[] fields = getVOClass(class1).getDeclaredFields();
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                return field.getName();
            }
        }
        return null;
    }

    /**
     * 返回 数据的基础vo类的class
     *
     * @param class1 可定义为其他控制类的类型
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Class<T> getVOClass(Class<? extends CommonListController> class1) {
        ParameterizedType type = null;
        // 遍历找到voClass
        while (true) {
            if (class1.getGenericSuperclass() == null || class1.getGenericSuperclass() instanceof ParameterizedType) {
                break;
            }
            class1 = (Class<? extends CommonListController>) class1.getSuperclass();
        }
        type = (ParameterizedType) class1.getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }

    /**
     * 返回 基础的编辑页面的 jsp路径
     *
     * @return
     */
    public String getBaseEditJspUrl() {
        return getBaseJspUrlByType("edit");
    }

    /**
     * 返回 基础的列表页面的 jsp路径
     *
     * @return
     */
    public String getBaseListJspUrl() {
        return getBaseJspUrlByType("list");
    }

    /**
     * 返回 基础的 jsp路径
     *
     * @return
     */
    public String getBaseJspUrl() {
        return getBaseJspUrlByType(null);
    }

    /**
     * 給据类别 返回 基础的列表页面的 jsp路径
     *
     * @param type
     * @return
     */
    private String getBaseJspUrlByType(String type) {
        // 如果type为空，返回公共路径
        FileConfig cmJ = this.getClass().getAnnotation(FileConfig.class);
        if (StringUtils.isNotBlank(type) && cmJ != null) {
            // 如果type在注解的selfJsp，返回自己的路径
            String[] types = cmJ.selfJsp();
            for (String t : types) {
                if (type.equals(t)) {
                    return getSelfBaseJspUrl();
                }
            }
        }
        return "admin/common";
    }

    /**
     * 返回 自己的基础的 jsp路径
     * @return
     */
    public String getSelfBaseJspUrl() {
    	FileConfig anno = this.getClass().getAnnotation(FileConfig.class);
        if (anno != null && StringUtils.isNotBlank(anno.jspBaseUrl())) {
            return anno.jspBaseUrl();
        }
        String url = ClassUtil.getRequestMappingValue(this.getClass(), true);
        log.info("访问的页面为:" + url);
        if (StringUtils.isBlank(url)) {
            throw new BusinessException("未配置JspBaseUrl或RequestMapping的值");
        }
        log.info("正在访问自己的jsp，路径：" + BASE_JSPURL_NOT_HAS_REQUESTMAPPING + url);
        return BASE_JSPURL_NOT_HAS_REQUESTMAPPING + url;
    }

    /**
     * 跳转到审核页面
     *
     * @param user
     * @param request
     * @return
     */
    public String toAudit(String id, HttpServletRequest request, HttpServletResponse response) {
        return toAudit(this.getClass(), id, request, response);
    }

    /**
     * 可跳转到某个控制层下面的编辑页面
     *
     * @param class1   控制层的class
     * @param id
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public String toAudit(Class<? extends CommonListController> class1, String id, HttpServletRequest request, HttpServletResponse response) {
        // 处理主键不是String类型的
        Object data = getOneByIdToString(getVOClass(class1), id);
        request.setAttribute("data", data);
        request.setAttribute("baseUrl", ClassUtil.getRequestMappingValue(class1, true));
        // 动态获取添加/编辑时需要的字段
        getFormColumns(class1, request, "toUpdate");
        // 获取不能编辑的vo中的属性，放到hidden的input里面，再传过来
        request.setAttribute("hiddenInputs", getInitDefaultColunm(class1));
        try {
            // 跳转到添加/编辑页面前的操作
            beforeToAddOrUpdate(request);
            // 跳转到编辑页面前的操作
            beforeToUpdate((T) data, request);
            // 处理字段为某些值时，不能修改的情况
            unEditColunmHiddle((T) data);
        } catch (AlertBusinessException abex) {
            // 前台用弹出框的方式处理异常，并返回到当前请求的页面
            AlertBusinessException.hiddleAlertException(request, response, request.getParameter("hideTitle"), abex.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            request.setAttribute("errorMsg", getMessage(e));
        }
        return getBaseEditJspUrl() + "/data-edit";
    }

    /**
     * 上传图片
     *
     * @param class1
     * @param formType FormColumn type
     * @param vo
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    public T uploadFile(Class<? extends CommonListController> class1, T vo, HttpServletRequest request) {
        Field[] fields = ClassUtil.getDeclaredFields(class1);
        for (Field field : fields) {
            FormColunm foJ = field.getAnnotation(FormColunm.class);
            if (foJ != null) {
                if (!foJ.picPath().equals("")) {
                    String fieldName = "";
                    if (StringUtils.isNotBlank(foJ.column())) {
                        fieldName = foJ.column();
                    } else {
                        fieldName = field.getName();
                    }
                    //上传图片 fieldName+"img" 为该图片对应的file的name
                    String imagePath = upload(request, fieldName + "File", foJ.picPath());
                    if (!"".equals(imagePath)) {
                        //设置字段 fieldName为 值为imagePath
                        if ("未选择图片".equals(imagePath)) {

                        } else {
                            vo.set(fieldName, imagePath);
                        }
                    } else {
                        request.setAttribute("errorMsg", "上传失败！请重新上传或联系管理员。");
                    }
                }
            }
        }
        return vo;
    }

}