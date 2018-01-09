package hdcz.com.app.greenland1.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import hdcz.com.app.greenland1.InformationActivity;
import hdcz.com.app.greenland1.PdInformationActivity;
import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.adapter.CheckInformationAdapter;
import hdcz.com.app.greenland1.bean.CheckInformationBean;

/**
 * Created by guyuqiang on 2017/12/28.12:32
 */

public class FragementXiaoXi extends Fragment {
    private List<CheckInformationBean> data = null;
    private CheckInformationAdapter checkinfordapter = null;
    private ListView checkinforlistview;
    private CheckInformationBean checkInformationBean1;
    private CheckInformationBean checkInformationBean2;
    private CheckInformationBean checkInformationBean3;
    private RadioButton rb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xiaoxi_content, container, false);
        dataInit();
        for (CheckInformationBean check : data
                ) {
            Log.e("data", check.toString());
        }
        checkinfordapter = new CheckInformationAdapter(data, getActivity());
        checkinforlistview = view.findViewById(R.id.xiaoxi_listview);
        checkinforlistview.setAdapter(checkinfordapter);
        checkinforlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("CommitTransaction")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               CheckInformationBean cf = data.get(position);
                Toast.makeText(view.getContext(), "你点击了第" + position + "项,发起人:"+cf.getFqr(), Toast.LENGTH_SHORT).show();
                //FragmentPanDian fpd = new FragmentPanDian();
                InformationActivity activity = (InformationActivity) getActivity();
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.ly_content,fpd);
//                RadioButton rb = activity.findViewById(R.id.rb_pandian);
//                rb.setChecked(true);
                Intent it1 = new Intent(view.getContext(), PdInformationActivity.class);
                it1.putExtra("pdinformation",new Gson().toJson(cf));
                startActivity(it1);

            }
        });
        return view;
    }

    public void dataInit() {
        data = new ArrayList<CheckInformationBean>();
        checkInformationBean1 = new CheckInformationBean();
        checkInformationBean2 = new CheckInformationBean();
        checkInformationBean3 = new CheckInformationBean();
        checkInformationBean1.setCode("NCGS-001");
        checkInformationBean2.setCode("NCGS-002");
        checkInformationBean3.setCode("NCGS-003");
        checkInformationBean1.setFqr("刘星");
        checkInformationBean2.setFqr("小雨");
        checkInformationBean3.setFqr("小雪");
        checkInformationBean1.setFqsj("2017-11-30");
        checkInformationBean2.setFqsj("2017-12-30");
        checkInformationBean3.setFqsj("2017-12-29");
        checkInformationBean1.setPdlx("抽盘");
        checkInformationBean2.setPdlx("抽盘");
        checkInformationBean3.setPdlx("盘点");
        checkInformationBean1.setPdsj("2017-11-30--2017-12-2");
        checkInformationBean2.setPdsj("2017-12-30--2017-12-31");
        checkInformationBean3.setPdsj("2017-12-29--2017-12-31");
        data.add(checkInformationBean1);
        data.add(checkInformationBean2);
        data.add(checkInformationBean3);
    }

}
