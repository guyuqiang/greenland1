package hdcz.com.app.greenland1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.CamcorderProfile;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.client.android.CaptureActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.bean.CheckInformationBean;
import hdcz.com.app.greenland1.dao.AssetInformationDao;
import hdcz.com.app.greenland1.dao.CheckInformationDao;
import hdcz.com.app.greenland1.fragment.FragementNotCheck;
import hdcz.com.app.greenland1.fragment.FragementYetCheck;
import hdcz.com.app.greenland1.json.AssetJson;
import hdcz.com.app.greenland1.sharedpreferences.SharedHelper;
import hdcz.com.app.greenland1.util.GetDbUtil;
import hdcz.com.app.greenland1.util.ReturnMessage;
import hdcz.com.app.greenland1.util.TosatShowUtil;

/**
 * Created by guyuqiang on 2018/1/3.10:05
 */

public class PdInformationActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    private TextView texthead;
    private Button back;
    private Button refresh;
    private Button download;
    private Button pand;
    private Button endpand;
    private Button up;
    private TextView fqr;
    private TextView fqsj;
    private TextView jdt;
    private TextView pdsj;
    private RadioButton yipand;
    private RadioButton weipand;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Context mecontext;
    private FragementNotCheck fragementNotCheck;
    private FragementYetCheck fragementYetCheck;
    private RadioButton rb;
    private RadioButton rb1;
    private RadioGroup rg;
    private ReturnMessage message = new ReturnMessage();
    private CheckInformationBean checkbean;
    private String wpandnum;
    private String yipandnum;
    private DialogInterface.OnClickListener dialog_delete;
    private String assetdown_result;
    private String assetdown_status;
    private SharedHelper sh;
    private String user;
    private String ljtext;
    private String upresult="0";
    private ImageView information_imageview;
    private AnimationDrawable ad;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        bindview();
        Intent it = getIntent();
        String pandinforjson = it.getStringExtra("pdinformation");
        checkbean = new Gson().fromJson(pandinforjson, CheckInformationBean.class);
        texthead.setText("盘点编号：" + checkbean.getCode() + "[" + checkbean.getPdlx() + "]");
        fqr.setText(checkbean.getFqr());
        fqsj.setText(checkbean.getFqsj());
        pdsj.setText(checkbean.getPdsj());
        //返回按钮添加事件
        back.setOnClickListener(new ButtonBack());
        fm = getSupportFragmentManager();
        mecontext = getApplicationContext();
        rg = findViewById(R.id.information_rg_center);
        rg.setOnCheckedChangeListener(this);
        //获取shardhelper文件
        sh = new SharedHelper(mecontext);
        Map<String, String> map = sh.getData();
        user = map.get("name");
        ljtext = map.get("ljtext");
        //获取第一个单选按钮
        rb = findViewById(R.id.information_rb_yipandian);
        rb.setChecked(true);
        //获取未盘点数量
        AssetInformationDao assetInformationDao = new AssetInformationDao();
        SQLiteDatabase db = GetDbUtil.getDb(mecontext, 2, "my.db");
        wpandnum = assetInformationDao.getDataCount(checkbean.getCode(), "0", db);
        weipand.setText("未盘点：" + wpandnum);
        //获取已盘点数量
        yipandnum = assetInformationDao.getDataCount(checkbean.getCode(), "1", db);
        yipand.setText("已盘点：" + yipandnum);
        //获取总数量
        String totalnum = assetInformationDao.getDataCount(checkbean.getCode(),db)+"";
        jdt.setText(yipandnum+"/"+totalnum);
        //盘点按钮添加点击事件
        pand.setOnClickListener(new pandButton());
        //刷新按钮添加点击事件
        //refresh.setOnClickListener(new refreshButton());
        //下载按钮添加事件
        download.setOnClickListener(new downloadButton());
        if (Integer.parseInt(yipandnum) > 0 || Integer.parseInt(wpandnum) > 0) {
            download.setText("删除资料");
        } else {
            download.setText("下载资料");
        }
        //上传资料按钮添加事件
        up.setOnClickListener(new upButton());
    }

    public void bindview() {
        texthead = findViewById(R.id.information_head);
        back = findViewById(R.id.information_bakc);
        //refresh = findViewById(R.id.information_refresh);
        download = findViewById(R.id.information_button_download);
        pand = findViewById(R.id.information_button_scan);
        //endpand = findViewById(R.id.information_button_endcheck);
        up = findViewById(R.id.information_button_up);
        fqr = findViewById(R.id.information_text_fqr);
        fqsj = findViewById(R.id.information_text_fqsj);
        jdt = findViewById(R.id.information_text_jdt);
        pdsj = findViewById(R.id.information_text_pdsj);
        yipand = findViewById(R.id.information_rb_yipandian);
        weipand = findViewById(R.id.information_rg_weipandian);
        information_imageview = findViewById(R.id.information_loading);
        ad = (AnimationDrawable) information_imageview.getDrawable();
        information_imageview.postDelayed(new Runnable() {
            @Override
            public void run() {
                ad.start();
            }
        },100);
        information_imageview.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ft = fm.beginTransaction();
        hideAllFragment(ft);
        switch (checkedId) {
            case R.id.information_rg_weipandian:
                //if(fragementNotCheck==null){
                AssetInformationDao assetInformationDao = new AssetInformationDao();
                SQLiteDatabase db = GetDbUtil.getDb(mecontext, 2, "my.db");
                wpandnum = assetInformationDao.getDataCount(checkbean.getCode(), "0", db);
                weipand.setText("未盘点：" + wpandnum);
                yipandnum = assetInformationDao.getDataCount(checkbean.getCode(), "1", db);
                yipand.setText("已盘点：" + yipandnum);
                fragementNotCheck = new FragementNotCheck(checkbean);
                ft.add(R.id.information_listview_content, fragementNotCheck);
                //}else{
                // ft.show(fragementNotCheck);
                //}
                break;
            case R.id.information_rb_yipandian:
                AssetInformationDao assetInformationDao1 = new AssetInformationDao();
                SQLiteDatabase db1 = GetDbUtil.getDb(mecontext, 2, "my.db");
                yipandnum = assetInformationDao1.getDataCount(checkbean.getCode(), "1", db1);
                yipand.setText("已盘点：" + yipandnum);
                wpandnum = assetInformationDao1.getDataCount(checkbean.getCode(), "0", db1);
                weipand.setText("未盘点：" + wpandnum);
                fragementYetCheck = new FragementYetCheck(checkbean);
                ft.add(R.id.information_listview_content, fragementYetCheck);
                break;
        }
        ft.commit();
    }

    class ButtonBack implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent it = new Intent(PdInformationActivity.this, InformationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("status","消息");
            it.putExtras(bundle);
            startActivity(it);
        }
    }

    //隐藏所有fagement
    public void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fragementNotCheck != null) fragmentTransaction.hide(fragementNotCheck);
        if (fragementYetCheck != null) fragmentTransaction.hide(fragementYetCheck);
    }

    //下载按钮添加点击事件
    class downloadButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String tag = (String) download.getText();
            if ("下载资料".equals(tag)) {
                information_imageview.setVisibility(View.VISIBLE);
                //判断数据是否已下载
                SQLiteDatabase db = GetDbUtil.getDb(mecontext, 2, "my.db");
                AssetInformationDao assetInformationDao = new AssetInformationDao();
                int num1 = assetInformationDao.getDataCount(checkbean.getCode(), db);
                if (num1 > 0) {
                    information_imageview.setVisibility(View.INVISIBLE);
                    TosatShowUtil.showShort(mecontext,"资料已下载！");
                } else {
                    sh = new SharedHelper(mecontext);
                    final Map<String, String> map = sh.getData();
                    final AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... voids) {
                            assetDown(checkbean.getCode(), map.get("ljtext"));
                            return null;
                        }
                    };
                    task.execute();
                }
            } else {
                //删除资料
                dialog_delete = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AssetInformationDao assetInformationDao = new AssetInformationDao();
                        SQLiteDatabase db = GetDbUtil.getDb(mecontext, 2, "my.db");
                        assetInformationDao.DeleteAsset(checkbean.getCode(), db);
                        download.setText("下载资料");
                        TosatShowUtil.showShort(mecontext,"删除完成！");
                        refreshView();
                    }
                };
                showDialog(v);
            }
        }
    }

    //盘点按钮添加事件
    class pandButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AssetInformationDao assetInformationDao = new AssetInformationDao();
            SQLiteDatabase db = GetDbUtil.getDb(mecontext, 2, "my.db");
            wpandnum = assetInformationDao.getDataCount(checkbean.getCode(), "0", db);
            if (Integer.parseInt(wpandnum) > 0) {
                Intent it = new Intent(PdInformationActivity.this, CaptureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("code", checkbean.getCode());
                bundle.putString("fqr", checkbean.getFqr());
                bundle.putString("fqsj", checkbean.getFqsj());
                bundle.putString("pdlx", checkbean.getPdlx());
                bundle.putString("pdsj", checkbean.getPdsj());
                bundle.putString("jdt",jdt.getText().toString());
                bundle.putString("status", "pand1");
                it.putExtras(bundle);
                startActivity(it);
            } else {
                pand.setTextColor(Color.parseColor("#8b8787"));
                TosatShowUtil.showShort(mecontext,"资产已盘点完！");
            }
        }
    }

    //上传资料按钮添加事件
    class upButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AssetInformationDao assetInformationDao = new AssetInformationDao();
            SQLiteDatabase db = GetDbUtil.getDb(mecontext, 2, "my.db");
            wpandnum = assetInformationDao.getDataCount(checkbean.getCode(), "0", db);
            if (Integer.parseInt(wpandnum) > 0) {
                TosatShowUtil.showShort(mecontext,"资产未盘点完！");
            } else {
                information_imageview.setVisibility(View.VISIBLE);
                //获取数据，盘点信息存入历史记录表中
                CheckInformationDao checkInformationDao = new CheckInformationDao();
                checkbean.setPdr(user);
                //获取当前时间
                Date data = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentdata = sdf.format(data);
                checkbean.setDeffind1(currentdata);
                checkInformationDao.saveCheckInformation(checkbean, db);
                //上传数据
                AssetInformationDao assetInformationDao1 = new AssetInformationDao();
                 db = GetDbUtil.getDb(mecontext,5,"my.db");
                 final Map<String,String> map = sh.getData();
                final String json = assetInformationDao1.upAsset(checkbean.getCode(),db);
                final AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        assetUp(json,map.get("ljtext"));
                        return null;
                    }
                };
                task.execute();
            }
        }
    }

    //刷新按钮添加事件
    class refreshButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            refreshView();
        }
    }

    public void refreshView() {
        AssetInformationDao assetInformationDao = new AssetInformationDao();
        SQLiteDatabase db = GetDbUtil.getDb(mecontext, 2, "my.db");
        rb = findViewById(R.id.information_rb_yipandian);
        rb1 = findViewById(R.id.information_rg_weipandian);
        //获取已盘点数量
        yipandnum = assetInformationDao.getDataCount(checkbean.getCode(), "1", db);
        yipand.setText("已盘点：" + yipandnum);
        rb.setChecked(true);
        wpandnum = assetInformationDao.getDataCount(checkbean.getCode(), "0", db);
        weipand.setText("未盘点：" + wpandnum);
        rb1.setChecked(true);
        //获取总数量
        String tatolnum = assetInformationDao.getDataCount(checkbean.getCode(),db)+"";
        jdt.setText(yipandnum+"/"+tatolnum);
    }

    //弹出框
    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定删除数据！");
        builder.setPositiveButton("返回", null);
        builder.setNegativeButton("确定", dialog_delete);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //从服务器获取数据
    public void assetDown(String pandcode, String url) {
        //定义获取手机信息的SoapAction与命名空间,作为常量
        String namespace = "webservices.blog.weaver.com.cn/";
        //盘点相关信息
        String pand_methodname = "findCptcapitalBypdmark";
        String pand_url = url + "//services/GetInventoryDataService";
        //指定webservice的命名空间和调用方法
        SoapObject soapObject = new SoapObject(namespace, pand_methodname);
        //设置需要传入的参数
        soapObject.addProperty("pandcode", pandcode);
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
            assetdown_result = object.getProperty(0).toString();
            assetdown_status = "1";
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(0x001);
    }

    //数据上传到服务器
    private void assetUp(String assetJson,String url){
        //定义获取手机信息的SoapAction与命名空间,作为常量
        String namespace = "webservices.blog.weaver.com.cn/";
        //盘点相关信息
        String pand_methodname = "backdatajson";
        String pand_url = url + "//services/GetInventoryDataService";
        //指定webservice的命名空间和调用方法
        SoapObject soapObject = new SoapObject(namespace, pand_methodname);
        //设置需要传入的参数
        soapObject.addProperty("assetjson", assetJson);
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
            upresult = object.getProperty(0).toString();
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(0x002);
    }
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0x001:
                    if (Integer.parseInt(assetdown_status) == 1) {
                        //json数据转换
                        AssetJson assetJson1 = new AssetJson();
                        List<AssetInformationBean> assetlist = assetJson1.getAssetByJson(assetdown_result, checkbean.getCode(), "0");
                        int num = 0;
                        for (int i = 0; i < assetlist.size(); i++) {
                            //数据存入本地数据库中
                            SQLiteDatabase db = GetDbUtil.getDb(mecontext, 2, "my.db");
                            AssetInformationDao assetInformationDao = new AssetInformationDao();
                            ReturnMessage message1 = assetInformationDao.saveAssetInformation(assetlist.get(i), db);
                            num++;
                        }
                        if (assetlist.size() == num) {
                            information_imageview.setVisibility(View.INVISIBLE);
                            TosatShowUtil.showShort(mecontext,"资料下载完成！");
                            refreshView();
                            download.setText("删除资料");
                        }
                    }
                    if (Integer.parseInt(assetdown_status) == 0) {
                        information_imageview.setVisibility(View.INVISIBLE);
                        TosatShowUtil.showShort(mecontext,"请求失败！");
                    }
                    break;
                case 0x002:
                    if(Integer.parseInt(upresult)==1){
                        information_imageview.setVisibility(View.INVISIBLE);
                        TosatShowUtil.showShort(mecontext,"数据上传成功！");
                    }else {
                        information_imageview.setVisibility(View.INVISIBLE);
                        TosatShowUtil.showShort(mecontext,"请求失败！");
                    }
                   break;
            }
        }
    };
}
