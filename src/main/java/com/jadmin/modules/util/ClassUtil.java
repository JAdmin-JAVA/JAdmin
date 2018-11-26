package com.jadmin.modules.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.aspectj.lang.JoinPoint;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jadmin.modules.controller.base.CommonListController;
import com.jadmin.modules.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:类相关的工具类
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Slf4j
public class ClassUtil {
	
	/**
	 * 获取当前AOP操作的方法的类型
	 * @param joinPoint
	 * @return
	 */
	public static String getNowMethodType(JoinPoint joinPoint) {
		return getNowMethod(joinPoint).getAnnotatedReturnType().getType().getTypeName();
	}
	
	/**
	 * 获取当前AOP操作的方法
	 * @param joinPoint
	 * @return
	 */
	public static Method getNowMethod(JoinPoint joinPoint) { 
       String targetName = joinPoint.getTarget().getClass().getName();  
       String methodName = joinPoint.getSignature().getName();  
       Object[] arguments = joinPoint.getArgs();  
		try {
			Class<?> targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();  
			for (Method method : methods) {  
				if (method.getName().equals(methodName)) {  
					Class<?>[] clazzs = method.getParameterTypes();  
					if (clazzs.length == arguments.length) {  
						return method;
					}  
				}  
			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}  
       return null;
   }
	
    /**
     * methodGet方法获得值
     * @param field
     * @return
     */
    public static Object methodInitInvoke(Field field, Class<?> class1){
    	String methodName = "init" + toUpperCaseFirstOne(field.getName());
		try {
			return class1.getMethod(methodName).invoke(class1.newInstance());
		} catch(Exception e) { 
			log.error(e.getMessage(), e);
			throw new BusinessException(field.getName() + "通过方法[" + methodName + "]获取，但是获取失败，请查看原因！");
		}
    }
    
    /**
     * methodGet方法获得值
     * @param field
     * @return
     */
    public static Object methodGetInvoke(Field field, Class<?> class1){
    	String methodName = "get" + toUpperCaseFirstOne(field.getName());
		try {
			return class1.getMethod(methodName).invoke(class1.newInstance());
		} catch(Exception e) { 
			log.error(e.getMessage(), e);
			throw new BusinessException(field.getName() + "声明了InitDefaultColunm类型为通过方法["
			+ methodName + "]获取，但是获取失败，请查看原因！");
		}
    }

	//首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
    /**
     * 获得类属性，包括继承了CommonListController类的父类的属性
     * @param class1
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Field[] getDeclaredFields(Class<? extends CommonListController> class1) {
    	List<Field> returnField = new ArrayList<Field>();
    	for(; class1 != CommonListController.class; class1 = (Class<? extends CommonListController>) class1.getSuperclass()){
    		returnField = getListByArray(class1.getDeclaredFields(), returnField);
    	}
		return returnField.toArray(new Field[]{});
	}
    
    private static List<Field> getListByArray(Field[] fields, List<Field> returnf){
    	List<Field> returnField = new ArrayList<Field>();
    	for (Field field : fields) {
    		returnField.add(field);
		}
    	returnField.addAll(returnf);
    	return returnField;
    }
    
    /**
     * 返回 RequestMapping的值
     * @param mustHave 是否一定要有值
     * @return
     */
    public static String[] getRequestMappingValues(Class<?> class1){
    	return getRequestMappingValue(class1.getAnnotation(RequestMapping.class), class1.getName(), true);
    }
    
    /**
     * 返回 RequestMapping的值
     * @param mustHave 是否一定要有值
     * @return
     */
    public static String[] getRequestMappingValues(Method method){
    	return getRequestMappingValue(method.getAnnotation(RequestMapping.class), method.toString(), true);
    }
    
    /**
     * 返回 RequestMapping的值
     * @param mustHave 是否一定要有值
     * @return
     */
    public static String getRequestMappingValue(Class<?> class1, boolean mustHave){
    	String[] urls = getRequestMappingValues(class1, mustHave);
    	return urls.length == 0 ? "" : urls[0];
    }
    
    /**
     * 返回 RequestMapping的值
     * @param mustHave 是否一定要有值
     * @return
     */
    public static String[] getRequestMappingValues(Class<?> class1, boolean mustHave){
    	return getRequestMappingValue(class1.getAnnotation(RequestMapping.class), class1.getName(), mustHave);
    }
    
    /**
     * 返回 RequestMapping的值
     * @param mustHave 是否一定要有值
     * @return
     */
    public static String getRequestMappingValue(Method method, boolean mustHave){
    	String[] urls = getRequestMappingValues(method, mustHave);
    	return urls.length == 0 ? "" : urls[0];
    }
    
    /**
     * 返回 RequestMapping的值
     * @param mustHave 是否一定要有值
     * @return
     */
    public static String[] getRequestMappingValues(Method method, boolean mustHave){
    	return getRequestMappingValue(method.getAnnotation(RequestMapping.class), method.toString(), mustHave);
    }
    
    /**
     * 返回 RequestMapping的值
     * @param anno
     * @param memo
     * @param mustHave
     * @return
     */
    private static String[] getRequestMappingValue(Annotation anno, String memo, boolean mustHave){
		if(anno != null){
			RequestMapping rM = (RequestMapping) anno;
			if(rM.value() != null && rM.value().length != 0){
				return rM.value();
			}
		}
		if(mustHave){
			throw new BusinessException(memo + "未配置RequestMapping的值");
		}
		log.debug(memo + "未配置RequestMapping的值");
		return new String[]{};
    }
    
    /**
     * 取得某个接口下所有实现这个接口的类
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<Class> getAllClassByInterface( Class c) {
        List<Class>  returnClassList = null;
        if(c.isInterface()) {
            // 获取当前的包名
            String packageName = c.getPackage().getName();
            // 获取当前包下以及子包下所以的类
            List<Class<?>> allClass = getClasses(packageName);
            if(allClass != null) {
                returnClassList = new ArrayList<Class>();
                for(Class classes : allClass) {
                    // 判断是否是同一个接口
                    if(c.isAssignableFrom(classes)) {
                        // 本身不加入进去
                        if(!c.equals(classes)) {
                            returnClassList.add(classes);        
                        }
                    }
                }
            }
        }
        return returnClassList;
    }

    
    /*
     * 取得某一类所在包的所有类名 不含迭代
     */
    public static String[] getPackageAllClassName(String classLocation, String packageName){
        //将packageName分解
        String[] packagePathSplit = packageName.split("[.]");
        String realClassLocation = classLocation;
        int packageLength = packagePathSplit.length;
        for(int i = 0; i< packageLength; i++){
            realClassLocation = realClassLocation + File.separator+packagePathSplit[i];
        }
        File packeageDir = new File(realClassLocation);
        if(packeageDir.isDirectory()){
            String[] allClassName = packeageDir.list();
            return allClassName;
        }
        return null;
    }
    
    /**
     * 从包package中获取所有的Class
     * @param pack
     * @return
     */
    public static List<Class<?>> getClasses(String packageName){
        
        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<Class<?>>();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements()){
                //获取下一个元素
                URL url = dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();
                //如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)){
                    //如果是jar包文件 
                    //定义一个JarFile
                    JarFile jar;
                    try {
                        //获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        //从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        //同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            //如果是以/开头的
                            if (name.charAt(0) == '/') {
                                //获取后面的字符串
                                name = name.substring(1);
                            }
                            //如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                //如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    //获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                //如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive){
                                    //如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        //去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            //添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                      }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        return classes;
    }
    
    /**
     * 以文件的形式来获取包下的所有Class
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes){
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
              public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
              }
            });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                                      file.getAbsolutePath(),
                                      recursive,
                                      classes);
            }
            else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 返回 数据的基础vo类的class
     * @param class1 可定义为其他控制类的类型
     * @return 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Class<?> getVOClass(Class<? extends CommonListController> class1){
    	ParameterizedType type = null;
    	// 遍历找到voClass
    	while(true){
    		if(class1.getGenericSuperclass() == null || class1.getGenericSuperclass() instanceof ParameterizedType){
    			break;
    		}
    		class1 = (Class<? extends CommonListController>) class1.getSuperclass();
    	}
    	type = (ParameterizedType)class1.getGenericSuperclass();
    	return (Class<?>) type.getActualTypeArguments()[0];
    }
}
