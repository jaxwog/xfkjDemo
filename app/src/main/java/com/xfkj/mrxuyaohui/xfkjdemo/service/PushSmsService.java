package com.xfkj.mrxuyaohui.xfkjdemo.service;

/**
 * Created by Mr.xuyaohui on 2015/5/11.
 */
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.FristActivity;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.MyAppliction;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.News_detail;
import com.xfkj.mrxuyaohui.xfkjdemo.bean.User_Login;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.Message_push.AsyncHttpClient;
import com.xfkj.mrxuyaohui.xfkjdemo.util.Message_push.AsyncHttpResponseHandler;

/**
 *
 * 短信推送服务类，在后台长期运行，每个一段时间就向服务器发送一次请求
 *
 * @author jerry
 *
 */
public class PushSmsService extends Service {
    private MyThread myThread;
    private NotificationManager manager;
    private Notification notification;
    private PendingIntent pi;
    private AsyncHttpClient client;
    private boolean flag = false;


    //使用SharedPreferences储存用户信息
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("oncreate()");
        this.client = new AsyncHttpClient();
        this.myThread = new MyThread();
        this.myThread.start();
        flag=((MyAppliction)getApplication()).getIspush();

        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();//获取编辑器
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        this.flag = false;
        super.onDestroy();
    }

    private void notification(String content, String number, String date) {
        // 获取系统的通知管理器
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification(R.drawable.ic_menu_compose, content,
                System.currentTimeMillis());
        notification.defaults = Notification.DEFAULT_ALL; // 使用默认设置，比如铃声、震动、闪灯
        notification.flags = Notification.FLAG_AUTO_CANCEL; // 但用户点击消息后，消息自动在通知栏自动消失
        notification.flags |= Notification.FLAG_NO_CLEAR;// 点击通知栏的删除，消息不会依然不会被删除

        Intent intent = new Intent(getApplicationContext(),
                News_detail.class);
        intent.putExtra("content", content);
        intent.putExtra("number", number);
        intent.putExtra("newsId", date);
        pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        notification.setLatestEventInfo(getApplicationContext(),
                "零点心理", content, pi);
        // 将消息推送到状态栏
        manager.notify(0, notification);
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            //[{"newsDate":"2015-06-10 11:24:11","newsId":57,"newsImg":"83674a3d-c9c5-4728-af87-a2ca8fca5be9.jpg","newsMessage":"sdafasdfsasfsaf","newsTitle":"sadfdasfsdaf"}]
            String url =  IpCofig.getIP_SERVER()+"MyheartDoctot/JpushNews";
            while (true) {
                try {
                    // 每个10秒向服务器发送一次请求
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 采用get方式向服务器发送请求
                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          byte[] responseBody) {
                        try {
                            JSONArray array=new JSONArray(new String(responseBody));
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject item = array.getJSONObject(i);
                                String content=item.getString("newsTitle");
                                String date = item.getString("newsDate");
                                String newId = item.getString("newsId");
                                if(sharedPreferences.getString("newsId",null).equals(newId)){
                                       Thread.sleep(10000);
                                }else {
                                    editor.putString("newsId",date);
                                    editor.commit();
                                    notification(content, newId, date);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(), "数据请求失败", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

            }
        }
    }

}

