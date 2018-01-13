package com.google.zxing.client.android;

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

import com.google.zxing.client.android.db.DBOpenHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pandasset);
        bindView();
        Intent it1 = getIntent();
        Bundle bundle = it1.getExtras();
        pandcode = bundle.getString("code");
        String fqr = bundle.getString("fqr");
        String fqsj = bundle.getString("fqsj");
        String pdlx = bundle.getString("pdlx");
        String pdsj = bundle.getString("pdsj");
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
            Toast.makeText(getApplicationContext(), "此资产不在未盘点列表中！", Toast.LENGTH_SHORT).show();
        }
        //确定按钮添加事件
        assetpand_sure.setOnClickListener(new saveButton());
        //返回按钮添加事件
        assetpand_back.setOnClickListener(new backButton());
        //调取相机
        image_show1.setOnClickListener(new cameraButton());
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
        radioGroup = findViewById(R.id.assetpand_radiogroup);
        assetpand_sure = findViewById(R.id.assetpand_surebutton);
        image_show1 = findViewById(R.id.pandasset_image);
    }

    class saveButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if ("".equals(assetname) || "null".equals(assetname) || assetname == null) {
                finish();
            } else {
                //获取盘点编码pandcode
                //获取资产编码assetcode
                //获取当前时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = sdf.format(new Date());
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
                    DBOpenHelper dbOpenHelper = new DBOpenHelper(getApplicationContext(), "my.db", null, 2);
                    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                    //根据盘点编码、资产编码修改此条资产的资产状态，盘点状态，盘点时间
//                    String sql = "update hdcz_assetpand set asset_pandstatus = ? ,asset_status=?,asset_deffind2=?  where asset_deffind1 =? and asset_code = ?";
//                    db.execSQL(sql, new String[]{"1", pandstatus, date, pandcode, assetcode});
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("asset_pandstatus", "1");
                    contentValues.put("asset_status", pandstatus);
                    contentValues.put("asset_deffind2", date);
                    contentValues.put("asset_pdphoto", imagedata);
                    String tablename = "hdcz_assetpand";
                    String where = "asset_deffind1 = ? and asset_code = ?";
                    String[] strings = {pandcode, assetcode};
                    db.update(tablename, contentValues, where, strings);
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
