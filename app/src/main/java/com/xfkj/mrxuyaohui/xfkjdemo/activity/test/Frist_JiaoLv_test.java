package com.xfkj.mrxuyaohui.xfkjdemo.activity.test;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.Deprssion_Test;

import org.w3c.dom.Text;

public class Frist_JiaoLv_test extends Activity {
    private Button back_button;
    private Button test;

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaolv_test);
        back_button=(Button)this.findViewById(R.id.back_before);
        test=(Button)this.findViewById(R.id.test);
        textView=(TextView)this.findViewById(R.id.image_desc);

    }
    public void click(View v)
    {
        Intent intent=new Intent();
        switch (v.getId())
        {
            case  R.id.back_before:
                finish();
                break;
            case R.id.test:
                intent.setClass(this, Jiaolv_test.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frist__jiao_lv_test, menu);
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
