package com.jadmin.vo.fundation.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.jadmin.modules.exception.BusinessException;
import com.jadmin.vo.fundation.tool.BeanProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:所有vo的父类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Getter @Setter @Slf4j
public class AbstractSimple implements Serializable {
    
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 6127367842897326094L;

    /**
     * get某一属性的值
     * 
     * @param attribute
     * @return
     */
    public Object get(String attribute) {
        try {
            BeanProperty property = new BeanProperty(this.getClass(), attribute, true, false);
            return property.get(this);
        } catch (Exception e) {
        	log.error("无法获得vo的属性值：" + attribute + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 是否还有key
     * @param attribute
     * @return
     */
    public boolean hasKey(String attribute) {
    	try {
    		new BeanProperty(this.getClass(), attribute, true, false);
    		return true;
		} catch (Exception e) {
			// 吃掉异常
			return false;
		}
    }

    /**
     * set某一属性的值
     * 
     * @param attribute
     * @param value
     * @return
     */
    public Object set(String attribute, Object value) {
    	try{
            BeanProperty property = new BeanProperty(this.getClass(), attribute, false, true);
            return property.set(this, value);
    	}catch (Exception e) {
    		log.error(e.getMessage(), e);
    		throw new BusinessException("无法设置属性" + this.getClass().getName() + "." + attribute + "的值：" + value);
		}
    }

    /**
     * 查看某一属性是否能写
     * 
     * @param PropertyName
     * @return
     */
    public boolean isWritableProperty(String PropertyName) {
        BeanProperty property = new BeanProperty(this.getClass(), PropertyName, false, true);
        return property.isWritable();
    }
    
    /**
     * 克隆一个完全相同的VO（深度克隆）
     * 
     * @return Object
     */
    public Object clone() {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream outs = new ObjectOutputStream(out);
            outs.writeObject(this);
            outs.close();
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
            return in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}