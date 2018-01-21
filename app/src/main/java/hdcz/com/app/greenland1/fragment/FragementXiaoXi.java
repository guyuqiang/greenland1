package hdcz.com.app.greenland1.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hdcz.com.app.greenland1.InformationActivity;
import hdcz.com.app.greenland1.PdInformationActivity;
import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.adapter.CheckInformationAdapter;
import hdcz.com.app.greenland1.bean.CheckInformationBean;
import hdcz.com.app.greenland1.json.AssetJson;
import hdcz.com.app.greenland1.sharedpreferences.SharedHelper;
import hdcz.com.app.greenland1.webservice.WebServiceDao;

/**
 * Created by guyuqiang on 2017/12/28.12:32
 */

@SuppressLint("ValidFragment")
public class FragementXiaoXi extends Fragment {
    private List<CheckInformationBean> data = null;
    private CheckInformationAdapter checkinfordapter = null;
    private ListView checkinforlistview;
    private RadioButton rb;
    private String datajson;

    public FragementXiaoXi(String data) {
        this.datajson = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xiaoxi_content, container, false);
        //json数据转换为对象数据
        AssetJson assetJson = new AssetJson();
        data = assetJson.getCheckinformationByJson(datajson);
        checkinfordapter = new CheckInformationAdapter(data, getActivity());
        checkinforlistview = view.findViewById(R.id.xiaoxi_listview);
        checkinforlistview.setAdapter(checkinfordapter);
        checkinforlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("CommitTransaction")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckInformationBean cf = data.get(position);
                Intent it1 = new Intent(view.getContext(), PdInformationActivity.class);
                it1.putExtra("pdinformation", new Gson().toJson(cf));
                startActivity(it1);
            }
        });
        return view;
    }
}
