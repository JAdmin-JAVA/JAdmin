package com.jadmin.vo.fundation.base;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import com.jadmin.modules.util.DictinfoUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * @Title:web框架
 * @Description:所有vo的父类
 * @Copyright:JAdmin (c) 2018年08月21日
 * @author:-jiujiya
 * @version:1.0
 */
@Repository @Getter @Setter
public abstract class AbstractValueObject extends AbstractSimple {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -2567834559471839093L;

    @Transient
    private int jspCount;

    @Transient
    private String cRs;

    /**
     * 构造器
     */
    public AbstractValueObject() {

    }

    /**
     * 构造器，传入billId
     *
     * @param billId
     */
    public AbstractValueObject(String pk) {
        setPrimaryKey(pk);
    }

    /**
     * 获得当前vo的中文标示
     *
     * @return
     */
    public String getMainName() {
        if (hasKey("name")) {
            return get("name") + "";
        }
        return "请重写上一个页面中vo类的getMainName方法";
    }

    /**
     * 获取json
     *
     * @return
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        Field[] fields = getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field field : fields) {
            if (!field.getName().equals("serialVersionUID")) {
                json.put(field.getName(), this.get(field.getName()));
            }
        }
        return json;
    }

    abstract public String getPrimaryKey();

    abstract public void setPrimaryKey(String key);

    public String toString() {
        return getPrimaryKey();
    }

    public int compareTo(Object o) {
        AbstractValueObject oldvp = (AbstractValueObject) o;
        String pk = getPrimaryKey();
        String oldpk = oldvp.getPrimaryKey();
        DecimalFormat df = new DecimalFormat("0000000000");
        if (pk == null) {
            pk = "Z" + df.format(hashCode());
        }
        if (oldpk == null) {
            oldpk = "Z" + df.format(o.hashCode());
        }
        return pk.compareTo(oldpk);
    }

    /**
     * 給据selectCode获取数据字典里面的值
     *
     * @param code
     * @param value
     * @return
     */
    public Object getSelectName(String code, Object value) {
        if (value == null) {
            return "";
        }
        return DictinfoUtils.getDictkindNameByKey(code, value.toString(), null);
    }

    /**
     * 获得只有月日时分的时间
     *
     * @return
     */
    public String getShortOperateTime() {
        String operateTime = (String) get("operateTime");
        if (StringUtils.isNotBlank(operateTime)) {
            operateTime = operateTime.substring(5, 16);
        }
        return operateTime;
    }

    /**
     * 获得只有年月日
     *
     * @return
     */
    public String getComOperateDate() {
        String operateTime = (String) get("operateTime");
        if (StringUtils.isNotBlank(operateTime)) {
            operateTime = operateTime.substring(0, 10);
        }
        return operateTime;
    }

}