package com.example.myboxgame;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
/*
* ListView的适配器
* */
public class ChoiceList_adapter extends ArrayAdapter<ChoiceMapData> {
    private int resourceId; // List子项布局的ID
    /*
    * 此方法用于将上下文环境（参数1）、子项布局的ID（参数2）、子项布局的数据（参数3）传递进来
    * */
    public ChoiceList_adapter( Context context, int resource, List<ChoiceMapData> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
//        获取当前项的ChoiceMapDate的实例
        ChoiceMapData choiceMapData = getItem(position);
//        convertView：用于保存之前加载好的布局（提高 ListView 的运行效率）
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mapName = view.findViewById(R.id.tv_mapName);
//            setTag()方法：将ViewHolder内的控件实例缓存到view中
            view.setTag(viewHolder);
        }else {
            view = convertView;
//            getTag()方法：当convertView不为空的时候，把viewHolder重新取出
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mapName.setText(choiceMapData.getMapName());
        return view;
    }
/*
*用于缓存控件的实例（提高 ListView 的运行效率）
* */
    class ViewHolder{
        TextView mapName;
    }

/*    * 无优化版的ListView的适配器的getVIew方法
    * public view getView(int position, View convertView, ViewGroup parent){
    * ChoiceMapData choiceMapData = getItem(position);
     * view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
     * TextView mapName = findViewByID(R.id.tv_mapName);
     * mapName.setText(choiceMapData.getMapName());
    * return view;
    * }
    *
    * */
}
