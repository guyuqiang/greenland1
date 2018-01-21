package hdcz.com.app.greenland1.fragment;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.adapter.HistoryAssetInfoAdapter;
import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.bean.CheckInformationBean;
import hdcz.com.app.greenland1.dao.AssetInformationDao;
import hdcz.com.app.greenland1.util.GetDbUtil;

/**
 * Created by guyuqiang on 2018/1/14.14:50
 */

@SuppressLint("ValidFragment")
public class FragementHistoryAsset extends Fragment {
    private CheckInformationBean checkInformationBean;
    private List<AssetInformationBean> data;
    private ListView historyassetlistview;
    private HistoryAssetInfoAdapter historyAssetInfoAdapter;
    public FragementHistoryAsset(CheckInformationBean checkben){
        this.checkInformationBean = checkben;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.historyassetinfo_content,container,false);
      //获取数据
        AssetInformationDao assetInformationDao = new AssetInformationDao();
        SQLiteDatabase db = GetDbUtil.getDb(getContext(),4,"my.db");
        data = assetInformationDao.getAssetByPdcode(checkInformationBean.getCode(),db);
        historyassetlistview = view.findViewById(R.id.historyassetinfor_listview);
        historyAssetInfoAdapter = new HistoryAssetInfoAdapter(data,getContext());
        historyassetlistview.setAdapter(historyAssetInfoAdapter);
       return view;
    }
}
