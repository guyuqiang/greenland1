package hdcz.com.app.greenland1.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.adapter.SerachAssetAdapter;
import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.dao.HistoryAssetInfoDao;
import hdcz.com.app.greenland1.util.GetDbUtil;

/**
 * Created by guyuqiang on 2018/1/15.11:50
 */

@SuppressLint("ValidFragment")
public class FragementSerachAsset extends Fragment {
    private String serachvalues;
    private ListView listView;
    private List<AssetInformationBean> data;
    public FragementSerachAsset(String serachvalues){
        this.serachvalues = serachvalues;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.serachasset_content,container,false);
       listView = view.findViewById(R.id.serachasset_list_view);
       //获取数据
        getData(serachvalues);
        SerachAssetAdapter serachAssetAdapter = new SerachAssetAdapter(data,getContext());
        listView.setAdapter(serachAssetAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
       return view;
    }
    public  void getData(String serachvalues){
        HistoryAssetInfoDao historyAssetInfoDao = new HistoryAssetInfoDao();
        SQLiteDatabase db = GetDbUtil.getDb(getActivity(),4,"my.db");
        data = historyAssetInfoDao.getAssetAll(serachvalues,db);
    }
}
