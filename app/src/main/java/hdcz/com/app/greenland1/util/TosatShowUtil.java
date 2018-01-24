package hdcz.com.app.greenland1.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import hdcz.com.app.greenland1.R;

/**
 * Created by guyuqiang on 2018/1/24.19:52
 */

public class TosatShowUtil {
    public static void showLong(Context context,String mesg){
        Toast toast = new Toast(context);
        //设置显示位置居中，X,Y偏移量都为0
        toast.setGravity(Gravity.CENTER,0,0);
        //获取自定义视图
        View view = LayoutInflater.from(context).inflate(R.layout.toastview,null);
        TextView textView = view.findViewById(R.id.toast_view);
        //设置文本
        textView.setText(mesg);
        //设置视图
        toast.setView(view);
        //设置时长
        toast.setDuration(Toast.LENGTH_LONG);
        //显示
        toast.show();
    }
    public static void showShort(Context context,String mesg){
        Toast toast = new Toast(context);
        //设置显示位置居中，X,Y偏移量都为0
        toast.setGravity(Gravity.CENTER,0,0);
        //获取自定义视图
        View view = LayoutInflater.from(context).inflate(R.layout.toastview,null);
        TextView textView = view.findViewById(R.id.toast_view);
        //设置文本
        textView.setText(mesg);
        //设置视图
        toast.setView(view);
        //设置时长
        toast.setDuration(Toast.LENGTH_SHORT);
        //显示
        toast.show();
    }
}
