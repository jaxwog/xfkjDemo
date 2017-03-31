package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImageView;

public class News_detail extends Activity {
//    private SmartImageView imageView;
//    private TextView name;
//    private TextView time;
//    private TextView desc;
//    private static final String url_image="http://192.168.1.177:8080/MyheartDoctot/user_img/";
//
//    private Button back_before;
    private WebView webView;
    private String url= IpCofig.getIP_SERVER()+"MyheartDoctot/NewsMessage?NewId=";
    private int news_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        Intent intent=getIntent();
        news_Id=intent.getIntExtra("newId",0);
        System.out.println("获取到的数据为："+news_Id);
        webView=(WebView)this.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl( IpCofig.getIP_SERVER()+"MyheartDoctot/NewsMessage?NewsId="+news_Id);

    }
    public void click(View v)
    {
        switch (v.getId())
        {
            case R.id.back_before:
                finish();
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
