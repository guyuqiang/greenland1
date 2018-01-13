package hdcz.com.app.greenland1.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import hdcz.com.app.greenland1.AssetPandInformationActivity;
import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.adapter.AssetInformationAdapter;
import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.bean.CheckInformationBean;
import hdcz.com.app.greenland1.dao.AssetInformationDao;
import hdcz.com.app.greenland1.util.GetDbUtil;

/**
 * Created by guyuqiang on 2018/1/3.17:48
 */

@SuppressLint("ValidFragment")
public class FragementYetCheck extends Fragment {
    private List<AssetInformationBean> data = new ArrayList<AssetInformationBean>();
    private AssetInformationAdapter assetInformationAdapter;
    private ListView assetlistview;
    private CheckInformationBean checkInformationBean;
    private AssetInformationBean assetInformationBean;
    public  FragementYetCheck(CheckInformationBean bean){
        this.checkInformationBean = bean;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assetinformation,container,false);
        //获取数据
        getData();
        assetlistview = view.findViewById(R.id.listview_assetinformation);
        assetInformationAdapter = new AssetInformationAdapter(data,getActivity());
        assetlistview.setAdapter(assetInformationAdapter);
        assetlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                assetInformationBean = data.get(position);
                Intent it = new Intent(view.getContext(), AssetPandInformationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("checkInformationBean",new Gson().toJson(checkInformationBean));
                bundle.putString("assetInformationBean",new Gson().toJson(assetInformationBean));
                it.putExtras(bundle);
                startActivity(it);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //查询相应编码以及未盘点的数据
    public void getData(){
        //获取链接
        SQLiteDatabase db = GetDbUtil.getDb(getActivity(),2,"my.db");
        AssetInformationDao assetInformationDao = new AssetInformationDao();
        data = assetInformationDao.getAssetByPdcode(checkInformationBean.getCode(),"1",db);
    }
}
