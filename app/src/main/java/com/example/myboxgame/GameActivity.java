package com.example.myboxgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_up, btn_down, btn_left, btn_right;
    GameView gameView;
    MapData mapData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setViewID();
        getMapCode();
    }

    /*
    * 设置控件绑定、监听
    * */
    public void setViewID(){
        gameView = findViewById(R.id.gameView);
        btn_up = findViewById(R.id.btn_up);
        btn_down = findViewById(R.id.btn_down);
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);
        btn_up.setOnClickListener(this);
        btn_down.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        mapData = new MapData();
    }

    /*
    * 点击监听
    * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_up:
                gameView.moveUp();
                break;
            case R.id.btn_down:
                gameView.moveDown();
                break;
            case R.id.btn_left:
                gameView.moveLeft();
                break;
            case R.id.btn_right:
                gameView.moveRight();
                break;
        }
    }
    /*
    * 接收ChoiceActivity传送过来的数据
    * */
    int mapCode;
    public void getMapCode(){
//        参数1：键值； 参数2：默认值，即没有数据传过来时的值
        mapCode = getIntent().getIntExtra("mapCode",-1);
        if(mapCode == -1){
            Toast.makeText(this, "mapCode为空", Toast.LENGTH_SHORT).show();
            return;
        }else {
            gameView.setMap(mapData.mapDataList.get(mapCode));
        }
    }
}
