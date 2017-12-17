package com.example.wudelin.cstudy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wudelin.cstudy.listviewadapter.Item;
import com.example.wudelin.cstudy.listviewadapter.ItemListViewAdapter;
import com.example.wudelin.cstudy.spans.SpansManager;
import com.example.wudelin.cstudy.util.HttpUtil;
import com.example.wudelin.cstudy.util.URL;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wudelin on 2017/11/23.
 */

public class PersonalFragment extends PageFragment{
    private View mView;
    private List<Item> itemList;
    private ListView listView;
    private String[] itemText;
    private int[] itemImage;
    private int[] itemIcon = new int[5];
    private ItemListViewAdapter listViewAdapter;
    private CircleImageView circleImageView;
    private RelativeLayout relativeLayout;
    private TextView personal_username;
    private String score;

    public static final String USERNAME = "username";
    private String roomId = "34053888737282";
    boolean isLogin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.viewpage_fragment_personal,container,false);
        listView = mView.findViewById(R.id.personal_listview);
        circleImageView = mView.findViewById(R.id.icon_image);
        relativeLayout = mView.findViewById(R.id.lay_login);
        personal_username = mView.findViewById(R.id.personal_username);

        inits();
        listViewAdapter = new ItemListViewAdapter(mView.getContext(),R.layout.personal_listview_item,itemList);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        Intent intent1 = new Intent(getContext(),NoteListActivity.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        showProgress();
                        break;
                    case 2:
                        showScoreDialog();

                        break;
                    case 3:
                        Intent intent = new Intent(getContext(),PersonalSettingActivity.class);
                        startActivity(intent);
                        break;
                    default:
                }
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mView.getContext(), "wowowo", Toast.LENGTH_SHORT).show();
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLogin) {
                    Log.d("wdl", "onClick: "+"已登录");
                }else{
                    Intent intent = new Intent(mView.getContext(), LoginActivity.class);
                    startActivity(intent);

                }
            }
        });
        return mView;
    }

    private void showProgress() {
        Intent intent = new Intent(getContext(),LearnProgressActivity.class);
        startActivity(intent);
    }

    private void showScoreDialog() {

        SharedPreferences prf = PreferenceManager.
                getDefaultSharedPreferences(mView.getContext());
        int score = prf.getInt("SCORE",0);
        final String name = prf.getString("USERNAME","");
        Log.d("wdl", "showScoreDialog: "+score);
        queryScore(name);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setCancelable(true);
        dialog.setTitle("查看任务成绩");
        dialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialog.setMessage("当前成绩:"+score);
        dialog.show();
    }

    private void queryScore(String name) {
        String url = URL.HTTP_URL_REQ_SCORE+name;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    String res = response.body().string();
                if (!TextUtils.isEmpty(res)) {
                    Message message = new Message();
                    message.what = 0;
                    Bundle bundle = new Bundle();
                    bundle.putString("response_text", res);
                    message.setData(bundle);
                    myHandler.sendMessage(message);
                }
            }
        });
    }
    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    score = message.getData().getString("response_text");
                    break;
                default:
            }
        }
    };
    @Override
    public void onResume() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mView.getContext());
        String username = prefs.getString(USERNAME,"");
        isLogin = prefs.getBoolean("IS_LOGIN",false);
        if(!TextUtils.isEmpty(username)&&isLogin){
            personal_username.setText(username);

        }else{
            personal_username.setText("您还未登录");
        }
        super.onResume();
    }

    private void inits() {
        itemList = new ArrayList<>();
        itemText = new String[]{"笔记","学习进度","任务成绩","设置"};
        Arrays.fill(itemIcon,R.mipmap.ic_next);
        itemImage = new int[]{R.mipmap.personal_notebook,R.mipmap.personal_progress,
                R.mipmap.personal_score,R.mipmap.personal_setting};
        for(int i = 0;i<itemImage.length;i++){
            Item item = new Item(itemImage[i],itemText[i],itemIcon[i]);
            itemList.add(item);
        }

    }
}
