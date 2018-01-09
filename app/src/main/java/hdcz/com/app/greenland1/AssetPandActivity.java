package hdcz.com.app.greenland1;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.bean.CheckInformationBean;
import hdcz.com.app.greenland1.dao.AssetInformationDao;
import hdcz.com.app.greenland1.util.GetDbUtil;

/**
 * Created by guyuqiang on 2018/1/5.11:09
 */

public class AssetPandActivity extends AppCompatActivity {
    private CheckInformationBean checkInformationBean;
    private AssetInformationBean assetInformationBean;
    private Context mcontext;
    private TextView assetpand_head;
    private Button assetpand_back;
    private TextView assetpand_fqr;
    private TextView assetpand_fqsj;
    private TextView assetpand_jdt;
    private TextView assetpand_pdsj;
    private TextView assetpand_assetname;
    private TextView assetpand_assetcode;
    private TextView assetpand_assetuser;
    private TextView assetpand_assettype;
    private TextView assetpand_assetspex;
    private TextView assetpand_assetunit;
    private TextView assetpand_assetbdpart;
    private TextView assetpand_savelocation;
    private TextView assetpand_uselocation;
    private TextView assetpand_pandstatus;
    private Button assetpand_sure;
    private String checkjson;
    private String assetjson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assetpand);
        mcontext = getApplicationContext();
        //获取intent传过来的数据
        Intent it = getIntent();
        Bundle bd = it.getExtras();
        checkjson = bd.getString("checkInformationBean");
        assetjson = bd.getString("assetInformationBean");
        //json数据转换成相应对象
        checkInformationBean = new Gson().fromJson(checkjson, CheckInformationBean.class);
        assetInformationBean = new Gson().fromJson(assetjson, AssetInformationBean.class);
        //加载控件
        bindView();
        //给控件赋值
        assetpand_head.setText("盘点编号：" + checkInformationBean.getCode() + "[" + checkInformationBean.getPdlx() + "]");
        assetpand_fqr.setText(checkInformationBean.getFqr());
        assetpand_fqsj.setText(checkInformationBean.getFqsj());
        assetpand_pdsj.setText(checkInformationBean.getPdsj());
        assetpand_assetname.setText(assetInformationBean.getAsset_name());
        assetpand_assetcode.setText(assetInformationBean.getAsset_code());
        assetpand_assetuser.setText(assetInformationBean.getAsset_usename());
        assetpand_assettype.setText(assetInformationBean.getAsset_type());
        assetpand_assetspex.setText(assetInformationBean.getAsset_spex());
        assetpand_assetunit.setText(assetInformationBean.getAsset_unit());
        assetpand_assetbdpart.setText(assetInformationBean.getAsset_belongdepart());
        assetpand_savelocation.setText(assetInformationBean.getAsset_savelocation());
        assetpand_uselocation.setText(assetInformationBean.getAsset_uselocation());
        //确定按钮添加事件
        assetpand_sure.setOnClickListener(new AssetPandSureButton());
        //添加返回事件
        assetpand_back.setOnClickListener(new AssetPandButtonBack());
    }

    public void bindView() {
        assetpand_head = findViewById(R.id.assetpand_head);
        assetpand_back = findViewById(R.id.assetpand_bakc);
        assetpand_fqr = findViewById(R.id.assetpand_text_fqr);
        assetpand_fqsj = findViewById(R.id.assetpand_text_fqsj);
        assetpand_jdt = findViewById(R.id.assetpand_text_jdt);
        assetpand_pdsj = findViewById(R.id.assetpand_text_pdsj);
        assetpand_assetname = findViewById(R.id.assetpand_text_assetname);
        assetpand_assetcode = findViewById(R.id.assetpand_text_assetcode);
        assetpand_assetuser = findViewById(R.id.assetpand_text_usename);
        assetpand_assettype = findViewById(R.id.assetpand_text_assettype);
        assetpand_assetspex = findViewById(R.id.assetpand_text_assetspex);
        assetpand_assetunit = findViewById(R.id.assetpand_text_unit);
        assetpand_assetbdpart = findViewById(R.id.assetpand_text_belongdepartment);
        assetpand_savelocation = findViewById(R.id.assetpand_text_savelocation);
        assetpand_uselocation = findViewById(R.id.assetpand_text_uselocation);
        assetpand_pandstatus = findViewById(R.id.assetpand_pandstatus_hand);
        assetpand_sure = findViewById(R.id.assetpand_surebutton);
    }

    //确定按钮添加事件
    class AssetPandSureButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //获取盘点状态，资产编码，图片以及盘点编码值
            String pandstatus = (String) assetpand_pandstatus.getText();
            //获取链接
            SQLiteDatabase db = GetDbUtil.getDb(mcontext, 2, "my.db");
            AssetInformationDao assetInformationDao = new AssetInformationDao();
            assetInformationDao.EditStatus(checkInformationBean.getCode(), assetInformationBean.getAsset_code(), "1", pandstatus, db);
//            Toast.makeText(mcontext,"点击确定按钮",Toast.LENGTH_SHORT).show();
//            finish();
            Intent it = new Intent(AssetPandActivity.this, PdInformationActivity.class);
            it.putExtra("pdinformation", checkjson);
            startActivity(it);
        }
    }
    //添加返回事件
    class AssetPandButtonBack implements View.OnClickListener{
        @Override
        public  void onClick(View v){
            Intent it = new Intent(AssetPandActivity.this,PdInformationActivity.class );
            startActivity(it);
        }
    }
}
