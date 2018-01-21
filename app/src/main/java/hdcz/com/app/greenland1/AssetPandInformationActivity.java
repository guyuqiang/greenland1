package hdcz.com.app.greenland1;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.bean.CheckInformationBean;
import hdcz.com.app.greenland1.dao.AssetInformationDao;
import hdcz.com.app.greenland1.util.GetDbUtil;

/**
 * Created by guyuqiang on 2018/1/10.18:46
 */

public class AssetPandInformationActivity extends AppCompatActivity {
    private CheckInformationBean checkInformationBean;
    private AssetInformationBean assetInformationBean;
    private Context mcontext;
    private TextView assetpandinformation_head;
    private Button assetpandinformation_back;
    private TextView assetpandinformation_fqr;
    private TextView assetpandinformation_fqsj;
    private TextView assetpandinformation_jdt;
    private TextView assetpandinformation_pdsj;
    private TextView assetpandinformation_assetname;
    private TextView assetpandinformation_assetcode;
    private ImageView assetpandinformation_image;
    private TextView assetpandinformation_assetuser;
    private TextView assetpandinformation_assettype;
    private TextView assetpandinformation_assetspex;
    private TextView assetpandinformation_assetunit;
    private TextView assetpandinformation_assetbdpart;
    private TextView assetpandinformation_savelocation;
    private TextView assetpandinformation_uselocation;
    private TextView assetpandinformation_pandstatus;
    private TextView assetpandinformation_date;
    private Button assetpandinformation_sure;
    private String checkjson;
    private String assetjson;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assetpandinformation);
        mcontext = getApplicationContext();
        //加载控件
        bindView();
        //获取数据
        //获取intent传过来的数据
        Intent it = getIntent();
        Bundle bd = it.getExtras();
        checkjson = bd.getString("checkInformationBean");
        assetjson = bd.getString("assetInformationBean");
        //json数据转换成相应对象
        checkInformationBean = new Gson().fromJson(checkjson, CheckInformationBean.class);
        assetInformationBean = new Gson().fromJson(assetjson, AssetInformationBean.class);
        //给控件赋值
        assetpandinformation_head.setText("盘点编号：" + checkInformationBean.getCode() + "[" + checkInformationBean.getPdlx() + "]");
        assetpandinformation_fqr.setText(checkInformationBean.getFqr());
        assetpandinformation_fqsj.setText(checkInformationBean.getFqsj());
        assetpandinformation_pdsj.setText(checkInformationBean.getPdsj());
        //获取总数量
        SQLiteDatabase db = GetDbUtil.getDb(mcontext,5,"my.db");
        AssetInformationDao assetInformationDao = new AssetInformationDao();
        String totalnum = assetInformationDao.getDataCount(checkInformationBean.getCode(),db)+"";
        //获取已盘点数量
        String yipandnum = assetInformationDao.getDataCount(checkInformationBean.getCode(),"1",db);
        assetpandinformation_jdt.setText(yipandnum+"/"+totalnum);
        assetpandinformation_assetname.setText(assetInformationBean.getAsset_name());
        assetpandinformation_assetcode.setText(assetInformationBean.getAsset_code());
        assetpandinformation_assetuser.setText(assetInformationBean.getAsset_usename());
        assetpandinformation_assettype.setText(assetInformationBean.getAsset_type());
        assetpandinformation_assetspex.setText(assetInformationBean.getAsset_spex());
        assetpandinformation_assetunit.setText(assetInformationBean.getAsset_unit());
        assetpandinformation_assetbdpart.setText(assetInformationBean.getAsset_belongdepart());
        assetpandinformation_savelocation.setText(assetInformationBean.getAsset_savelocation());
        assetpandinformation_uselocation.setText(assetInformationBean.getAsset_uselocation());
        assetpandinformation_pandstatus.setText(assetInformationBean.getAsset_status());
        assetpandinformation_date.setText(assetInformationBean.getAsset_deffind2());
        byte [] imagedata = assetInformationBean.getAsset_pdphoto();
        if("".equals(imagedata)||"null".equals(imagedata)||imagedata==null){

        }else{
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagedata,0,imagedata.length);
            assetpandinformation_image.setImageBitmap(bitmap);
        }
        //移除按钮添加事件
        assetpandinformation_sure.setOnClickListener(new AssetPandInformationSureButton());
        //添加返回事件
        assetpandinformation_back.setOnClickListener(new AssetPandInformationButtonBack());
    }
    public void bindView(){
        assetpandinformation_head = findViewById(R.id.assetpandinformation_head);
        assetpandinformation_back = findViewById(R.id.assetpandinformation_bakc);
        assetpandinformation_fqr = findViewById(R.id.assetpandinformation_text_fqr);
        assetpandinformation_fqsj = findViewById(R.id.assetpandinformation_text_fqsj);
        assetpandinformation_jdt = findViewById(R.id.assetpandinformation_text_jdt);
        assetpandinformation_pdsj = findViewById(R.id.assetpandinformation_text_pdsj);
        assetpandinformation_assetname = findViewById(R.id.assetpandinformation_text_assetname);
        assetpandinformation_assetcode = findViewById(R.id.assetpandinformation_text_assetcode);
        assetpandinformation_image = findViewById(R.id.assetpandinformation_image);
        assetpandinformation_assetuser = findViewById(R.id.assetpandinformation_text_usename);
        assetpandinformation_assettype = findViewById(R.id.assetpandinformation_text_assettype);
        assetpandinformation_assetspex = findViewById(R.id.assetpandinformation_text_assetspex);
        assetpandinformation_assetunit = findViewById(R.id.assetpandinformation_text_unit);
        assetpandinformation_assetbdpart = findViewById(R.id.assetpandinformation_text_belongdepartment);
        assetpandinformation_savelocation = findViewById(R.id.assetpandinformation_text_savelocation);
        assetpandinformation_uselocation = findViewById(R.id.assetpandinformation_text_uselocation);
        assetpandinformation_pandstatus = findViewById(R.id.assetpandinformation_text_pandstatus);
        assetpandinformation_date = findViewById(R.id.assetpandinformation_text_date);
        assetpandinformation_sure = findViewById(R.id.assetpandinformation_surebutton);
    }
    //移除按钮添加事件
    class AssetPandInformationSureButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //获取链接
            SQLiteDatabase db = GetDbUtil.getDb(mcontext,2,"my.db");
            AssetInformationDao assetInformationDao = new AssetInformationDao();
            assetInformationDao.deletAssetFromYpand(checkInformationBean.getCode(),assetInformationBean.getAsset_code(),db);
            //finish();
            Intent it = new Intent(AssetPandInformationActivity.this,PdInformationActivity.class);
            it.putExtra("pdinformation",checkjson);
            startActivity(it);
        }
    }
    //添加返回事件
    class AssetPandInformationButtonBack implements View.OnClickListener{
        @Override
        public  void onClick(View v){
            finish();
        }
    }
}
