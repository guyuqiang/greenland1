package hdcz.com.app.greenland1;


import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import hdcz.com.app.greenland1.fragment.FragementXiaoXi;
import hdcz.com.app.greenland1.fragment.FragmentAsset;
import hdcz.com.app.greenland1.fragment.FragmentPanDian;

/**
 * Created by guyuqiang on 2017/12/27.15:17
 */

public class InformationActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioButton rb;
    private FragementXiaoXi fgxiaoxi;
    private FragmentAsset fgasset;
    private FragmentPanDian fgpandian;
    private FragmentManager fManager;
    private Context mcontext;
    private Context context;

    @Override
    public  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_xiaoxi);
        mcontext = InformationActivity.this;
        context = getApplicationContext();
        fManager = getSupportFragmentManager();
        RadioGroup rg = findViewById(R.id.rg_tab_bar);
        rg.setOnCheckedChangeListener(this);
        //获取第一个单选按钮
        rb = findViewById(R.id.rb_xiaoxi);
        rb.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction ft = fManager.beginTransaction();
        hideAllFragment(ft);
        switch (checkedId){
            case R.id.rb_xiaoxi:
                if(fgxiaoxi ==null){
                    fgxiaoxi = new FragementXiaoXi();
                    ft.add(R.id.ly_content,fgxiaoxi);
                }else{
                    ft.show(fgxiaoxi);
                }
               break;
            case R.id.rb_asset:
                if(fgasset == null){
                    fgasset = new FragmentAsset();
                    ft.add(R.id.ly_content,fgasset);
                }else{
                    ft.show(fgasset);
                }
                break;
            case R.id.rb_pandian:
                if(fgpandian == null){
                    fgpandian = new FragmentPanDian();
                    ft.add(R.id.ly_content,fgpandian);
                }else{
                    ft.show(fgpandian);
                }
                break;
            }
            ft.commit();
        }
    //隐藏所有fagement
    public void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgxiaoxi !=null)fragmentTransaction.hide(fgxiaoxi);
        if(fgasset != null)fragmentTransaction.hide(fgasset);
        if(fgpandian != null)fragmentTransaction.hide(fgpandian);
    }
}
