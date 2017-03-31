package com.xfkj.mrxuyaohui.xfkjdemo.util;

/**
 * Created by Mr.xuyaohui on 2015/4/19.
 */


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Collection_check_JsonHelp {

    public static String getListPerson(String urlPath) {
        String result=null;
        try {
            byte[] data = readParse(urlPath);
            System.out.println("得到的json"+new String(data));
            JSONArray array=new JSONArray(new String(data));
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                 result=item.getString("result");
            }
            return result;
        }
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
    public static byte[]  readParse(String urlPath) throws Exception {
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
