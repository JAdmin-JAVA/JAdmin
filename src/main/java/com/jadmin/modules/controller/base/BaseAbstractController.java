package com.jadmin.modules.controller.base;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jadmin.dao.SystemDao;
import com.jadmin.modules.annotation.column.SearchColumn;
import com.jadmin.modules.annotation.list.DeleteMode;
import com.jadmin.modules.exception.BusinessException;
import com.jadmin.modules.itf.GeneralOperatUtils;
import com.jadmin.modules.util.ConfigUtil;
import com.jadmin.modules.util.SpringContext;
import com.jadmin.util.file.FileUtil;
import com.jadmin.vo.entity.base.UserVO;
import com.jadmin.vo.enumtype.JavaType;
import com.jadmin.vo.fundation.base.AbstractValueObject;
import com.jadmin.vo.fundation.base.QueryArgVO;
import com.jadmin.vo.fundation.controller.AdminPageSearchColumnVO;
import com.jadmin.vo.fundation.tool.ClientENV;
import com.jadmin.vo.fundation.tool.Commons;
import com.jadmin.vo.fundation.tool.Page;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Title:web框架
 * @Description:基础控制器
 * @Copyright:JAdmin (c) 2018年08月21日
 * @author:-jiujiya
 * @version:1.0
 */
@Controller
@Slf4j
public class BaseAbstractController extends GeneralOperatUtils {

    /**
     * 返回404找不到文件的页面
     */
    protected static final String NOT_FIND = "commons/404";

    /**
     * 错误描述
     */
    protected static final String ERROR_MSG = "服务器异常，请稍后重试";

    /**
     * success常量
     */
    public static final String success = "success";

    /**
     * failed常量
     */
    public static final String failed = "failed";

    /**
     * noIdentificat常量
     */
    public static final String noIdentificat = "noIdentificat";

    /**
     * 日志类型
     */
    public static final String SYS_LOG_TYPE = "SYS_LOG_TYPE";

    /**
     * 分页默认每页数量
     */
    protected static int WEB_DEF_PAGESIZE = 20;

    /**
     * 分页默认每页数量
     */
    protected static int DEF_PAGESIZE = 20;

    /**
     * 不包含@RequestMapping注解中url的基础jsp路径
     */
    public final static String BASE_JSPURL_NOT_HAS_REQUESTMAPPING = "admin/business";

    @Autowired
    protected SystemDao systemDao;

    /**
     * 处理pageNO为空的情况
     *
     * @param pageNo
     * @return
     */
    public int getPageNo(String pageNo) {
        int pageNoInt = 1;
        if (StringUtils.isNotBlank(pageNo)) {
            pageNoInt = Integer.parseInt(pageNo);
        }
        return pageNoInt;
    }

    /**
     * 如果是业务异常，直接返回 e.getMessage()
     *
     * @param e
     * @return
     */
    public static String getMessage(Exception e) {
        if (e instanceof BusinessException) {
            return e.getMessage();
        }
        return ERROR_MSG;
    }

    /**
     * 获取ClientENV
     *
     * @param session
     * @return
     */
    public static ClientENV getClientENV(HttpSession session) {
        return new ClientENV(session);
    }

    /**
     * 获取用户的session
     *
     * @param session
     * @return
     */
    public static UserVO getCurUser(HttpSession session) {
        return new ClientENV(session).getCurUser();
    }

    /**
     * @param request
     * @param fileType
     * @param filePathFor
     * @return 返回文件上传位置（相对路径）
     */
    public String upload(HttpServletRequest request, String fileType, String filePathFor) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        /**构建文件保存的网络目录**/
        String path = "/temp" + filePathFor + "/";//+ fileType + "/";
        /**构建文件保存的本地目录**/
        String logoPathDir = ConfigUtil.getJAdminProperty().getFileBasePath() + path;
        /**创建文件夹**/
        FileUtil.addDirs(logoPathDir);
        /**页面控件的文件流**/
        MultipartFile multipartFile = multipartRequest.getFile(fileType);
        /**文件名**/
        if (FileUtil.getFileType(multipartFile.getOriginalFilename()) == null || "".equals(FileUtil.getFileType(multipartFile.getOriginalFilename()))) {
            return "未选择图片";
        } else {
            String fileName = System.currentTimeMillis() + "." + FileUtil.getFileType(multipartFile.getOriginalFilename());
            File file = new File(logoPathDir + fileName);
            //上传到指定的文件夹
            try {
                multipartFile.transferTo(file);
                log.info("文件上传成功：" + logoPathDir + fileName);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new BusinessException("文件上传失败");
            }
            return path + fileName;
        }
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    public @ResponseBody
    String delete(DeleteMode deleteMode, String ids, Class<? extends AbstractValueObject> clazz) {
        try {
            return systemDao.deleteAll(deleteMode, clazz, ids);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ERROR_MSG;
        }
    }

    /**
     * 解决get请求中文乱码问题
     *
     * @param request
     * @param key
     * @return
     */
    protected String getParameter(HttpServletRequest request, String key) {
        String parameter = request.getParameter(key);
        if (parameter != null && request.getMethod().equals("GET")) {
            try {
                return new String(parameter.getBytes("iso-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return parameter;
    }

    /**
     * @param url
     * @return
     */
    public String redirect(String url) {
        log.debug("redirect:" + url);
        return "redirect:" + url;
    }

    /**
     * @param url
     * @return
     */
    public String forward(String url) {
        log.debug("forward:" + url);
        return "forward:" + url;
    }

    /**
     * 初始化 pageNo和pageSize，处理了前台未穿值得情况
     *
     * @param pageNo
     * @param pageSize
     * @param adminPageSearchColumnVOs 搜索的初始化的属性，包括Java类型和搜索的初始化值等属性
     */
    protected <T> Page<T> queryPage(HttpServletRequest request, String pageNo, String pageSize, String hql, String orderByHql, List<AdminPageSearchColumnVO> adminPageSearchColumnVOs) {
        return queryPage(null, request, pageNo, pageSize, hql, orderByHql, adminPageSearchColumnVOs);
    }

    protected <T> Page<T> queryPage(@SuppressWarnings("rawtypes") Class<? extends CommonListController> class1, HttpServletRequest request, String pageNo, String pageSize, String hql, String orderByHql, List<AdminPageSearchColumnVO> adminPageSearchColumnVOs) {
        Page<T> page = new Page<T>();
        if (StringUtils.isNotBlank(pageNo)) {
            page.setPageNo(Integer.parseInt(pageNo));
        }
        String pageSizeStr = Commons.getConfigValue("DEF_PAGESIZE", DEF_PAGESIZE + "");
        request.setAttribute("DEF_PAGESIZE", pageSizeStr);
        if (StringUtils.isNotBlank(pageSize)) {
            page.setPageSize(Integer.parseInt(pageSize));
        } else {
            page.setPageSize(Integer.parseInt(pageSizeStr));
        }
        // 动态获取搜索的args
        List<QueryArgVO> args = new ArrayList<QueryArgVO>();
        // 获取用户有选择的搜索条件
        Enumeration<String> enu = request.getParameterNames();
        // 获取搜索类型
        String searchType = request.getParameter("searchType");
        boolean isSearchEasy = "1".equals(searchType);
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            // 如果搜索类型是8不为空，不再效验值是否为空 && StringUtils.isNotBlank(request.getParameter(paraName))
            if (paraName.startsWith("search.")
                    && ((isSearchEasy && paraName.endsWith(" 1")) || (!isSearchEasy && !paraName.endsWith(" 1")))) {
                String column = paraName.substring(7, paraName.length());
                String stringValue = request.getParameter(paraName);
                Object val = stringValue;
                int like = SearchColumn.INIT;
                for (AdminPageSearchColumnVO apsc : adminPageSearchColumnVOs) {
                    String apscColumn = apsc.getColumn() + (isSearchEasy ? " 1" : "");
                    if (apscColumn.equals(column)) {
                        val = JavaType.getValueByString(apsc.getJavaType(), stringValue);
                        like = apsc.getLike();
                        if (isSearchEasy) {
                            column = column.substring(0, column.length() - 2);
                        }
                    }
                }
                if (StringUtils.isNotBlank(request.getParameter(paraName)) || like == SearchColumn.NOT_NULL) {
                    args.add(new QueryArgVO("o." + column, val, like));
                }
            }
        }
        // 如果是精简搜索
        if ("1".equals(searchType)) {
            String inputSearchColumnNames = request.getParameter("inputSearchColumnNames");
            String dateColumn = request.getParameter("dateColumn");
            if (StringUtils.isNotBlank(inputSearchColumnNames)) {
                String inputSearchColumns = request.getParameter("inputSearchColumns");
                String searchHqlWhere = "";
                for (String col : inputSearchColumns.split(" ")) {
                    searchHqlWhere += col + " like '%" + inputSearchColumnNames + "%' or ";
                }
                if (StringUtils.isNotBlank(searchHqlWhere)) {
                    hql += " and (" + searchHqlWhere.substring(0, searchHqlWhere.length() - 4) + ")";
                }
            }
            if (StringUtils.isNotBlank(dateColumn)) {
                String searchModeStartTime = request.getParameter("searchModeStartTime");
                String searchModeEndTime = request.getParameter("searchModeEndTime");
                if (StringUtils.isNotBlank(searchModeStartTime)) {
                    hql += " and o." + dateColumn + " >= '" + searchModeStartTime + "'";
                }
                if (StringUtils.isNotBlank(searchModeEndTime)) {
                    hql += " and o." + dateColumn + " <= '" + searchModeEndTime + " 24:00:00'";
                }
            }
        }
        // 获取初始化的值，如果用户有选择，无视该数据
        A:
        for (AdminPageSearchColumnVO apsc : adminPageSearchColumnVOs) {
            if (apsc.getInitDefaultValue() != null && StringUtils.isNotBlank(apsc.getInitDefaultValue().toString())) {
                for (QueryArgVO queryArgVO : args) {
                    if (queryArgVO.getKey().equals(apsc.getColumn())) {
                        continue A;
                    }
                }
                args.add(new QueryArgVO("o." + apsc.getColumn(), apsc.getInitDefaultValue()));
            }
        }
        request.setAttribute("search", args);
        try {
            if (systemDao == null) {
                systemDao = SpringContext.getBean(SystemDao.class);
            }
            // 导出数据是不再查询总数量
            if ("1".equals(request.getParameter("isDownLoad"))) {
                page.setAutoCount(false);
            }
            systemDao.queryPage(page, hql + orderByHql, args);
            String string = "pageSize=" + page.getPageSize() + "&";
            for (QueryArgVO entry : args) {
                if (entry.getValue() != null && !"".equals(entry.getValue())) {
                    string += entry.getKey() + "=" + entry.getValue() + "&";
                }
            }
            page.setArgs(string);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("查询数据失败，请检查hql（" + hql + "）的参数：" + args);
        }
        return page;
    }


    /**
     * 将list转换为json
     *
     * @param list
     * @return
     */
    public static JSONArray getJsonList(List<?> list) {
        return getJsonList(list, false);
    }

    /**
     * 将page对象转换为json
     *
     * @param page
     * @return
     */
    public String getPageJson(Page<Map<String, Object>> page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", page.result);
        map.put("page", page.totalPages);
        return getJsonMap(map).toString();
    }

    /**
     * 将map转换为json
     *
     * @param map
     * @return
     */
    public static JSONObject getJsonMap(Map<String, Object> map) {
        return getJsonMap(map, false);
    }

    /**
     * 将list转换为json
     *
     * @param list
     * @param keyToLowerCase 是否转换小写
     * @return
     */
    public static JSONArray getJsonList(List<?> list, boolean keyToLowerCase) {
        JSONArray array = new JSONArray();
        if (list == null || list.isEmpty()) {
            return array;
        }
        if (list.get(0) instanceof AbstractValueObject) {
            for (Object object : list) {
                array.add(((AbstractValueObject) object).toJson());
            }
        } else if (list.get(0) instanceof Map) {
            for (Object object : list) {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) object;
                JSONObject json = new JSONObject();
                traversingData(keyToLowerCase, map, json);
                array.add(json);
            }
        }
        return array;
    }

    /**
     * 创建者: dzy
     * 描述:遍历map将值添加到json中
     * 创建时间: 2018/9/11 17:28
     * [keyToLowerCase, map, json]
     *
     * @return void
     */
    private static void traversingData(boolean keyToLowerCase, Map<String, Object> map, JSONObject json) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value == null) {
                value = "";
            }
            String key = keyToLowerCase ? entry.getKey().toLowerCase() : entry.getKey();
            if (value instanceof List<?>) {
                json.put(key, getJsonList((List<?>) value, keyToLowerCase));
            } else if (value instanceof Map<?, ?>) {
                json.put(key, getJsonMap((Map<String, Object>) value, keyToLowerCase));
            } else {
                json.put(key, getTempValue(value));
            }
        }
    }

    /**
     * 将map转换为json
     *
     * @param keyToLowerCase 是否转换小写
     * @param map
     * @return
     */
    public static JSONObject getJsonMap(Map<String, Object> map, boolean keyToLowerCase) {
        JSONObject json = new JSONObject();
        traversingData(keyToLowerCase, map, json);
        return json;
    }

    public static Object getTempValue(Object value) {
		/*if(value instanceof String){
			return value.toString().replace("河南", "辽宁").replace("鹤壁", "葫芦岛");
		}*/
        return value;
    }

    //阻塞线程
    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {

        }
    }
}
