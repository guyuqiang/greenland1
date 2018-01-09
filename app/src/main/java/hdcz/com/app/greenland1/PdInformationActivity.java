package hdcz.com.app.greenland1;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.history.HistoryItem;
import com.google.zxing.client.android.result.ResultHandler;

import hdcz.com.app.greenland1.bean.CheckInformationBean;
import hdcz.com.app.greenland1.dao.AssetInformationDao;
import hdcz.com.app.greenland1.fragment.FragementNotCheck;
import hdcz.com.app.greenland1.fragment.FragementYetCheck;
import hdcz.com.app.greenland1.util.GetDbUtil;
import hdcz.com.app.greenland1.util.ReturnMessage;
import hdcz.com.app.greenland1.webservice.WebService;

/**
 * Created by guyuqiang on 2018/1/3.10:05
 */

public class PdInformationActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    private TextView texthead;
    private Button back;
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
    private RadioGroup rg;
    private ReturnMessage message = new ReturnMessage();
    private CheckInformationBean checkbean;
    private String wpandnum;
    private String yipandnum;
    private final int requestcode = 111111;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        bindview();
        Intent it = getIntent();
        String pandinforjson = it.getStringExtra("pdinformation");
        checkbean = new Gson().fromJson(pandinforjson,CheckInformationBean.class);
        texthead.setText("盘点编号："+checkbean.getCode()+"["+checkbean.getPdlx()+"]");
        fqr.setText(checkbean.getFqr());
        fqsj.setText(checkbean.getFqsj());
        pdsj.setText(checkbean.getPdsj());
        back.setOnClickListener(new ButtonBack());
        fm = getSupportFragmentManager();
        mecontext = getApplicationContext();
        rg = findViewById(R.id.information_rg_center);
        rg.setOnCheckedChangeListener(this);
       //获取第一个单选按钮
        rb = findViewById(R.id.information_rb_yipandian);
        rb.setChecked(true);
        //下载按钮添加事件
        download.setOnClickListener(new downloadButton());
        //获取未盘点数量
        AssetInformationDao assetInformationDao = new AssetInformationDao();
        SQLiteDatabase db = GetDbUtil.getDb(mecontext,2,"my.db");
        wpandnum = assetInformationDao.getDataCount(checkbean.getCode(),"0",db);
        weipand.setText("未盘点："+wpandnum);
        //获取已盘点数量
        yipandnum = assetInformationDao.getDataCount(checkbean.getCode(),"1",db);
        yipand.setText("已盘点："+yipandnum);
        //盘点按钮添加点击事件
        pand.setOnClickListener(new pandButton());
    }
    public void bindview(){
         texthead = findViewById(R.id.information_head);
         back = findViewById(R.id.information_bakc);
         download = findViewById(R.id.information_button_download);
         pand = findViewById(R.id.information_button_scan);
         endpand = findViewById(R.id.information_button_endcheck);
         up = findViewById(R.id.information_button_up);
         fqr = findViewById(R.id.information_text_fqr);
         fqsj = findViewById(R.id.information_text_fqsj);
         jdt = findViewById(R.id.information_text_jdt);
         pdsj = findViewById(R.id.information_text_pdsj);
         yipand = findViewById(R.id.information_rb_yipandian);
         weipand = findViewById(R.id.information_rg_weipandian);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
             ft = fm.beginTransaction();
             hideAllFragment(ft);
        switch (checkedId){
            case R.id.information_rg_weipandian:
                if(fragementNotCheck==null){
                    fragementNotCheck =new FragementNotCheck(checkbean);
                    ft.add(R.id.information_listview_content,fragementNotCheck);
                }else{
                    ft.show(fragementNotCheck);
                }
                break;
            case R.id.information_rb_yipandian:
                if(fragementYetCheck==null){
                    fragementYetCheck = new FragementYetCheck(checkbean.getCode());
                    ft.add(R.id.information_listview_content,fragementYetCheck);
                }else {
                    ft.show(fragementYetCheck);
                }
                break;
        }
        ft.commit();
    }

    class ButtonBack implements View.OnClickListener{
        @Override
        public  void onClick(View v){
            Intent it = new Intent(PdInformationActivity.this,InformationActivity.class );
            startActivity(it);
        }
    }
    //隐藏所有fagement
    public void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fragementNotCheck !=null)fragmentTransaction.hide(fragementNotCheck);
        if(fragementYetCheck != null)fragmentTransaction.hide(fragementYetCheck);
    }
    //下载按钮添加点击事件
    class  downloadButton implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            WebService webService = new WebService();
             message = webService.getAssetByWebservice(mecontext,checkbean.getCode());
             if(message.getStatus()==0){
                 Toast.makeText(mecontext,message.getMessage(),Toast.LENGTH_SHORT).show();
             }else{
                 Toast.makeText(mecontext,"开始下载资料",Toast.LENGTH_SHORT).show();
                 Toast.makeText(mecontext,message.getMessage(),Toast.LENGTH_SHORT).show();
                 weipand.setText("未盘点："+message.getStatus()+"");
             }
        }
    }
    class  pandButton implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent it = new Intent(PdInformationActivity.this, CaptureActivity.class);
           startActivity(it);
            //startActivityForResult(it,requestcode);
        }
    }
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if (resultCode == RESULT_OK && requestCode == requestCode ) {
//            intent.getDataString();
//            Toast.makeText(mecontext,"dfdfdfdf",Toast.LENGTH_SHORT).show();
//            }
//        }
}
