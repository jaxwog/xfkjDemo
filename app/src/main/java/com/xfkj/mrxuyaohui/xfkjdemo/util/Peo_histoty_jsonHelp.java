package com.xfkj.mrxuyaohui.xfkjdemo.util;

import com.xfkj.mrxuyaohui.xfkjdemo.bean.Peo_history_Bean;
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
public class Peo_histoty_jsonHelp {
    public static List<Peo_history_Bean> getListPerson(String urlPath) {
// String name, String address, String sex, String date, String password, String attending, String massage, float score, int scoreTime, int age, String imageUrl, String phone) {

        try{
            List<Peo_history_Bean> mlists = new ArrayList<Peo_history_Bean>();
            byte[] data = readParse(urlPath);

            JSONArray jsonArray1=new JSONArray(new String(data));
            JSONObject item1 = jsonArray1.getJSONObject(0);
            JSONArray array =item1.getJSONArray("list");


            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);

                String time=item.getString("billTime");
                JSONObject docArray=item.getJSONObject("doc");
                int docId=docArray.getInt("docId");
                String docName=docArray.getString("docName");

                mlists.add(new Peo_history_Bean(time,docId,docName));
            }
            return mlists;}
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
