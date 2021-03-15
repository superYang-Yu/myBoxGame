package com.example.myboxgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
* 选择关卡界面
* */
public class ChoiceActivity extends AppCompatActivity {
//    声明ListView控件
    ListView lv_mapList;
//    实例化一个地图选择的列表
    private List<ChoiceMapData> choiceMapDataList = new ArrayList<>();
    Intent intent_mapCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
//        绑定控件
        lv_mapList = findViewById(R.id.lv_mapList);
//        自定义的方法，放入数据
        initChoiceMapDate();
//        参数1：上下问环境；参数2：显示数据的布局；参数3：传入的数据
        final ChoiceList_adapter choiceListAdapter = new ChoiceList_adapter(ChoiceActivity.this,
                R.layout.choicelist_item, choiceMapDataList);
//        设置适配器
        lv_mapList.setAdapter(choiceListAdapter);
        /*
        * 当条目被点击时自动回调
        * */
        intent_mapCode = new Intent(this, GameActivity.class);
        lv_mapList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                点击条目后携带数据跳转
                intent_mapCode.putExtra("mapCode",position);
                startActivity(intent_mapCode);
            }
        });
    }

    /*
    * 利用循环放入数据
    * */
    MapData mapData = new MapData();
    public void initChoiceMapDate(){
        for (int i=0; i<mapData.mapDataList.size(); i++){
            ChoiceMapData choiceMapData = new ChoiceMapData("推箱子第" + (i+1) + "关");
            choiceMapDataList.add(choiceMapData);
        }
    }
}
