package hdcz.com.app.greenland1.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import hdcz.com.app.greenland1.HistoryAssetInfoActivity;
import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.adapter.CheckInformationAdapter;
import hdcz.com.app.greenland1.adapter.HistoryPandAdapter;
import hdcz.com.app.greenland1.bean.CheckInformationBean;
import hdcz.com.app.greenland1.dao.CheckInformationDao;
import hdcz.com.app.greenland1.sharedpreferences.SharedHelper;
import hdcz.com.app.greenland1.util.GetDbUtil;

/**
 * Created by guyuqiang on 2017/12/28.12:32
 */

public class FragmentPanDian extends android.support.v4.app.Fragment {
    private List<CheckInformationBean> data;
    private SharedHelper sh;
    private CheckInformationAdapter checkinfordapter;
    private ListView checklistview;
    public FragmentPanDian(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pandian_content,container,false);
        //获取数据
        CheckInformationDao checkInformationDao = new CheckInformationDao();
        sh = new SharedHelper(getActivity());
        Map<String,String> map = sh.getData();
        SQLiteDatabase db = GetDbUtil.getDb(getContext(),4,"my.db");
        data = checkInformationDao.getCheckAllByUser(map.get("name"),db);
        HistoryPandAdapter historyPandAdapter = new HistoryPandAdapter(data,getActivity());
        checklistview = view.findViewById(R.id.pandhistory_listview);
        checklistview.setAdapter(historyPandAdapter);
        checklistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckInformationBean checkInformationBean = data.get(position);
                Intent it = new Intent(view.getContext(), HistoryAssetInfoActivity.class);
                it.putExtra("pdinformation",new Gson().toJson(checkInformationBean));
                startActivity(it);
            }
        });
        return view;
    }
}
