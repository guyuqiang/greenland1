package hdcz.com.app.greenland1.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guyuqiang on 2017/12/28.17:38
 */

public class SharedHelper {
    private Context mcontext;
    public SharedHelper (){

    }
    public SharedHelper(Context mcontext){
        this.mcontext = mcontext;
    }
    //保存数据
    public void saveData(String name,String password,String ljtext){
        //获取sharedpreferences对象
        SharedPreferences sp = mcontext.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",name);
        editor.putString("password",password);
        editor.putString("ljtext",ljtext);
        editor.commit();
    }
    //获取数据
    public Map<String,String> getData(){
        Map<String,String> data = new HashMap<String,String>();
        SharedPreferences sp = mcontext.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        data.put("name",sp.getString("name",""));
        data.put("password",sp.getString("password",""));
        data.put("ljtext",sp.getString("ljtext",""));
        return data;
    }
}
