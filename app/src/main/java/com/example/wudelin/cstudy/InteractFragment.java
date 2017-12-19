package com.example.wudelin.cstudy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wudelin.cstudy.draw.NetWorkMessage;
import com.example.wudelin.cstudy.draw.YouDrawIGuessActivity;
import com.example.wudelin.cstudy.recyclerviewadapter.Msg;
import com.example.wudelin.cstudy.recyclerviewadapter.MsgAdapter;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wudelin on 2017/11/23.
 */

public class InteractFragment extends PageFragment implements EMMessageListener {
    private View mView;
    private EditText inputText;
    private Button sendBtn;
    private Button takeIn;
    //private EMGroup group;
    private String roomId = "34053888737282";
    //private EMConversation conversation;
    // 显示内容的 TextView
    //private TextView mContentText;
    // 消息监听器
    private EMMessageListener mMessageListener;
    // 当前会话对象
    private EMConversation mConversation;
    private Boolean isLogin;

    private RecyclerView recyclerView;
    private MsgAdapter msgAdapter;
    private LinearLayoutManager layoutManager;
   // private TextView button;
    private List<Msg> msgList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.viewpage_fragment_interact, container, false);
        mMessageListener = this;
        initView();
        initConversation();
        return mView;
    }
    private void initView() {
        inputText = mView.findViewById(R.id.input_text);
        sendBtn = mView.findViewById(R.id.send_btn);
       // mContentText = mView.findViewById(R.id.content_view_interact);
        takeIn = mView.findViewById(R.id.take_in);
        recyclerView =  mView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(mView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        msgAdapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(msgAdapter);
        // 设置textview可滚动，需配合xml布局设置
       // mContentText.setMovementMethod(new ScrollingMovementMethod());

        takeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(getActivity(), YouDrawIGuessActivity.class));
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String content = inputText.getText().toString().trim();
                if (!TextUtils.isEmpty(content)&&isLogin) {
                    inputText.setText("");

                    // 创建一条新消息，第一个参数为消息内容，第二个为接受者username
                    EMMessage message = EMMessage.createTxtSendMessage(content, roomId);
                    // 将新的消息内容和时间加入到下边
                    //mContentText.setText(mContentText.getText() + "\n发送：" + content + " - time: " + message.getMsgTime());
                    message.setChatType(EMMessage.ChatType.GroupChat);
                    // 调用发送消息的方法
                    EMClient.getInstance().chatManager().sendMessage(message);
                    // 为消息设置回调
                    message.setMessageStatusCallback(new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            // 消息发送成功，打印下日志，正常操作应该去刷新ui
                            Log.i("wdl", "send message on success");
                            String message = content;
                            Message msg = mHandler.obtainMessage();
                            msg.what = 1;
                            msg.obj = message;
                            mHandler.sendMessage(msg);

                        }

                        @Override
                        public void onError(int i, String s) {
                            // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
                            Log.i("wdl", "send message on error " + i + " - " + s);
                        }

                        @Override
                        public void onProgress(int i, String s) {
                            // 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
                        }
                    });
                }else if(!isLogin){
                    Toast.makeText(mView.getContext(),"您未登录,请前往登录",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(content)){
                    Toast.makeText(mView.getContext(),"内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 初始化会话对象，并且根据需要加载更多消息
     */
    private void initConversation() {

        /**
         * 初始化会话对象，这里有三个参数么，
         * 第一个表示会话的当前聊天的 useranme 或者 groupid
         * 第二个是绘画类型可以为空
         * 第三个表示如果会话不存在是否创建
         */
        mConversation = EMClient.getInstance().chatManager().getConversation(roomId, null, true);
        // 设置当前会话未读数为 0
        mConversation.markAllMessagesAsRead();
        int count = mConversation.getAllMessages().size();
        if (count < mConversation.getAllMsgCount() && count < 20) {
            // 获取已经在列表中的最上边的一条消息id
            String msgId = mConversation.getAllMessages().get(0).getMsgId();
            // 分页加载更多消息，需要传递已经加载的消息的最上边一条消息的id，以及需要加载的消息的条数
            mConversation.loadMoreMsgFromDB(msgId, 20 - count);
        }
        // 打开聊天界面获取最后一条消息内容并显示
        if (mConversation.getAllMessages().size() > 0) {
            EMMessage messge = mConversation.getLastMessage();
            EMTextMessageBody body = (EMTextMessageBody) messge.getBody();
            // 将消息内容和时间显示出来
            //mContentText.setText("聊天记录：" + body.getMessage() + " - time: " + mConversation.getLastMessage().getMsgTime());
        }
    }
    /**
     * 自定义实现Handler，主要用于刷新UI操作
     */

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    EMMessage message = (EMMessage) msg.obj;
                    // 这里只是简单的demo，也只是测试文字消息的收发，所以直接将body转为EMTextMessageBody去获取内容
                    EMTextMessageBody body = (EMTextMessageBody) message.getBody();
                    // 将新的消息内容和时间加入到下边
                    //mContentText.setText(mContentText.getText() + "\n接收：" + body.getMessage() + " - time: " + message.getMsgTime());
                    Msg msgM = new Msg(body.getMessage(),Msg.TYPE_RECEICE);
                    msgList.add(msgM);
                    msgAdapter.notifyItemInserted(msgList.size() - 1);
                    //当有新消息时刷新RecyclerView中的显示
                    recyclerView.scrollToPosition(msgList.size() - 1);
                    //将msgRecyclerView定位到最后一行
                    break;
                case 1:
                    String content = (String) msg.obj;
                    Msg msgN = new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msgN);
                    msgAdapter.notifyItemInserted(msgList.size()-1);
                    //当有新消息时刷新RecyclerView中的显示
                    recyclerView.scrollToPosition(msgList.size()-1);
                    //将msgRecyclerView定位到最后一行
                    break;
                    default:break;
            }
        }
    };
    @Override
    public void onResume() {
        isLogin = PreferenceManager.getDefaultSharedPreferences(
                mView.getContext()).getBoolean("IS_LOGIN",false);
        super.onResume();
        // 添加消息监听
        EMClient.getInstance().chatManager().addMessageListener(mMessageListener);

    }
    @Override
    public void onStop() {
        super.onStop();
        // 移除消息监听
        EMClient.getInstance().chatManager().removeMessageListener(mMessageListener);
    }


    @Override
    public void onMessageReceived(List<EMMessage> list) {
        // 循环遍历当前收到的消息
        Log.d("wdl", "onMessageReceived: ");
        for (EMMessage message : list) {
            // 设置消息为已读
            mConversation.markMessageAsRead(message.getMsgId());

            //因为消息监听回调这里是非ui线程，所以要用handler去更新ui
            Message msg = mHandler.obtainMessage();
            msg.what = 0;
            msg.obj = message;
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

        for (int i = 0; i < list.size(); i++) {
            // 透传消息
            EMMessage cmdMessage = list.get(i);
            EMCmdMessageBody body = (EMCmdMessageBody) cmdMessage.getBody();
           // Log.i("lzan13", body.action());
        }

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }


}
