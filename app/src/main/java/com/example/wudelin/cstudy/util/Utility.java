package com.example.wudelin.cstudy.util;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.example.wudelin.cstudy.db.Content;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wudelin on 2017/11/24.
 */

public class Utility {

    /*
    * 处理服务器返回的文本数据
    * */
    public static boolean handleContentResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray jsonArray = new JSONArray(response);
                for(int i = 0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Content content = new Content();
                    content.setL_name(object.getInt("l_name"));
                    Spanned result;
                    if (android.os.Build.VERSION.SDK_INT >= 25) {
                        result = Html.fromHtml(object.getString("contain"),Html.FROM_HTML_MODE_LEGACY);
                    } else {
                        result = Html.fromHtml(object.getString("contain"));
                    }
                    content.setContent(result.toString());
                    content.save();
                }
                return true;


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}
