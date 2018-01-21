package hdcz.com.app.greenland1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.gson.Gson;

import java.util.Map;

import hdcz.com.app.greenland1.bean.CheckInformationBean;
import hdcz.com.app.greenland1.dao.AssetInformationDao;
import hdcz.com.app.greenland1.dao.CheckInformationDao;
import hdcz.com.app.greenland1.fragment.FragementHistoryAsset;
import hdcz.com.app.greenland1.sharedpreferences.SharedHelper;
import hdcz.com.app.greenland1.util.GetDbUtil;

/**
 * Created by guyuqiang on 2018/1/14.14:01
 */

public class HistoryAssetInfoActivity extends FragmentActivity {
    private FragementHistoryAsset fragementHistoryAsset;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Button backbutton;
    private Button deletbutton;
    private CheckInformationBean checkInformationBean;
    private String checkjson;
    private TextView fqr;
    private TextView head;
    private TextView pandlx;
    private TextView wcsj;
    private TextView pdsj;
    private DialogInterface.OnClickListener dialog_delete;
    private Context mcontext;
    private SharedHelper sh;
    private String name;
    private String ljtext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historyassetinformation);
        bindview();
        mcontext = getApplicationContext();
        sh = new SharedHelper(mcontext);
        Map<String,String> map = sh.getData();
        name = map.get("name");
        ljtext = map.get("ljtext");
        Intent it = getIntent();
        checkjson = it.getStringExtra("pdinformation");
        checkInformationBean = new Gson().fromJson(checkjson, CheckInformationBean.class);
        head.setText("盘点编号:"+checkInformationBean.getCode());
        fqr.setText(checkInformationBean.getFqr());
        wcsj.setText(checkInformationBean.getDeffind1());
        pandlx.setText(checkInformationBean.getPdlx());
        pdsj.setText(checkInformationBean.getPdsj());
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        fragementHistoryAsset = new FragementHistoryAsset(checkInformationBean);
        ft.add(R.id.historaseet_listview_content, fragementHistoryAsset);
        ft.commit();
        //返回按钮添加事件
        backbutton.setOnClickListener(new backButton());
        //删除按钮添加事件
        deletbutton.setOnClickListener(new deleteButton());
    }

    public void bindview() {
        backbutton = findViewById(R.id.historyasset_bakc);
        deletbutton = findViewById(R.id.historyasset_delete);
        head = findViewById(R.id.historyasset_head);
        fqr = findViewById(R.id.historyasset_text_fqr);
        wcsj = findViewById(R.id.historyasset_text_wcsj);
        pandlx = findViewById(R.id.historyasset_text_lx);
        pdsj = findViewById(R.id.historyasset_text_pdsj);
    }
    //返回按钮添加事件
    class backButton implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            finish();
        }
    }
    //删除按钮添加事件
    class deleteButton implements View.OnClickListener{
        @Override
        public void onClick(View v) {
          dialog_delete = new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  SQLiteDatabase db = GetDbUtil.getDb(mcontext,4,"my.db");
                  AssetInformationDao assetInformationDao = new AssetInformationDao();
                  CheckInformationDao checkInformationDao = new CheckInformationDao();
                  //删除资产数据
                  assetInformationDao.DeleteAsset(checkInformationBean.getCode(),db);
                  checkInformationDao.deleteCheckByCodeAndUser(checkInformationBean.getCode(),name,db);
                 Intent it = new Intent(HistoryAssetInfoActivity.this,InformationActivity.class);
                 Bundle bundle = new Bundle();
                 bundle.putString("status","盘点");
                 it.putExtras(bundle);
                 startActivity(it);
              }
          };
          showDialog(v);
        }
    }
    //弹出框
    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定删除数据(无法恢复)！");
        builder.setPositiveButton("返回", null);
        builder.setNegativeButton("确定", dialog_delete);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
