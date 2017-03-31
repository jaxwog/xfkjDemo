package com.xfkj.mrxuyaohui.xfkjdemo.activity;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class Setting extends Activity {
    private Button back_before;
   private Button peo_help=null;
    private Button peo_state,jiancegengxin,kefu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_set);
        peo_help=(Button)findViewById(R.id.peo_help);
        peo_state=(Button)findViewById(R.id.peo_statement);
        back_before=(Button)findViewById(R.id.title);
        jiancegengxin=(Button)findViewById(R.id.jiancegengxin);
        kefu=(Button)findViewById(R.id.kefu);


    }
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.kefu:
                intent.setClass(this, PeoStatement.class);
                intent.putExtra("version","客服电话为：0371-6530423");
                startActivity(intent);
                break;
            case R.id.peo_help://转到帮助界面
                intent.setClass(this, PeoStatement.class);
                intent.putExtra("version","如果你有什么疑问，欢迎致电我们的客服！");
                startActivity(intent);
                break;
            case R.id.peo_statement://转到声明界面
                intent.setClass(this, PeoStatement.class);
                intent.putExtra("version","版权所有，侵权必究！");
                startActivity(intent);
                break;
            case R.id.title:
                finish();
                break;
            case R.id.jiancegengxin:
                intent.setClass(this, PeoStatement.class);
                intent.putExtra("version",getVersion());
                startActivity(intent);
                break;

        }
    }
    public String getVersion() {
             try {
                     PackageManager manager = this.getPackageManager();
                     PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
                     String version = info.versionName;
                     return "版本号： "+ version;
                 } catch (Exception e) {
                     e.printStackTrace();
                     return "没有找到...";
                 }
         }
}
