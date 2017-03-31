package com.xfkj.mrxuyaohui.xfkjdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.activity.FristActivity;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.News_detail;
import com.xfkj.mrxuyaohui.xfkjdemo.util.Welcome;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImage;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImageView;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News_JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.welcome.WelcomeJsonHelp;

import java.util.ArrayList;
import java.util.List;


public class WelcomeActivity extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private boolean flag=false;
    private ViewPagerAdapter vpAdapter;
    private List<SmartImageView> imageList;

    private  String[] imageIds = new String[3];
    private int[] image_bendi={R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
//    private  String[] imageIds = {"http://192.168.1.177:8080/MyheartDoctot/news_img/83674a3d-c9c5-4728-af87-a2ca8fca5be9.jpg","http://192.168.1.177:8080/MyheartDoctot/news_img/ff7fdf5f-88fb-4c1f-a94c-90afa54ac918.jpg","http://192.168.1.177:8080/MyheartDoctot/news_img/31042785-61d4-48a1-a218-5e03bbb43562.jpg"};

    // 底部小点图片
    private ImageView[] dots;
    private long exitTime = 0;//退出程序

    // 记录当前选中位置
    private int currentIndex;

    private Button button;

    private String urlPath="http://192.168.1.177:8080/MyheartDoctot/SelectStartscreenBystyle";
    private List<Welcome> persons;
    private String url_image="http://192.168.1.177:8080/MyheartDoctot/app/";
    private String[] imageDescriptions=new String[3];
    private ViewPagerAdapter viewPagerAdapter;




    //判断最后一页
    private int maxPos = 2;
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        button=(Button)findViewById(R.id.button);

        // 初始化页面
        initViews();

        // 初始化底部小点
        initDots();
//        new Thread(new JsonThread()).start();
    }
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            List<Welcome> newInfoList = WelcomeJsonHelp.getListPerson(urlPath);
            Message msg = new Message();
            if(newInfoList != null) {
                msg.what = 1;
                msg.obj = newInfoList;
            } else {
                msg.what = 0;
            }
            shandler.sendMessage(msg);
        }
    }
    Handler shandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    persons = (List<Welcome>) msg.obj;
                    for(int i=0;i<persons.size();i++)
                    {
                        imageIds[i]=url_image+persons.get(i).getImage();
                    }
                    for (int i = 0; i < imageIds.length; i++) {
                        SmartImageView image=new SmartImageView(WelcomeActivity.this);
                        image.setImageUrl(imageIds[i]);
                        imageList.add(image);
                    }
                    viewPagerAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private void initViews() {
        imageList = new ArrayList<SmartImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            SmartImageView image=new SmartImageView(WelcomeActivity.this);
//            image.setImageUrl(imageIds[i]);
            if(imageIds[i]==null) {
                image.setImageResource(image_bendi[i]);
            }else {
                image.setImageUrl(imageIds[i]);
            }
            imageList.add(image);
        }

        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(imageList, this);

        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        // 绑定回调
        vp.setOnPageChangeListener(this);
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[imageList.size()];

        // 循环取得小点图片
        for (int i = 0; i < imageList.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > imageList.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }
    public void goHome() {
        // 跳转
        Intent intent = new Intent(WelcomeActivity.this, FristActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *
     * method desc：设置已经引导过了，下次启动不用再次引导
     */
    public void setGuided() {
        SharedPreferences preferences = this.getSharedPreferences(
                "first_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // 存入数据
        editor.putBoolean("isFirstIn", false);
        // 提交修改
        editor.commit();
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
        switch (arg0) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                flag = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                flag = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if (vp.getCurrentItem() == vp.getAdapter().getCount() - 1 && !flag) {
                    button.setVisibility(View.VISIBLE);
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // 设置已经引导
                            setGuided();
                            goHome();
                        }
                    });
                }
                flag = true;
                break;
        }
    }


        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurrentDot(arg0);
    }
    public class ViewPagerAdapter extends PagerAdapter {

        // 界面列表
        private List<SmartImageView> views;
        private Activity activity;

        private static final String SHAREDPREFERENCES_NAME = "first_pref";

        public ViewPagerAdapter(List<SmartImageView> views, Activity activity) {
            this.views = views;
            this.activity = activity;
        }

        // 销毁arg1位置的界面
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(views.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        // 获得当前界面数
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        // 初始化arg1位置的界面
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1), 0);
            return views.get(arg1);
        }



        // 判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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
