package hdcz.com.app.greenland1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.client.android.bean.CheckInformationBean;
import com.google.zxing.client.android.db.DBOpenHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hdcz.com.app.greenland1.util.TosatShowUtil;

/**
 * Created by guyuqiang on 2018/1/9.14:19
 */

public class PandAssetActivity extends Activity {
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
    private Button assetpand_sure;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String pandstatus;
    private String pandcode;
    private String assetcode;
    private String assetname;
    private ImageView image_show1;
    private Bitmap bitmap;
    private String fqr;
    private String fqsj;
    private String pdlx;
    private String pdsj;
    private String jdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pandasset);
        bindView();
        Intent it1 = getIntent();
        Bundle bundle = it1.getExtras();
        pandcode = bundle.getString("code");
         fqr = bundle.getString("fqr");
         fqsj = bundle.getString("fqsj");
         pdlx = bundle.getString("pdlx");
         pdsj = bundle.getString("pdsj");
         jdt = bundle.getString("jdt");
        assetname = bundle.getString("assetname");
        assetcode = bundle.getString("assetcode");
        String asset_usename = bundle.getString("asset_usename");
        String asset_type = bundle.getString("asset_type");
        String asset_spex = bundle.getString("asset_spex");
        String asset_unit = bundle.getString("asset_unit");
        String asset_bedpart = bundle.getString("asset_bedpart");
        String asset_uselocation = bundle.getString("asset_uselocation");
        String asset_savelocation = bundle.getString("asset_savelocation");
        assetpand_head.setText("盘点编号：" + pandcode + "[" + pdlx + "]");
        assetpand_fqr.setText(fqr);
        assetpand_fqsj.setText(fqsj);
        assetpand_pdsj.setText(pdsj);
        assetpand_jdt.setText(jdt);
        assetpand_assetname.setText(assetname);
        assetpand_assetcode.setText(assetcode);
        assetpand_assetuser.setText(asset_usename);
        assetpand_assettype.setText(asset_type);
        assetpand_assetspex.setText(asset_spex);
        assetpand_assetunit.setText(asset_unit);
        assetpand_assetbdpart.setText(asset_bedpart);
        assetpand_savelocation.setText(asset_savelocation);
        assetpand_uselocation.setText(asset_uselocation);
        if ("".equals(assetcode) || "null".equals(assetcode) || assetcode == null) {
            TosatShowUtil.showShort(getApplicationContext(),"此资产不在未盘点列表中！");
        }
        //确定按钮添加事件
        assetpand_sure.setOnClickListener(new saveButton());
        //返回按钮添加事件
        assetpand_back.setOnClickListener(new backButton());
        //调取相机
        image_show1.setOnClickListener(new cameraButton());
    }

    public void bindView() {
        assetpand_head = findViewById(com.google.zxing.client.android.R.id.assetpand_head);
        assetpand_back = findViewById(com.google.zxing.client.android.R.id.assetpand_bakc);
        assetpand_fqr = findViewById(com.google.zxing.client.android.R.id.assetpand_text_fqr);
        assetpand_fqsj = findViewById(com.google.zxing.client.android.R.id.assetpand_text_fqsj);
        assetpand_jdt = findViewById(com.google.zxing.client.android.R.id.assetpand_text_jdt);
        assetpand_pdsj = findViewById(com.google.zxing.client.android.R.id.assetpand_text_pdsj);
        assetpand_assetname = findViewById(com.google.zxing.client.android.R.id.assetpand_text_assetname);
        assetpand_assetcode = findViewById(com.google.zxing.client.android.R.id.assetpand_text_assetcode);
        assetpand_assetuser = findViewById(com.google.zxing.client.android.R.id.assetpand_text_usename);
        assetpand_assettype = findViewById(com.google.zxing.client.android.R.id.assetpand_text_assettype);
        assetpand_assetspex = findViewById(com.google.zxing.client.android.R.id.assetpand_text_assetspex);
        assetpand_assetunit = findViewById(com.google.zxing.client.android.R.id.assetpand_text_unit);
        assetpand_assetbdpart = findViewById(com.google.zxing.client.android.R.id.assetpand_text_belongdepartment);
        assetpand_savelocation = findViewById(com.google.zxing.client.android.R.id.assetpand_text_savelocation);
        assetpand_uselocation = findViewById(com.google.zxing.client.android.R.id.assetpand_text_uselocation);
        radioGroup = findViewById(com.google.zxing.client.android.R.id.assetpand_radiogroup);
        assetpand_sure = findViewById(com.google.zxing.client.android.R.id.assetpand_surebutton);
        image_show1 = findViewById(com.google.zxing.client.android.R.id.pandasset_image);
    }

    class saveButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            CheckInformationBean checkInformationBean = new CheckInformationBean();
            checkInformationBean.setCode(pandcode);
            checkInformationBean.setFqr(fqr);
            checkInformationBean.setFqsj(fqsj);
            checkInformationBean.setPdlx(pdlx);
            checkInformationBean.setPdsj(pdsj);
            String json = new Gson().toJson(checkInformationBean);
            if ("".equals(assetname) || "null".equals(assetname) || assetname == null) {
                Intent it = new Intent("PDINFORMATIONACTIVITY");
                it.putExtra("pdinformation",json);
                startActivity(it);
                finish();
            } else {
                //获取盘点编码pandcode
                //获取资产编码assetcode
                //获取当前时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = sdf.format(new Date());
                Long datanum = System.currentTimeMillis();
                //获取图片
                byte[] imagedata = null;
                if (bitmap == null || "".equals(bitmap) || "null".equals(bitmap)) {
                    imagedata = new byte[0];
                } else {
                    //获得大小
                    int size = bitmap.getWidth() * bitmap.getHeight() * 4;
                    //创建一个字节数组输出流，大小为size
                    ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
                    try {
                        //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        //将字节数组输出流转化为字节数组
                        imagedata = bos.toByteArray();
                    } catch (Exception e) {
                        imagedata = new byte[0];
                    } finally {
                        try {
                            bitmap.recycle();
                            bos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //获取盘点方式
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    radioButton = (RadioButton) radioGroup.getChildAt(i);
                    if (radioButton.isChecked()) {
                        pandstatus = (String) radioButton.getText();
                    }
                }
                //获取链接
                try {
                    DBOpenHelper dbOpenHelper = new DBOpenHelper(getApplicationContext(), "my.db", null, 6);
                    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                    //根据盘点编码、资产编码修改此条资产的资产状态，盘点状态，盘点时间
//                    String sql = "update hdcz_assetpand set asset_pandstatus = ? ,asset_status=?,asset_deffind2=?  where asset_deffind1 =? and asset_code = ?";
//                    db.execSQL(sql, new String[]{"1", pandstatus, date, pandcode, assetcode});
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("asset_pandstatus", "1");
                    contentValues.put("asset_status", pandstatus);
                    contentValues.put("asset_deffind2", date);
                    contentValues.put("asset_pdphoto", imagedata);
                    contentValues.put("asset_deffind3",datanum);
                    String tablename = "hdcz_assetpand";
                    String where = "asset_deffind1 = ? and asset_code = ?";
                    String[] strings = {pandcode, assetcode};
                    db.update(tablename, contentValues, where, strings);
                    Intent it = new Intent("PDINFORMATIONACTIVITY");
                    it.putExtra("pdinformation",json);
                    startActivity(it);
                    finish();
                } catch (Exception e) {
                    Log.e("链接异常", e.toString());
                }
            }
        }
    }

    class backButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    //调取相机
    class cameraButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.DEFAULT_KEYS_DIALER) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            image_show1.setImageBitmap(bitmap);
        }
    }
}
