package com.example.myboxgame;
/*
* 地图名称类
* */
public class ChoiceMapData {
    String MapName;   // 地图名称（即第一关、第二关等）
//    构造函数
    public ChoiceMapData(String MapName){
        this.MapName = MapName;
    }

    public String getMapName() {
        return MapName;
    }

    public void setMapName(String mapName) {
        MapName = mapName;
    }
}
