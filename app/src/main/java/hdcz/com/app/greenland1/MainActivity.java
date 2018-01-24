package hdcz.com.app.greenland1;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

import hdcz.com.app.greenland1.bean.PersonBean;
import hdcz.com.app.greenland1.dao.PersonDao;
import hdcz.com.app.greenland1.db.DBOpenHelper;
import hdcz.com.app.greenland1.md5.MD5;
import hdcz.com.app.greenland1.sharedpreferences.SharedHelper;
import hdcz.com.app.greenland1.util.GetDbUtil;
import hdcz.com.app.greenland1.util.TosatShowUtil;
import hdcz.com.app.greenland1.webservice.WebServiceDao;

public class MainActivity extends AppCompatActivity {
    private Context mcontext;
    private Context context;
    private SharedHelper sh;
    private String name;
    private String password;
    private String md5password;
    private EditText editname;
    private EditText editpassword;
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private PersonBean person;
    private PersonBean person1;
    private PersonDao personDao;
    private String lijtext;
    private String backresult = "0";
    private ImageView main_pgbar_imageview;
    private AnimationDrawable ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = getApplicationContext();
        context = MainActivity.this;
        Intent it = getIntent();
        lijtext = it.getStringExtra("ljtext");
        personDao = new PersonDao();
        db = GetDbUtil.getDb(mcontext, 3, "my.db");
        sh = new SharedHelper(mcontext);
        Button hcbutton = findViewById(R.id.shoudong);
        //手动配置按钮添加事件
        hcbutton.setOnClickListener(new HandConfigurationButton());
        Button scanbutton = findViewById(R.id.saomiao);
        //扫描配置按钮添加事件
        scanbutton.setOnClickListener(new ScanConfigurationButton());
        Button savebutton = findViewById(R.id.savebutton);
        //登陆按钮添加事件
        savebutton.setOnClickListener(new SaveButton());
        //加载进度条imageview
        main_pgbar_imageview = findViewById(R.id.main_loading);
        ad = (AnimationDrawable) main_pgbar_imageview.getDrawable();
        main_pgbar_imageview.postDelayed(new Runnable() {
            @Override
            public void run() {
                ad.start();
            }
        }, 100);
        main_pgbar_imageview.setVisibility(View.INVISIBLE);
        //父layout添加点击事件
        findViewById(R.id.main_layout).setOnClickListener(new mainlayout());
    }

    class SaveButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            main_pgbar_imageview.setVisibility(View.VISIBLE);
            editname = findViewById(R.id.editUser);
            editpassword = findViewById(R.id.editPassword);
            name = editname.getText().toString();
            password = editpassword.getText().toString();
            //加密密码
            md5password = MD5.getMD5(password);
            person = new PersonBean();
            person.setName(name);
            person.setPassword(password);
            //数据存入到sharedpreferences中
            if ("".equals(lijtext) || "null".equals(lijtext) || lijtext == null) {
                lijtext = sh.getData().get("ljtext");
            }
            sh.saveData(name, password, lijtext);
            RadioGroup radioGroup = findViewById(R.id.main_radiogroup);
            if ("".equals(lijtext) || "null".equals(lijtext) || lijtext == null) {
                main_pgbar_imageview.setVisibility(View.INVISIBLE);
                TosatShowUtil.showShort(mcontext,"配置链接地址！");
            } else {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
                    if (rb.isChecked()) {
                        final String message = (String) rb.getText();
                        if ("连线登陆".equals(message)) {
                            //远程验证用户名和密码是否正确
                            final WebServiceDao webServiceDao = new WebServiceDao();
                            final AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                                @Override
                                protected String doInBackground(Void... voids) {
                                    // backresult = webServiceDao.userLog(name,md5password,lijtext);
                                    logIn(name, md5password, lijtext);
                                    return null;
                                }
                            };
                            task.execute();
                        } else {
                            //从本地数据库中验证用户名和密码是否正确
                            person1 = personDao.checkPerson(name, password, db);
                            if ("null".equals(person1) || person1 == null) {
                                main_pgbar_imageview.setVisibility(View.INVISIBLE);
                                TosatShowUtil.showShort(mcontext,"用户不存在！");
                            } else {
                                Intent it = new Intent(MainActivity.this, InformationActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("status", "消息");
                                it.putExtras(bundle);
                                main_pgbar_imageview.setVisibility(View.INVISIBLE);
                                startActivity(it);
                            }
                        }
                    }
                }
            }
        }
    }

    class HandConfigurationButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent it = new Intent(MainActivity.this, HandConfiguration.class);
            startActivity(it);
        }
    }

    class ScanConfigurationButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent it1 = new Intent(MainActivity.this, CaptureActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("status", "scan");
            it1.putExtras(bundle);
            startActivity(it1);
        }
    }

    //父layout添加点击事件
    class mainlayout implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String, String> map = sh.getData();
        editname = findViewById(R.id.editUser);
        editpassword = findViewById(R.id.editPassword);
        editname.setText(map.get("name"));
        editpassword.setText(map.get("password"));
    }

    public void logIn(String name, String password, String url) {
        //定义获取手机信息的SoapAction与命名空间,作为常量
        String namespace = "webservices.blog.weaver.com.cn/";
        //登陆相关信息
        String log_methodname = "checkUserBynameAndPassword";
        //String log_logurl = "http://125.69.90.196:10089//services/GetInventoryDataService";
        String log_logurl = url + "//services/GetInventoryDataService";
        String log_soapAction = "webservices.blog.weaver.com.cn/checkUserBynameAndPassword";
        //指定webservice的命名空间和调用方法
        SoapObject soapObject = new SoapObject(namespace, log_methodname);
        //设置需要传入的参数
        soapObject.addProperty("username", name);
        soapObject.addProperty("password", password);
        //生成调用webservice方法的soap请求信息，并指定soap版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        //设置是否调用的是dotnet开发的webservice
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        //获取网络链接
        HttpTransportSE transport = new HttpTransportSE(log_logurl);
        try {
            //调用webservice
            transport.call(namespace, envelope);
            //获取返回的数据
            SoapObject object = (SoapObject) envelope.bodyIn;
            //获取返回结果
            backresult = object.getProperty(0).toString();
        } catch (HttpResponseException e) {
            e.printStackTrace();
            backresult = "2";
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            backresult = "2";
        } catch (IOException e) {
            e.printStackTrace();
            backresult = "2";
        }
        handler.sendEmptyMessage(0x001);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0x001:
                    if (Integer.parseInt(backresult) == 1) {
                        //根据name从数据库中查询数据
                        person1 = personDao.getPersonByName(name, db);
                        //数据库中有此条数据
                        if ("null".equals(person1) || person1 == null) {
                            //数据存入到数据库中-插入
                            personDao.savePserson(person, db);
                        } else {
                            //数据存入到数据库中-更新
                            personDao.updatePersonByName(name, password, db);
                        }
                        Intent it = new Intent(MainActivity.this, InformationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("status", "消息");
                        it.putExtras(bundle);
                        main_pgbar_imageview.setVisibility(View.INVISIBLE);
                        startActivity(it);
                    } else {
                        if (Integer.parseInt(backresult) == 0) {
                            main_pgbar_imageview.setVisibility(View.INVISIBLE);
                            TosatShowUtil.showShort(mcontext,"用户名或者密码错误！");
                        } else {
                            main_pgbar_imageview.setVisibility(View.INVISIBLE);
                            TosatShowUtil.showShort(mcontext,"请求失败！");
                        }
                    }
            }
        }
    };
}
