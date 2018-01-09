package hdcz.com.app.greenland1;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Map;

import hdcz.com.app.greenland1.bean.PersonBean;
import hdcz.com.app.greenland1.dao.LoginDao;
import hdcz.com.app.greenland1.dao.PersonDao;
import hdcz.com.app.greenland1.db.DBOpenHelper;
import hdcz.com.app.greenland1.md5.MD5;
import hdcz.com.app.greenland1.sharedpreferences.SharedHelper;

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
    private String backresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = getApplicationContext();
        context = MainActivity.this;
        Intent it = getIntent();
        lijtext = it.getStringExtra("ljtext");
        personDao = new PersonDao();
        dbOpenHelper = new DBOpenHelper(context,"my.db",null,2);
        dbOpenHelper.getWritableDatabase();
        db = dbOpenHelper.getWritableDatabase();
        sh = new SharedHelper(mcontext);
        Button hcbutton = findViewById(R.id.shoudong);
        hcbutton.setOnClickListener(new HandConfigurationButton());
        Button scanbutton = findViewById(R.id.saomiao);
        scanbutton.setOnClickListener(new ScanConfigurationButton());
        Button savebutton = findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new SaveButton());
    }
    class SaveButton implements View.OnClickListener{
        @Override
        public  void onClick(View v){
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
            if("".equals(lijtext)||"null".equals(lijtext)||lijtext==null){
                lijtext = sh.getData().get("ljtext");
            }
            sh.saveData(name,password,lijtext);
            RadioGroup radioGroup = findViewById(R.id.main_radiogroup);
            if("".equals(lijtext)||"null".equals(lijtext)||lijtext==null){
                Toast.makeText(mcontext,"请点击底部的配置填写链接地址！",Toast.LENGTH_SHORT).show();
            }else {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
                    if (rb.isChecked()) {
                        String message = (String) rb.getText();
                        if ("连线登陆".equals(message)) {
                            //远程验证用户名和密码是否正确
//                            String url =lijtext+"/plugins/hdcz/app/App.jsp?name="+name+"&password="+md5password;
//                            backresult = new LoginDao().logIn(url);
//                            Toast.makeText(mcontext,backresult,Toast.LENGTH_SHORT).show();
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
                            startActivity(it);
                        } else {
                            //从本地数据库中验证用户名和密码是否正确
                            person1 = personDao.checkPerson(name, password, db);
                            if ("null".equals(person1) || person1 == null) {
                                Toast.makeText(mcontext, "用户不存在！", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent it = new Intent(MainActivity.this, InformationActivity.class);
                                startActivity(it);
                            }
                        }
                    }
                }
            }
        }
    }
    class HandConfigurationButton implements View.OnClickListener{
        @Override
        public  void onClick(View v){
           Intent it = new Intent(MainActivity.this,HandConfiguration.class );
           startActivity(it);
        }
    }
    class ScanConfigurationButton implements View.OnClickListener{
        @Override
        public  void onClick(View v){
            Intent it1 = new Intent(MainActivity.this,ScanConfiguration.class);
            startActivity(it1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String,String> map = sh.getData();
        editname = findViewById(R.id.editUser);
        editpassword = findViewById(R.id.editPassword);
        editname.setText(map.get("name"));
        editpassword.setText(map.get("password"));
    }
}
