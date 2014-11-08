package com.wufangnao.webInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.wufangnao.constant.S_WebBaseInfo;
import com.wufangnao.manger.HttpPostManager;
/**
 * 访问网络的公共类
 * @author lrk
 *2013-12-7下午02:15:59
 */
public abstract class TWebBase implements Runnable {
	protected Bundle data;
	private Handler handler;
	private Message message;
	protected HttpPostManager jsonHelper;
	private Context context;
	private String eventId;
	private long tempstime;
	public TWebBase(Context context, Handler handler, String webInterface)
	{
		this.context = context;
		data = new Bundle();
		this.handler = handler;
		this.eventId = webInterface;
		this.tempstime = System.currentTimeMillis();
		message = handler.obtainMessage();
		jsonHelper = new HttpPostManager(S_WebBaseInfo.WEBROOT + webInterface);
		jsonHelper.addParams("timestamp", tempstime);
		jsonHelper.addParams("signature",MD5Test(tempstime+"{soufang}"));
		
	}
	
	public void run() {
		String jsonResult = jsonHelper.getData();
		System.out.println("jsonResult = "+jsonResult);
		JSONObject jsonObject = null;
		try
		{
			jsonObject = new JSONObject(jsonResult);
			String result = jsonObject.getString("success");
			data.putString("result", result);
			System.out.println("result ="+result);
			if("true".endsWith(result))
			{
				data.putString("jsonResult", jsonResult);
			}
			else
			{
				data.putString("info", jsonObject.getString("result"));
			}
		}
		catch (JSONException e)
		{
			data.putString("result", "fail");
			data.putString("info", "网络数据格式错误");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			data.putString("result", "fail");
			data.putString("info", "网络异常");
			e.printStackTrace();
		}
		if(context!=null)
		{
			HashMap<String, String> values = new HashMap<String, String>();
			for(int i = 0 ; i<jsonHelper.getParams().size();i++)
			{
				values.put(jsonHelper.getParams().get(i).getName(), jsonHelper.getParams().get(i).getValue());
			}
			values.put("result", data.getString("result"));
			if(data.getString("result").equals("fail")) values.put("info", data.getString("info"));
		
		}
		
		message.setData(data);
		handler.sendMessage(message);
	}
	/**
	 * MD5 加密
	 * @param string
	 * @return
	 */
	private  String MD5Test(String string){  
        MessageDigest md=null;  
        try {  
            md=MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        md.update(string.getBytes()); //MD5加密算法只是对字符数组而不是字符串进行加密计算，得到要加密的对象  
        byte[] bs=md.digest();   //进行加密运算并返回字符数组  
        StringBuffer sb=new StringBuffer();  
        for(int i=0;i<bs.length;i++){    //字节数组转换成十六进制字符串，形成最终的密文  
            int v=bs[i]&0xFF;  
            if(v<16){  
                sb.append(0);  
            }  
            sb.append(Integer.toHexString(v));  
        }  
      return sb.toString().toUpperCase();//小写转大写
    } 
	
}
