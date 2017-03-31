package com.xfkj.mrxuyaohui.xfkjdemo.util;

import com.xfkj.mrxuyaohui.xfkjdemo.bean.Doc_Login;
import com.xfkj.mrxuyaohui.xfkjdemo.bean.User_Login;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.xuyaohui on 2015/5/4.
 */
public class Login_JsonHelp{
        public static List<User_Login> getListPerson(String message) {
            try{
                List<User_Login> mlists = new ArrayList<User_Login>();
                JSONArray array=new JSONArray(message);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                   int userId=item.getInt("UserId");
                    String result=item.getString("Sucess");
                    String phone=item.getString("UserPhone");
                    mlists.add(new User_Login(userId,result,phone));
                }
                return mlists;}
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    public static List<Doc_Login> getDocListPerson(String message) {
        try{
            List<Doc_Login> mlists = new ArrayList<Doc_Login>();
            JSONArray array=new JSONArray(message);
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                int userId=item.getInt("DocId");
                String result=item.getString("Sucess");
                String phone=item.getString("DocPhone");
                mlists.add(new Doc_Login(userId,result,phone));
            }
            return mlists;}
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
