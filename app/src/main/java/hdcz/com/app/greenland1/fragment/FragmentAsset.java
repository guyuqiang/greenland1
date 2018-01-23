package hdcz.com.app.greenland1.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.dao.HistoryAssetInfoDao;
import hdcz.com.app.greenland1.json.AssetJson;
import hdcz.com.app.greenland1.sharedpreferences.SharedHelper;
import hdcz.com.app.greenland1.util.GetDbUtil;

/**
 * Created by guyuqiang on 2017/12/28.12:32
 */

@SuppressLint("ValidFragment")
public class FragmentAsset extends Fragment {
    private Button shdownbutton;
    private Button shscanbutton;
    private EditText shedittext;
    private Button shserahcbutton;
    private Button shdeletebutton;
    private String backresult;
    private String status = "0";
    private SharedHelper sh;
    private String name;
    private String ljtext;
    private SQLiteDatabase db;
    private String serachvalues = "0";
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragementSerachAsset fragementSerachAsset;
    private Integer num;
    private DialogInterface.OnClickListener dialog_delete;
    private ImageView serachasset_imageview;
    private AnimationDrawable ad;

    public FragmentAsset(String valuse) {
        this.serachvalues = valuse;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.asset_content, container, false);
        db = GetDbUtil.getDb(getContext(), 4, "my.db");
        //获取进度条imageview
       serachasset_imageview = view.findViewById(R.id.serachasset_loading);
       ad = (AnimationDrawable) serachasset_imageview.getDrawable();
       serachasset_imageview.postDelayed(new Runnable() {
           @Override
           public void run() {
               ad.start();
           }
       },100);
       serachasset_imageview.setVisibility(View.INVISIBLE);
        //获取数量
        HistoryAssetInfoDao historyAssetInfoDao = new HistoryAssetInfoDao();
        num = historyAssetInfoDao.getDataCount(db);
        fm = getFragmentManager();
        sh = new SharedHelper(getActivity());
        Map<String, String> map = sh.getData();
        name = map.get("name");
        ljtext = map.get("ljtext");
        shdownbutton = view.findViewById(R.id.serachasset_download);
        shscanbutton = view.findViewById(R.id.serachasset_scanbutton);
        shedittext = view.findViewById(R.id.serachasset_edittext);
        shserahcbutton = view.findViewById(R.id.serachasset_searchbutton);
        shdeletebutton = view.findViewById(R.id.serachasset_delete);
        ft = fm.beginTransaction();
        if (fragementSerachAsset != null) {
            ft.hide(fragementSerachAsset);
        }
        fragementSerachAsset = new FragementSerachAsset(serachvalues);
        ft.add(R.id.serachasset_ly_content, fragementSerachAsset);
        ft.commit();
        //下载按钮添加事件
        shdownbutton.setOnClickListener(new downbutton());
        //扫描按钮添加事件
        shscanbutton.setOnClickListener(new scanbutton());
        //搜索按钮添加事件
        shserahcbutton.setOnClickListener(new serachebutton());
        //删除按钮添加事件
        shdeletebutton.setOnClickListener(new deleteButton());
        return view;
    }

    //删除按钮添加事件
    class deleteButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            dialog_delete = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    HistoryAssetInfoDao historyAssetInfoDao = new HistoryAssetInfoDao();
                    historyAssetInfoDao.deleteData(db);
                    ft = fm.beginTransaction();
                    if (fragementSerachAsset != null) {
                        ft.hide(fragementSerachAsset);
                    }
                    fragementSerachAsset = new FragementSerachAsset(serachvalues);
                    ft.add(R.id.serachasset_ly_content, fragementSerachAsset);
                    ft.commit();
                }
            };
            showDialog(v);
        }
    }

    //下载按钮添加事件
    class downbutton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //获取数量
            HistoryAssetInfoDao historyAssetInfoDao = new HistoryAssetInfoDao();
            num = historyAssetInfoDao.getDataCount(db);
            if (num > 0) {
                Toast.makeText(getContext(), "数据已下载！", Toast.LENGTH_SHORT).show();
            } else {
                serachasset_imageview.setVisibility(View.VISIBLE);
                final AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        assetDown(name, ljtext);
                        return null;
                    }
                };
                task.execute();
            }
        }
    }

    //扫描按钮添加事件
    public class scanbutton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //获取数量
            HistoryAssetInfoDao historyAssetInfoDao = new HistoryAssetInfoDao();
            num = historyAssetInfoDao.getDataCount(db);
            if (num > 0) {
                Intent it = new Intent("android.intent.action.CAPTUREACTIVITY");
                Bundle bundle = new Bundle();
                bundle.putString("status", "serachescan");
                it.putExtras(bundle);
                startActivity(it);
            } else {
                Toast.makeText(getContext(), "请先下载数据！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //搜索按钮添加事件
    class serachebutton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //获取数量
            HistoryAssetInfoDao historyAssetInfoDao = new HistoryAssetInfoDao();
            num = historyAssetInfoDao.getDataCount(db);
            if (num > 0) {
                //加载fagement
                ft = fm.beginTransaction();
                if (fragementSerachAsset != null) {
                    ft.hide(fragementSerachAsset);
                }
                serachvalues = shedittext.getText().toString();
                fragementSerachAsset = new FragementSerachAsset(serachvalues);
                ft.add(R.id.serachasset_ly_content, fragementSerachAsset);
                ft.commit();
            } else {
                Toast.makeText(getContext(), "请先下载数据！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //弹出框
    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确定删除数据！");
        builder.setPositiveButton("返回", null);
        builder.setNegativeButton("确定", dialog_delete);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //从服务器获取数据
    public void assetDown(String name, String url) {
        //定义获取手机信息的SoapAction与命名空间,作为常量
        String namespace = "webservices.blog.weaver.com.cn/";
        //盘点相关信息
        String pand_methodname = "getCptcapitalByloginid";
        String pand_url = url + "//services/GetInventoryDataService";
        //指定webservice的命名空间和调用方法
        SoapObject soapObject = new SoapObject(namespace, pand_methodname);
        //设置需要传入的参数
        soapObject.addProperty("name", name);
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
            backresult = object.getProperty(0).toString();
            status = "1";
        } catch (HttpResponseException e) {
            e.printStackTrace();
            status = "0";
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            status = "0";
        } catch (IOException e) {
            e.printStackTrace();
            status = "0";
        }
        handler.sendEmptyMessage(0x001);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0x001:
                    if (Integer.parseInt(status) == 1) {
                        //转换数据
                        AssetJson assetJson = new AssetJson();
                        List<AssetInformationBean> data = assetJson.getAssetByJson(backresult, null, null);
                        //数据存入数据库中
                        HistoryAssetInfoDao historyAssetInfoDao = new HistoryAssetInfoDao();
                        for (int i = 0; i < data.size(); i++) {
                            historyAssetInfoDao.saveAsset(data.get(i), db);
                        }
                        ft = fm.beginTransaction();
                        if (fragementSerachAsset != null) {
                            ft.hide(fragementSerachAsset);
                        }
                        fragementSerachAsset = new FragementSerachAsset(serachvalues);
                        ft.add(R.id.serachasset_ly_content, fragementSerachAsset);
                        ft.commit();
                        serachasset_imageview.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "数据下载完成！", Toast.LENGTH_SHORT).show();
                    } else {
                        serachasset_imageview.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "获取数据失败！！", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
}