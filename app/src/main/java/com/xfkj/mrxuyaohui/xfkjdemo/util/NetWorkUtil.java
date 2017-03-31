package com.xfkj.mrxuyaohui.xfkjdemo.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class NetWorkUtil {
	
	public static String httpPost(String url,List<NameValuePair> params){
		String result = "";
		HttpPost httpRequest = new HttpPost(url);
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			result = EntityUtils.toString(httpResponse.getEntity());		
		} catch (UnsupportedEncodingException e) {
			result = "URL����";
		} catch (ClientProtocolException e) {
			result = "����ʧ��";
		} catch (IOException e) {
			result = "����������";
		}
		return result;
	}
	
}
