package com.xfkj.mrxuyaohui.xfkjdemo.util;

import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.Bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.xuyaohui on 2015/5/11.
 */
public class Peo_Collects_All_jsonHelp {
    public static List<Bean> getListPerson(String urlPath) {
// String name, String address, String sex, String date, String password, String attending, String massage, float score, int scoreTime, int age, String imageUrl, String phone) {

        try{
            List<Bean> mlists = new ArrayList<Bean>();
            byte[] data = readParse(urlPath);

            JSONArray array=new JSONArray(new String(data));
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                String attending= item.getString("attending");
                String address=item.getString("docAddress");
                int age=item.getInt("docAge");
                String date=item.getString("docDate");
                String imageUrl=item.getString("docImg");
                String massage=item.getString("docMassage");
                String name=item.getString("docName");
                String passwd=item.getString("docPasswd");
                String phone=item.getString("docPhone");
                String score=item.getString("docScore");
                String sex=item.getString("docSex");
                String scoreTime=item.getString("docSoreTime");
                String db_secondId=item.getString("db_secondId");
                int docId=item.getInt("docId");
                String docServe=item.getString("docServe");
                mlists.add(new Bean(name,address,sex,date, passwd,attending,massage,score, scoreTime,age,imageUrl,phone,db_secondId,docId,docServe));
            }
            return mlists;}
        catch (Exception e) {
            e.printStackTrace();
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

