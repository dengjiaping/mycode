package com.wufangnao.manger;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/****************获取服务器数据***************/
public class HttpPostManager {
	//获取数据的url地址
	private String url;
	//请求参数
	private List<NameValuePair> params = new ArrayList<NameValuePair>();
	String result;
	
	public HttpPostManager(String url)
	{
		this.url = url;
	}
	
	public String getData()
	{
		HttpPost httppost = new HttpPost(url);
		HttpEntity httpentity;
		try {
			httpentity = new UrlEncodedFormEntity(params,"UTF-8");		
			httppost.setEntity(httpentity); 
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			HttpResponse httpResponse = httpClient.execute(httppost);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{					
				result = EntityUtils.toString(httpResponse.getEntity());						
			}
			else if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED)
			{
				result = "{\"result\": \"fail\", \"info\": \"没有权限\"}";
			}
			else if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_METHOD_NOT_ALLOWED)
			{
				result = "{\"result\": \"fail\", \"info\": \"该接口未授权\"}";
			}
			else
			{
				result = "{\"result\": \"fail\", \"info\": \"网络错误:"+httpResponse.getStatusLine().getStatusCode()+"\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"result\": \"fail\", \"info\": \"网络异常\"}";
		}
		return result;
	}
	
	public void addParams(String name, String value)
	{
		if(value != null && value.length() > 0)
		{
			params.add(new BasicNameValuePair(name, value));
		}
	}
	
	public void addParams(String namel, Object value)
	{
		if(value != null)
		{
			addParams(namel, value.toString());
		}
	}

	public List<NameValuePair> getParams() {
		return params;
	}
	
}
