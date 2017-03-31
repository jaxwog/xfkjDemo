package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.ToRoundBitmapUtil;
import com.xfkj.mrxuyaohui.xfkjdemo.util.sd_exist;

import java.io.File;

public class MyCenter extends Activity {

    private Button login=null;//添加登录按钮
    private Button collection=null;//添加收藏按钮
    private Button history=null;//添加历史记录按钮
    private Button feedback=null;//添加建议反馈按钮
    private Button setting=null;//添加设置按钮
    private Button off;         //注销按钮

    private long exitTime = 0;//退出程序
    private String name;//用户的手机号

    private Uri picPath;

    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";

    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    /**
     * 选择文件
     */
    public static final int TO_SELECT_PHOTO = 3;
    //选择图片
    private ImageButton select_pic;

    private final static int REQUESTCODE = 1;//返回的结果码
    //用户是否登录
    private boolean isLogin=false;
    //个人资料的显示
    private Button ziliao;
    //选择图片
    private Intent dataIntent = null;



    //使用SharedPreferences储存用户信息
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_people_login);
        login=(Button)this.findViewById(R.id.login);
        collection=(Button)this.findViewById(R.id.collection);
        history=(Button)this.findViewById(R.id.history);
        feedback=(Button)this.findViewById(R.id.feedback);
        setting=(Button)this.findViewById(R.id.setting);
        //个人资料
        ziliao=(Button)this.findViewById(R.id.peo_ziliao);
        //注销按钮
        off=(Button)this.findViewById(R.id.logout);
        //选择图片
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();//获取编辑器
//        select_pic=(ImageButton)this.findViewById(R.id.select_pic);
        //判断用户的登录状态
        System.out.println("用户登录前的flag为：" + isLogin);
    }
    public void onClick(View v)
    {
        Intent intent=new Intent();
        switch (v.getId())
        {
//            case R.id.select_pic://选择图片
//                isLogin=((MyAppliction)getApplication()).getFlag();
//                //改改了
//                if (isLogin){
//                    select_pic.setClickable(true);
//                    ShowPickDialog();
//                }else
//                {
//                    select_pic.setClickable(false);
//                    Toast.makeText(MyCenter.this,"你还未登录，请先登录！",Toast.LENGTH_SHORT).show();
//                }
//                break;
            case R.id.login://跳转到用户登录界面
                isLogin=((MyAppliction)getApplication()).getFlag();
                if(!isLogin) {
                    intent.setClass(MyCenter.this, Login.class);
                    startActivityForResult(intent, REQUESTCODE);
                }else {
                    Toast.makeText(MyCenter.this,"你已经登录过了！",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.collection://跳转到收藏页面
                intent.setClass(MyCenter.this,Collection.class);
                startActivity(intent);
                break;
            case R.id.history://跳转到历史记录页面
                intent.setClass(MyCenter.this,History.class);
                startActivity(intent);
                break;
            case R.id.feedback://跳转到意见反馈记录页面
                intent.setClass(MyCenter.this,Feedback.class);
                startActivity(intent);
                break;
            case R.id.setting://跳转到设置页面
                intent.setClass(MyCenter.this,Setting.class);
                startActivity(intent);
                break;
            case R.id.logout://注销功能
                Intent logoutIntent = new Intent(this, FristActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                ((MyAppliction)getApplication()).setFlag(false);
                startActivity(logoutIntent);
                break;
            case R.id.peo_ziliao:
                intent.setClass(MyCenter.this,Person_ziliao.class);
                intent.putExtra("userPhone",name);
                startActivity(intent);
                break;
        }
    }
    private void ShowPickDialog() {
        new AlertDialog.Builder(this)
                .setTitle("设置头像...")
                .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        /**
                         * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
                         * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下
                         */
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, 2);

                    }
                })
                .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        //下面这句指定调用相机拍照后的照片存储的路径
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(Environment
                                        .getExternalStorageDirectory(),
                                        "xiaoma.jpg")));
                        startActivityForResult(intent, 3);
                    }
                }).show();
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                try {
                    Bundle bundle = data.getExtras();
                    name = bundle.getString("username");
                    String logout = bundle.getString("logout");
                    login.setText(name);
                    ziliao.setVisibility(View.VISIBLE);
                    login.setClickable(false);
                    off.setVisibility(View.VISIBLE);
                    off.setText(logout);
                    select_pic.setClickable(true);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            // 如果是直接从相册获取
            case 2:
                startPhotoZoom(data.getData());
                break;
            // 如果是调用相机拍照时
            case 3:
                File temp = new File(Environment.getExternalStorageDirectory()
                        + "/xiaoma.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
            // 取得裁剪后的图片
            case 4:
                /**
                 * 非空判断大家一定要验证，如果不验证的话，
                 * 在剪裁之后如果发现不满意，要重新裁剪，丢弃
                 * 当前功能时，会报NullException，小马只
                 * 在这个地方加下，大家可以根据不同情况在合适的
                 * 地方做判断处理类似情况
                 *
                 */
                if(data != null){
                    dataIntent = data;
                    setPicToView(data);
                }
                break;
            default:
                break;

        }
//        if (requestCode == REQUESTCODE) {
//            if (resultCode == 1) {
//                //设置登录名和注销按钮键
////              String  Name=data.getStringExtra("username");
//                Bundle bundle = data.getExtras();
//                name = bundle.getString("username");
//                String logout = bundle.getString("logout");
//                login.setText(name);
//                ziliao.setVisibility(View.VISIBLE);
//                login.setClickable(false);
//                off.setVisibility(View.VISIBLE);
//                off.setText(logout);
//                select_pic.setClickable(true);
//                off.setBackgroundColor(getResources().getColor(R.color.lanse));
//            }else if(resultCode==3){
//
//            }
//        }
    }
    public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能,
		 * 是直接调本地库的，小马不懂C C++  这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么
		 * 制做的了...吼吼
		 */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo=ToRoundBitmapUtil.toRoundBitmap(photo);
            Drawable drawable = new BitmapDrawable(photo);
            select_pic.setBackgroundDrawable(drawable);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_center, menu);
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
}
