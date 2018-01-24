package hdcz.com.app.greenland1;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

import hdcz.com.app.greenland1.fragment.FragementXiaoXi;
import hdcz.com.app.greenland1.fragment.FragmentAsset;
import hdcz.com.app.greenland1.fragment.FragmentPanDian;
import hdcz.com.app.greenland1.sharedpreferences.SharedHelper;
import hdcz.com.app.greenland1.util.TosatShowUtil;

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
    private String xiaoxi_result;
    private String xiaoxi_status = "0";
    private SharedHelper sh;
    private String status;
    private String serachvalues;
    private Bundle bundle;
    private ImageView pgbar_imageview;
    private AnimationDrawable ad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_xiaoxi);
        mcontext = InformationActivity.this;
        Intent it = getIntent();
        bundle = it.getExtras();
        status = bundle.getString("status");
        context = getApplicationContext();
        fManager = getSupportFragmentManager();
        //获取进度条
        pgbar_imageview = findViewById(R.id.xiaoxi_loading);
        ad = (AnimationDrawable) pgbar_imageview.getDrawable();
        pgbar_imageview.postDelayed(new Runnable() {
            @Override
            public void run() {
                ad.start();
            }
        },100);
        pgbar_imageview.setVisibility(View.INVISIBLE);
        RadioGroup rg = findViewById(R.id.rg_tab_bar);
        rg.setOnCheckedChangeListener(this);
        //获取第一个单选按钮
        if("消息".equals(status)) {
            rb = findViewById(R.id.rb_xiaoxi);
            rb.setChecked(true);
        }
        if("搜索".equals(status)){
            serachvalues = bundle.getString("serachvalues");
            rb = findViewById(R.id.rb_asset);
            rb.setChecked(true);
        }
        if("盘点".equals(status)){
            rb = findViewById(R.id.rb_pandian);
            rb.setChecked(true);
        }
        //父layout添加点击事件
        findViewById(R.id.serach_layout).setOnClickListener(new serachlayout());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction ft = fManager.beginTransaction();
        hideAllFragment(ft);
        switch (checkedId) {
            case R.id.rb_xiaoxi:
                pgbar_imageview.setVisibility(View.VISIBLE);
                sh = new SharedHelper(context);
                final Map<String, String> map = sh.getData();
                final String name = map.get("name");
                final String url = map.get("ljtext");
                final AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        pandInformation(name, url);
                        return null;
                    }
                };
                task.execute();
                break;
            case R.id.rb_asset:
                pgbar_imageview.setVisibility(View.INVISIBLE);
                if(fgasset==null) {
                    fgasset = new FragmentAsset(serachvalues);
                    ft.add(R.id.ly_content, fgasset);
                }else{
                    ft.show(fgasset);
                }
                break;
            case R.id.rb_pandian:
                pgbar_imageview.setVisibility(View.INVISIBLE);
                fgpandian = new FragmentPanDian();
                ft.add(R.id.ly_content, fgpandian);
                break;
        }
        ft.commit();
    }

    //隐藏所有fagement
    public void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fgxiaoxi != null) fragmentTransaction.hide(fgxiaoxi);
        if (fgasset != null) fragmentTransaction.hide(fgasset);
        if (fgpandian != null) fragmentTransaction.hide(fgpandian);
    }
    //父layout添加点击事件
    class serachlayout implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
    public void pandInformation(String name, String url) {
        //定义获取手机信息的SoapAction与命名空间,作为常量
        String namespace = "webservices.blog.weaver.com.cn/";
        //盘点相关信息
        String pand_methodname = "findpdBypdr";
        String pand_soapAction = "webservices.blog.weaver.com.cn/findpdBypdr";
        String pand_url = url + "//services/GetInventoryDataService";
        //指定webservice的命名空间和调用方法
        SoapObject soapObject = new SoapObject(namespace, pand_methodname);
        //设置需要传入的参数
        soapObject.addProperty("username", name);
        //生成调用webservice方法的soap请求信息，并指定soap版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        //设置是否调用的是dotnet开发的webservice
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        //获取网络链接
        HttpTransportSE transport = new HttpTransportSE(pand_url);
        try {
            //调用webservice
            transport.call(namespace, envelope);
            //获取返回的数据
            SoapObject object = (SoapObject) envelope.bodyIn;
            //获取返回结果
            xiaoxi_result = object.getProperty(0).toString();
            xiaoxi_status = "1";
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(0x001);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            FragmentTransaction ft1= fManager.beginTransaction();
            switch (message.what) {
                case 0x001:
                    if (Integer.parseInt(xiaoxi_status) == 1) {
                        fgxiaoxi = new FragementXiaoXi(xiaoxi_result);
                        ft1.add(R.id.ly_content, fgxiaoxi);
                        ft1.commit();
                        pgbar_imageview.setVisibility(View.INVISIBLE);
                    }
                    if (Integer.parseInt(xiaoxi_status) == 0) {
                        pgbar_imageview.setVisibility(View.INVISIBLE);
                        TosatShowUtil.showShort(mcontext,"请求失败！");
                    }
            }
        }
    };
}
