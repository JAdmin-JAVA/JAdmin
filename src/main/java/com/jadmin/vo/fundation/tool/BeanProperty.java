package com.jadmin.vo.fundation.tool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

/** 
 * @Title:web框架
 * @Description:Bean属性操作工具类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class BeanProperty {

	private Class beanClass = null;
    private String propertyName = null;

    private Class valueClass = null;
    private List getterChain = null;
    private List setterChain = null;
    private static Object[] EMPTY_ARGUMENTS = new Object[0];
    private static Class[] EMPTY_PARAMETER_TYPES = new Class[0];

    /**
     * 构造器
     */
    public BeanProperty(Class beanClass, String propertyName, boolean readable, boolean writable) {
        this.beanClass = beanClass;
        this.propertyName = propertyName;

        String[] propertyParts = propertyName.split("\\.");
        List commonChain = new ArrayList();
        Class currentClass = beanClass;
        for (int p = 0; p < propertyParts.length - 1; p++) {
            Method partGetter = findGetterMethod(currentClass, propertyParts[p]);
            commonChain.add(partGetter);
            currentClass = partGetter.getReturnType();
        }

        if (readable) {
            getterChain = new ArrayList();
            getterChain.addAll(commonChain);
            Method lastGetter = findGetterMethod(currentClass, propertyParts[propertyParts.length - 1]);
            getterChain.add(lastGetter);
            valueClass = lastGetter.getReturnType();
        }

        if (writable) {
            setterChain = new ArrayList();
            setterChain.addAll(commonChain);
            Method lastSetter = findSetterMethod(currentClass, propertyParts[propertyParts.length - 1]);
            setterChain.add(lastSetter);
            if (valueClass == null)
                valueClass = lastSetter.getParameterTypes()[0];
        }
    }

    /**
     * 查找属性的get方法
     */
    private Method findGetterMethod(Class targetClass, String property) {
        Method result = null;

        Class currentClass = targetClass;
        while (currentClass != null) {
            String getProperty = "get" + capitalize(property);
            result = getMethod(currentClass, getProperty, EMPTY_PARAMETER_TYPES);
            if (result != null) {
                validateGetter(result);
                return result;
            }

            String isProperty = "is" + capitalize(property);
            result = getMethod(currentClass, isProperty, EMPTY_PARAMETER_TYPES);
            if (result != null) {
                validateGetter(result);
                return result;
            }
            currentClass = currentClass.getSuperclass();
        }

        throw new IllegalArgumentException("找不到 "+targetClass.getName()+" \"" + property + "\"的get方法");
    }

    /**
     * 查找属性的set方法
     */
    private Method findSetterMethod(Class targetClass, String property) {
        String setProperty = "set" + capitalize(property);

        Class currentClass = targetClass;
        while (currentClass != null) {

            Method[] classMethods = currentClass.getMethods();
            for (int m = 0; m < classMethods.length; m++) {
                if (!classMethods[m].getName().equals(setProperty))
                    continue;
                if (classMethods[m].getParameterTypes().length != 1)
                    continue;
                validateSetter(classMethods[m]);
                return classMethods[m];
            }
            currentClass = currentClass.getSuperclass();
        }

        throw new IllegalArgumentException("找不到 "+targetClass.getName()+" \"" + property + "\"的set方法");
    }

    /**
     * 验证get方法
     */
    private void validateGetter(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalArgumentException("get方法 \"" + method + "\" 非 public");
        }

        if (Void.TYPE.equals(method.getReturnType())) {
            throw new IllegalArgumentException("get方法 \"" + method + "\" 为 void");
        }
    }

    /**
     * 验证set方法
     */
    private void validateSetter(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalArgumentException("set方法 \"" + method + "\" 非 public");
        }

        if (method.getParameterTypes().length != 1) {
            throw new IllegalArgumentException("set方法 \"" + method + "\" 没有参数");
        }
    }

    private String capitalize(String property) {
        StringBuffer result = new StringBuffer();
        result.append(Character.toUpperCase(property.charAt(0)));
        result.append(property.substring(1));
        return result.toString();
    }

    private Method getMethod(Class targetClass, String methodName, Class[] parameterTypes) {
        try {
            return targetClass.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Class getValueClass() {
        return valueClass;
    }

    public boolean isReadable() {
        return (getterChain != null);
    }

    public boolean isWritable() {
        return (setterChain != null);
    }

    public Object get(Object member) {
        if (!isReadable())
            throw new IllegalStateException("Property " + propertyName + " of " + beanClass + " not readable");

        try {
            // do all the getters in sequence
            Object currentMember = member;
            for (int i = 0; i < getterChain.size(); i++) {
                Method currentMethod = (Method) getterChain.get(i);
                currentMember = currentMethod.invoke(currentMember, EMPTY_ARGUMENTS);
                if (currentMember == null)
                    return null;
            }

            // return the result of the last getter
            return currentMember;
        } catch (IllegalAccessException e) {
            throw new SecurityException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new UndeclaredThrowableException(e.getCause());
        }
    }

    public Object set(Object member, Object newValue) {
        if (!isWritable())
            throw new IllegalStateException("Property " + propertyName + " of " + beanClass + " not writable");

        try {
            Object currentMember = member;
            for (int i = 0; i < setterChain.size() - 1; i++) {
                Method currentMethod = (Method) setterChain.get(i);
                currentMember = currentMethod.invoke(currentMember, EMPTY_ARGUMENTS);
                if (currentMember == null)
                    return null;
            }

            Method setterMethod = (Method) setterChain.get(setterChain.size() - 1);
            Object result = setterMethod.invoke(currentMember, new Object[] { newValue });
            return result;
        } catch (IllegalAccessException e) {
            throw new SecurityException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new UndeclaredThrowableException(e.getCause());
        }
    }

    public boolean equals(Object other) {
        BeanProperty otherProperty = (BeanProperty) other;
        if (!beanClass.equals(otherProperty.beanClass))
            return false;
        if (!propertyName.equals(otherProperty.propertyName))
            return false;
        return true;
    }
}