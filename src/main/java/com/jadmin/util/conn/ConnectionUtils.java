package com.jadmin.util.conn;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.jadmin.modules.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/** 
 * @Title:web框架
 * @Description:连接工具
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
@Slf4j
public class ConnectionUtils {
	
    public static void main(String[] args) {
    	// 模拟http提交
//		Object o = ConnectionUtils.doGet("http://127.0.0.1:8080/detection?checkId=" + "7b5bb485c04d11e68ce2d017c22b65fd", new HashMap<String, Object>());
//		System.out.println(o.toString());
//		FileUtil.writeToTxtByFileWriter(new File("D://11.html"), o.toString());
    	try {
			download("http://www.81.cn/attachement/jpg/site351/20161226/8cdcd43004a119caaa6c03.jpg", "D://11.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
     * 提交一个post请求，并获得请求的返回值
     * @return
     */
    public static Object doPost(String type, Map<String, Object> map) {
        return DO(type, "POST", map);
    }
    
    /**
     * 提交一个post请求，并获得请求的返回值
     * @return
     */
    public static Object doGet(String type, Map<String, Object> map) {
        return DO(type, "GET", map);
    }

    private static Object DO(String type, String postType, Map<String, Object> map){
        return BaseDO(type, postType, map);
    }
    
    /**
     * 简单的Get请求
     * @param weburl
     * @param charsetName 编码
     * @return
     * @throws Exception 
     */
    public static Object esayGet(String weburl, String charsetName) throws Exception{
    	HttpURLConnection connection = null;
        ObjectInputStream ois = null;
        OutputStreamWriter out = null;
        ObjectOutputStream oos = null;
        try {
        	URL url = new URL(weburl);
            // 新建连接实例
            connection = (HttpURLConnection) url.openConnection();  
		     connection.setDoOutput(true);// 打开写入属性  
		     connection.setDoInput(true);// 打开读取属性  
		     connection.setRequestMethod("GET");// 设置提交方法  
		     
		     connection.setUseCaches(false);
		     connection.setInstanceFollowRedirects(true);
		     connection.setConnectTimeout(1000 * 5);// 连接超时时间  
		     connection.setReadTimeout(1000 * 5);// 连接超时时间  
		     connection.connect();  

            if (200 == connection.getResponseCode()) {
                InputStream instream = connection.getInputStream();  
                byte[] data = read(instream);  
                String jsonStr = new String(data, charsetName);
                return jsonStr;
            } else{
            	throw new RuntimeException(weburl + "无法获取数据,返回类型为：" + connection.getResponseCode());
            }
        } finally {
            close(out);
            close(oos);
            close(ois);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    public static Object BaseDO(String weburl, String postType, Map<String, Object> map){
    	HttpURLConnection connection = null;
        ObjectInputStream ois = null;
        OutputStreamWriter out = null;
        ObjectOutputStream oos = null;
        try {
        	String args = "";
        	if(postType.equals("POST")){
        		args =UrlUtils.addURLArgus(weburl, map, false);
        	}else if(map != null && !map.isEmpty()){
        		args = UrlUtils.formatURL(weburl, map);
        		weburl += "?" + args;
        	}
        	URL url = new URL(weburl);
            // 新建连接实例
            connection = (HttpURLConnection) url.openConnection();  
		     connection.setDoOutput(true);// 打开写入属性  
		     connection.setDoInput(true);// 打开读取属性  
		     connection.setRequestMethod(postType);// 设置提交方法  
		     
		     connection.setUseCaches(false);
		     connection.setInstanceFollowRedirects(true);
		     connection.setConnectTimeout(1000 * 20);// 连接超时时间  
		     connection.connect();  

         	if(postType.equals("POST")){
		         out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");  
	   		     out.write(args);
	   		     out.flush(); 
	   		     out.close(); 
         	}
            if (200 == connection.getResponseCode()) {  
                InputStream instream = connection.getInputStream();  
                byte[] data = read(instream);  
                String jsonStr = new String(data, "UTF-8"); 
                return jsonStr;
            } else{
            	log.error (weburl+"返回类型为：" + connection.getResponseCode());
            	return null;
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        } finally {
            close(out);
            close(oos);
            close(ois);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    /** 
     * 读取输入流为byte[]数组 
     */  
    public static byte[] read(InputStream instream) throws IOException {  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while ((len = instream.read(buffer)) != -1)  
        {  
            bos.write(buffer, 0, len);  
        }  
        return bos.toByteArray();  
    }  
    
    public static void close(Closeable out) {
        try {
            if(out != null){
                out.close();
            }
        } catch (IOException e) {
        }
    }
    
    /**
     * 下载文件到本地
     * @param urlString
     *          被下载的文件地址
     * @param filename
     *          本地文件名
     * @throws Exception
     *           各种异常
     */
  public static void download(String urlString, String filename){
	  InputStream is = null;
	  OutputStream os = null;
	  try {
		  // 构造URL
	      URL url = new URL(urlString);
	      // 打开连接
	      URLConnection con = url.openConnection();
	      // 输入流
	      is = con.getInputStream();
	      // 1K的数据缓冲
	      byte[] bs = new byte[1024];
	      // 读取到的数据长度
	      int len;
	      // 输出的文件流
	      os = new FileOutputStream(filename);
	      // 开始读取
	      while ((len = is.read(bs)) != -1) {
	        os.write(bs, 0, len);
	      }
	  } catch (Exception e) {
		  log.error(e.getMessage(), e);
		  throw new BusinessException("静态化" + urlString + "页面失败");
	  } finally{
		  close(os);
		  close(is);
	  }
      
  }
    
}
