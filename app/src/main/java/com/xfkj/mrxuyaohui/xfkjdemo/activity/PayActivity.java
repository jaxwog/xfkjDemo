package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.pay.Constants;
import com.xfkj.mrxuyaohui.xfkjdemo.util.pay.MD5;
import com.xfkj.mrxuyaohui.xfkjdemo.util.pay.Util;
import com.xfkj.mrxuyaohui.xfkjdemo.util.time.DateTimePickDialogUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PayActivity extends Activity {
    private Button pay_but;
    private EditText timepick;
    private EditText phone;
    private static final String TAG = "MicroMsg.SDKSample.PayActivity";
    private String initStartDateTime = "2013年9月3日 14:44"; // 初始化开始时间

    PayReq req;
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    Map<String,String> resultunifiedorder;
    StringBuffer sb;


    private TextView fuwu_name;
    private TextView fuwu_price;
    private Button title;

    //从上一个页面获取到的数据
    private String fuwu;
    private String price;
    private String zhifu_chanshu;
    private String getZhifu_chanshu;
    private int docId;
    private int Billtapeid_before;
    private int xiang;
    private TableLayout zhengzhuang1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_activity_main);
        zhengzhuang1=(TableLayout)findViewById(R.id.content);
        imageView=(ImageView)findViewById(R.id.image);
        title=(Button)findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //从上一个页面获取服务项和资费
        Intent intent=getIntent();
        price=intent.getStringExtra("price");
        fuwu=intent.getStringExtra("fuwu");
        Billtapeid_before=intent.getIntExtra("Billtapeid",-1);
        docId=intent.getIntExtra("docId",-1);
        xiang=intent.getIntExtra("xiang",-1);
        getZhifu_chanshu=intent.getStringExtra("zhifu");
        if(xiang==2)
        {
            zhengzhuang1.setVisibility(View.VISIBLE);
        }else {
            zhengzhuang1.setVisibility(View.GONE);
        }


        fuwu_name=(TextView)findViewById(R.id.fuwu_name);
        fuwu_name.setText(fuwu);
        fuwu_price=(TextView)findViewById(R.id.fuwu_price);
        fuwu_price.setText(price+"元");
        pay_but=(Button)findViewById(R.id.pay);
        timepick=(EditText)findViewById(R.id.time);
        phone=(EditText)findViewById(R.id.phone);
        timepick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        PayActivity.this, initStartDateTime);
                dateTimePicKDialog.dateTimePicKDialog(timepick);

            }
        });
        req = new PayReq();
        sb=new StringBuffer();

        msgApi.registerApp(Constants.APP_ID);
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();

    }
    public void click(View v)
    {
       switch (v.getId())
       {
           case R.id.pay:
               if(((MyAppliction)getApplication()).getFlag()){
                    if (check()){
                        pay();
                    }
               }else {
                   Toast.makeText(PayActivity.this,"你还未登录！请先登录...",Toast.LENGTH_SHORT).show();
               }
               break;
       }
    }
    //判断用户输入的信息
    public boolean check()
    {
        if(phone.getText().toString().length()==0||timepick.getText().toString().length()==0)
        {
            Toast.makeText(PayActivity.this,"请填写完整信息...",Toast.LENGTH_SHORT).show();
        }else {
            String remark = phone.getText().toString().trim();
            try {
                remark = URLEncoder.encode(remark, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            zhifu_chanshu = IpCofig.getIP_SERVER() + "MyheartDoctot/BillsAdd?Billappointmenttime=" + timepick.getText().toString().replace(" ", "%20") + "&UserId=" + ((MyAppliction) getApplication()).getUserId() + "&DocId=" + docId + "&Billpaytape=%E5%BE%AE%E4%BF%A1&BillRemarks=" + remark + "&Billtapeid=" + Billtapeid_before;
            ((MyAppliction) getApplication()).setWeixin_zhifu(zhifu_chanshu);
            return true;
        }
        return false;

    }
    public void pay()
    {
        genPayReq();
        sendPayReq();
    }
    /**
     生成签名
     */

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion",packageSign);
        return packageSign;
    }
    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion",appSign);
        return appSign;
    }
    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<"+params.get(i).getName()+">");


            sb.append(params.get(i).getValue());
            sb.append("</"+params.get(i).getName()+">");
        }
        sb.append("</xml>");

        Log.e("orion",sb.toString());
        return sb.toString();
    }

    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String,String>> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(PayActivity.this, getString(R.string.app_tip), getString(R.string.getting_prepayid));
        }

        @Override
        protected void onPostExecute(Map<String,String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            System.out.println("得到的prepay_id为："+result.get("prepay_id"));
            resultunifiedorder=result;

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String,String>  doInBackground(Void... params) {

            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();

            Log.e("orion", entity);

            byte[] buf = Util.httpPost(url, entity);

            String content = new String(buf);
            Log.e("orion", content);
            Map<String,String> xml=decodeXml(content);

            return xml;
        }
    }



    public Map<String,String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion",e.toString());
        }
        return null;

    }


    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }



    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }


    //
    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();

        try {
            String	nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
            packageParams.add(new BasicNameValuePair("body",getZhifu_chanshu));
            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", "http://121.40.35.3/test"));
            packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));
            packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
            packageParams.add(new BasicNameValuePair("total_fee",""+Integer.parseInt(price)*100));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));


            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));


            String xmlstring =toXml(packageParams);

            return xmlstring;

        } catch (Exception e) {
            return null;
        }


    }
    private void genPayReq() {

        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n"+req.sign+"\n\n");
        Log.e("orion", signParams.toString());

    }
    private void sendPayReq() {
        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pay, menu);
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
