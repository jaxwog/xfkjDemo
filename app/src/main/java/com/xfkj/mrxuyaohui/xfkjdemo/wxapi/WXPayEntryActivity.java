package com.xfkj.mrxuyaohui.xfkjdemo.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.MyAppliction;
import com.xfkj.mrxuyaohui.xfkjdemo.bean.User_ziliao;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News_JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.pay.Constants;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_submit_JsonHelp;

import java.util.List;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
    private String result_register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX&&resp.errCode==0) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
            builder.setMessage("支付成功！请等待...");
            builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new Thread(new JsonThread()).start();
                }
            });
			builder.show();
		}else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("支付失败！请等待...");
            builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        }
	}
    //开启线程提交数据到服务器
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            String result = Register_submit_JsonHelp.getListPerson( ((MyAppliction) getApplication()).getWeixin_zhifu());
            Message msg = new Message();
            if(result != null) {
                msg.what = 1;
                msg.obj = result;
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
                    result_register=(String)msg.obj;
                    if (result_register.equals("sucess"))
                    {
                        Toast.makeText(WXPayEntryActivity.this, "信息提交成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(WXPayEntryActivity.this,"信息提交失败！",Toast.LENGTH_SHORT).show();
                        finish();
                    }


            }
        }
    };
}