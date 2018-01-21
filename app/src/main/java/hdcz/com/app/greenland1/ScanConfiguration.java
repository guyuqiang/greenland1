package hdcz.com.app.greenland1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

import hdcz.com.app.greenland1.sharedpreferences.SharedHelper;

/**
 * Created by guyuqiang on 2017/12/26.
 */

public class ScanConfiguration extends AppCompatActivity {
    private SharedHelper sh;
    private Context mcontext;
    private Button scansave;
    private Button scanhand;
    private EditText ljtext;
    private String ljtext1;
     @Override
    public  void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(R.layout.scanconfiguration);
         mcontext = getApplicationContext();
         sh = new SharedHelper(mcontext);
         scansave = findViewById(R.id.scan_saveE);
         scansave.setOnClickListener(new SaveEditButton());
         scanhand = findViewById(R.id.scan_hand);
         scanhand.setOnClickListener(new HandButton());
     }
    class SaveEditButton implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            ljtext = findViewById(R.id.scanconfigur_scan_ljtext);
            ljtext1 = ljtext.getText().toString();
            Intent it = new Intent(ScanConfiguration.this,MainActivity.class);
            it.putExtra("ljtext",ljtext1);
            startActivity(it);
        }
    }
    class HandButton implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent it = new Intent(ScanConfiguration.this,HandConfiguration.class);
            startActivity(it);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent it = getIntent();
        String url = it.getStringExtra("url");
        ljtext = findViewById(R.id.scanconfigur_scan_ljtext);
        ljtext.setText(url);
    }
}
