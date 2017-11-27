package com.example.wudelin.cstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.wudelin.cstudy.listviewadapter.Item;
import com.example.wudelin.cstudy.listviewadapter.ItemListViewAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.viewpage_fragment_personal,container,false);
        listView = mView.findViewById(R.id.personal_listview);
        circleImageView = mView.findViewById(R.id.icon_image);
        relativeLayout = mView.findViewById(R.id.lay_login);
        inits();
        listViewAdapter = new ItemListViewAdapter(mView.getContext(),R.layout.personal_listview_item,itemList);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
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
                Intent intent = new Intent(mView.getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        return mView;
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
