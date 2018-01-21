package hdcz.com.app.greenland1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.client.android.CaptureActivity;

import java.util.Map;

import hdcz.com.app.greenland1.sharedpreferences.SharedHelper;

/**
 * Created by guyuqiang on 2017/12/26.
 */

public class HandConfiguration extends AppCompatActivity {
    private EditText hlj;
    private String ljtext;
    private Button sebutton;
    private Button scanbutton;
    private SharedHelper sh;
    private Context mcontext;
    @Override
    public  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handconfiguration);
        mcontext = getApplicationContext();
        sh = new SharedHelper(mcontext);
        sebutton = findViewById(R.id.hand_saveedit);
        sebutton.setOnClickListener(new SaveEditButton());
        scanbutton = findViewById(R.id.hand_scanbutton);
        scanbutton.setOnClickListener(new Scanbutton());
    }
    class SaveEditButton implements View.OnClickListener{
        @Override
        public void onClick(View v) {
          hlj = findViewById(R.id.hand_lianjietext);
          ljtext = hlj.getText().toString();
            Intent it = new Intent(HandConfiguration.this,MainActivity.class);
            it.putExtra("ljtext",ljtext);
            startActivity(it);
        }
    }
    class Scanbutton implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent it1 = new Intent(HandConfiguration.this,CaptureActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("status","scan");
            it1.putExtras(bundle);
            startActivity(it1);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Map<String,String> map = sh.getData();
        hlj = findViewById(R.id.hand_lianjietext);
        hlj.setText(map.get("ljtext"));
    }
}
