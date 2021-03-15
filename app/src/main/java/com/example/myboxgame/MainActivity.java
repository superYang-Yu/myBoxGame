package com.example.myboxgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    开始游戏按钮监听方法
//   点击后跳转选择游戏关卡界面
    public void startGame(View view) {
        /*
        * 使用Intent(意图)跳转
        * 参数1：上下文环境
        * 参数2：跳转后的界面
        * */
        Intent intent_start = new Intent(this, ChoiceActivity.class);
//        启动跳转（一定要记得启动）
        startActivity(intent_start);
    }

//    关于游戏按钮监听方法，点击后弹出Toast
    public void aboutGame(View view) {
        Toast.makeText(this, "推箱子小游戏-----By:SuperWork", Toast.LENGTH_SHORT).show();
    }

//    退出游戏按钮监听方法
    public void exitGame(View view) {
        System.exit(0);
    }
}
