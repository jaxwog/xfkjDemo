package com.xfkj.mrxuyaohui.xfkjdemo.util.doc;

import com.xfkj.mrxuyaohui.xfkjdemo.bean.Doc_ZiLiaoBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/12.
 */
public class Doc_Ziliao_jsonHelp {
    public static List<Doc_ZiLiaoBean> getListPerson(String urlPath) {
        try{
            List<Doc_ZiLiaoBean> mlists = new ArrayList<Doc_ZiLiaoBean>();
            byte[] data = readParse(urlPath);

            JSONArray jsonArray1=new JSONArray(new String(data));
            JSONObject item1 = jsonArray1.getJSONObject(0);
            JSONObject array =item1.getJSONObject("list");

             String attending=array.getString("attending");
              String docAddress=array.getString("docAddress");
             int docAge=array.getInt("docAge");
             String docDate=array.getString("docDate");
             String docMassage=array.getString("docMassage");
             String docName=array.getString("docName");
             String docPhone=array.getString("docPhone");
             String docServe=array.getString("docServe");
             String docSex=array.getString("docSex");
            String db_secondId=array.getString("db_secondId");
            String docScore=array.getString("docScore");
            String docSoreTime=array.getString("docSoreTime");

            mlists.add(new Doc_ZiLiaoBean(attending,docAddress,docAge,docSex,docServe,docPhone,docName,docMassage,docDate,db_secondId,docScore,docSoreTime));
            return mlists;
        }
        catch (Exception e) {
            e.printStackTrace();
//    } finally {
//        if(client != null) {
//            client.getConnectionManager().shutdown();		// 关闭和释放资源
//        }
        }
        return null;
    }
    /**
     * 从指定的url中获取字节数组
     *
     * @param urlPath
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] readParse(String urlPath) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
