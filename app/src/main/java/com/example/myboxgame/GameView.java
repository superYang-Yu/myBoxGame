package com.example.myboxgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;

public class GameView extends View{

    final int MAP_H = 15;  // 地图的行数
    final int MAP_L = 15;  // 地图的列数

    private int width; // view的宽
    private int height; // view的高
    //     当前坐标的属性值
    final int WALL = 0; //墙
    final int EMPTY = 1; // 空
    final int GAME_MAN = 2; // 玩家
    final int BOX_NOT = 3; // 空箱子（待移动）
    final int BOX_FULL = 4; // 满箱子
    final int FLOWER = 5; // 花（目的地）
    final int FLOWER_MAN = 6; // 花和人重叠在一起

    /*
     * 图片资源赋值
     * */
    Bitmap bitmap_wall = BitmapFactory.decodeResource(getResources(),R.drawable.wall);
    Bitmap bitmap_empty = BitmapFactory.decodeResource(getResources(),R.drawable.empty);
    Bitmap bitmap_gameMan = BitmapFactory.decodeResource(getResources(),R.drawable.gameman);
    Bitmap bitmap_boxNot = BitmapFactory.decodeResource(getResources(),R.drawable.box_not);
    Bitmap bitmap_boxFill = BitmapFactory.decodeResource(getResources(),R.drawable.box_fill);
    Bitmap bitmap_flower = BitmapFactory.decodeResource(getResources(),R.drawable.flower);
    Bitmap bitmap_flowerAndMan = BitmapFactory.decodeResource(getResources(),R.drawable.flowerandman);
    //二维地图数据
    int[][] gameMap =  {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,5,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,3,1,3,5,0,0,0,0,0},
            {0,0,0,0,5,3,2,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,3,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,5,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };
    //    参数1：上下文环境  参数2：引用布局时在layout中的参数
    public GameView(Context context, AttributeSet attrs) { super(context, attrs); }
    //    获取View的大小
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSper){
        super.onMeasure(widthMeasureSpec, heightMeasureSper);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSper);
        width = specWidthSize;
        height = specHeightSize;
        setMeasuredDimension(specWidthSize, specHeightSize);
    }

    Paint mPaint;
    Canvas mCanvas;
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        mPaint = new Paint();
        mCanvas = new Canvas();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        this.mCanvas = canvas;
        paintMap();
    }

    protected void paintMap(){
        for (int h=0; h<MAP_H; h++){
            for (int l=0; l<MAP_L; l++){
//                二维数组的下标 --> 相当于地图上的一个点
                int bitmap_point = gameMap[h][l];
                /*
                 * Left相当于X，Top相当于Y
                 *Right - X ：长方形的长
                 *Bottom - Y：长方形的宽
                 * */
                Rect rect = new Rect
                        ((width/MAP_L)*l, (height/MAP_H)*h,(width/MAP_L)*(l+1),(height/MAP_H)*(h+1));
//                根据二维数组中的值绘制相应的图
                switch (bitmap_point){
                    case WALL:
                        mCanvas.drawBitmap(bitmap_wall,null,rect,mPaint);
                        break;
                    case EMPTY:
                        mCanvas.drawBitmap(bitmap_empty,null,rect,mPaint);
                        break;
                    case GAME_MAN:
                        mCanvas.drawBitmap(bitmap_gameMan,null,rect,mPaint);
                        break;
                    case BOX_NOT:
                        mCanvas.drawBitmap(bitmap_boxNot,null,rect,mPaint);
                        break;
                    case BOX_FULL:
                        mCanvas.drawBitmap(bitmap_boxFill,null,rect,mPaint);
                        break;
                    case FLOWER:
                        mCanvas.drawBitmap(bitmap_flower,null,rect,mPaint);
                        break;
                    case FLOWER_MAN:
                        mCanvas.drawBitmap(bitmap_flowerAndMan,null,rect,mPaint);
                        break;
                    default:
                        Toast.makeText(getContext(), "地图数据有误:"+bitmap_point, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }
    /*
     *      设置地图方法
     * */
    public void setMap(int[][] mapData){

        for (int h=0; h<MAP_L; h++){
            for (int l=0; l<MAP_H; l++){
                gameMap[h][l] = mapData[h][l];
            }
        }
        invalidate();  // 刷新画布
    }

    //    移动方法
   private int nowWhat = -1;   // 当前遍历位置值
    private int nextWhat = -1;  // 当前遍历位置值上一行的值
    private int nextWhat2 = -1;  // 上两个值
    public void moveUp() {
        for (int h = 0; h < MAP_H; h++) {
            for (int l = 0; l < MAP_L; l++) {
                nowWhat = gameMap[h][l]; // 遍历地图，不断地取值赋值
//                当值为人物或者人加花时才可移动
                if (nowWhat == GAME_MAN || nowWhat == FLOWER_MAN) {
                    nextWhat = gameMap[h-1][l];
                    nextWhat2 = gameMap[h-2][l];
                    switch (nextWhat){
//                        人物上面是墙
                        case WALL:
                            Toast.makeText(getContext(), "人物上面是墙", Toast.LENGTH_SHORT).show();
                            break;
//                            人物上面是空
                        case EMPTY:
//                            判断当前是否为人物加花的状态
                            if (nowWhat == FLOWER_MAN){
                                nowWhat = FLOWER;
                            }else {
                                nowWhat = EMPTY;
                            }
                            nextWhat = GAME_MAN;
                            gameMap[h][l] = nowWhat;
                            gameMap[h-1][l] = nextWhat;
                            break;
//                            上面是空箱
                        case BOX_NOT:
                            // 判断当前是否为人物加花的状态
                            if (nowWhat == FLOWER_MAN){
                                nowWhat = FLOWER;
                            }else {
                                nowWhat = EMPTY;
                            }
//                            箱子的上面是花
                            if (nextWhat2 == FLOWER){
                                nextWhat = GAME_MAN;
                                nextWhat2 = BOX_FULL;
                            }
//                            箱子上面是箱子或者墙
                            else if (nextWhat2 == WALL || nextWhat2 == BOX_NOT || nextWhat2 == BOX_FULL){
                                Toast.makeText(getContext(), "箱子上面被堵了", Toast.LENGTH_SHORT).show();
                                break;
                            }
//                            箱子上面是空的
                           else if (nextWhat2 == EMPTY){
                                nextWhat = GAME_MAN;
                                nextWhat2 = BOX_NOT;
                            }
                            gameMap[h][l] = nowWhat;
                            gameMap[h-1][l] = nextWhat;
                            gameMap[h-2][l] = nextWhat2;
                            Log.i("nextWhat2",""+gameMap[h-2][l]);
                            break;
//                            上面是满箱子
                        case BOX_FULL:
                            // 判断当前是否为人物加花的状态
                            if (nowWhat == FLOWER_MAN){
                                nowWhat = FLOWER;
                            }else {
                                nowWhat = EMPTY;
                            }
//                            满箱子上面是花
                            if (nextWhat2 == FLOWER){
                                nextWhat2 = BOX_FULL;
                                nextWhat = FLOWER_MAN;
                            }
//                            满箱子上面是墙、箱子
                            else if (nextWhat2 == WALL || nextWhat2 == BOX_NOT || nextWhat2 == BOX_FULL){
                                Toast.makeText(getContext(), "箱子上面被堵了", Toast.LENGTH_SHORT).show();
                                break;
                            }
//                            满箱子上面是空
                            else if (nextWhat2 == EMPTY){
                                nextWhat = FLOWER_MAN;
                                nextWhat2 = BOX_NOT;
                        }
                            gameMap[h][l] = nowWhat;
                            gameMap[h-1][l] = nextWhat;
                            gameMap[h-2][l] = nextWhat2;
                            break;
//                            人物上面是花
                        case FLOWER:
                            // 判断当前是否为人物加花的状态
                            if (nowWhat == FLOWER_MAN){
                                nowWhat = FLOWER;
                            }else {
                                nowWhat = EMPTY;
                            }
                            nextWhat = FLOWER_MAN;
                            gameMap[h][l] = nowWhat;
                            gameMap[h-1][l] = nextWhat;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        invalidate();
    }

    public void moveDown(){

    }

    public void moveLeft(){

    }

    public void moveRight(){

    }
}
