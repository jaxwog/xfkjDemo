package com.xfkj.mrxuyaohui.xfkjdemo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.bean.Doc_Login;
import com.xfkj.mrxuyaohui.xfkjdemo.bean.User_Login;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.xuyaohui on 2015/4/17.
 */
public class HttpUtil {

    private  static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时5秒钟
    private  static  final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟

    public  static List<User_Login> loginServer(String username, String password,String tape)

    {

        //使用apache HTTP客户端实现,需要改为服务器的地址
        String urlStr = IpCofig.getIP_SERVER()+"MyheartDoctot/UserLoginAction";
        HttpPost request = new HttpPost(urlStr);//新建Post请求
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加用户名和密码
        params.add(new BasicNameValuePair("tape",tape));
        params.add(new BasicNameValuePair("phone",username));
        params.add(new BasicNameValuePair("passwd",password));
        try
        {
            //设置请求参数项
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpClient client = getHttpClient();
            //执行请求返回响应
            HttpResponse response = client.execute(request);
            System.out.println("从服务器端返回的"+response.getStatusLine().getStatusCode());

            //判断是否请求成功
            if(response.getStatusLine().getStatusCode()==200)
            {
                //获得响应信息
                System.out.println("返回为200");
                String responseMsg = EntityUtils.toString(response.getEntity());
                System.out.println("返回的数据为："+responseMsg);
                List<User_Login> userMessage =Login_JsonHelp.getListPerson(responseMsg);
                return userMessage;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
//            return flag;
        return null;
    }
    //医生登陆验证
    public  static List<Doc_Login> docLoginServer(String username, String password,String tape)

    {

        //使用apache HTTP客户端实现,需要改为服务器的地址
        String urlStr = IpCofig.getIP_SERVER()+"MyheartDoctot/UserLoginAction";
        HttpPost request = new HttpPost(urlStr);//新建Post请求
        //如果传递参数多的话，可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //添加用户名和密码
        params.add(new BasicNameValuePair("tape",tape));
        params.add(new BasicNameValuePair("phone",username));
        params.add(new BasicNameValuePair("passwd",password));
        try
        {
            //设置请求参数项
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpClient client = getHttpClient();
            //执行请求返回响应
            HttpResponse response = client.execute(request);
            System.out.println("从服务器端返回的"+response.getStatusLine().getStatusCode());

            //判断是否请求成功
            if(response.getStatusLine().getStatusCode()==200)
            {
                //获得响应信息
                System.out.println("返回为200");
                String responseMsg = EntityUtils.toString(response.getEntity());
                System.out.println("返回的数据为："+responseMsg);
                List<Doc_Login> userMessage =Login_JsonHelp.getDocListPerson(responseMsg);
                return userMessage;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
//            return flag;
        return null;
    }


    public  static  HttpClient getHttpClient()
    {
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
        HttpClient client = new DefaultHttpClient(httpParams);
        return client;
    }



}

