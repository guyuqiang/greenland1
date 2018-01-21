package hdcz.com.app.greenland1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.bean.AssetInformationBean;

/**
 * Created by guyuqiang on 2018/1/15.12:17
 */

public class SerachAssetAdapter extends BaseAdapter {
    private List<AssetInformationBean> data;
    private Context mcontext;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mcontext).inflate(R.layout.serachasset_list_item,parent,false);
        TextView assetname =convertView.findViewById(R.id.serachassetinformation_text_assetname);
        TextView assetcode = convertView.findViewById(R.id.serachassetinformation_text_assetcode);
        TextView usename = convertView.findViewById(R.id.serachassetinformation_text_usename);
        TextView assettype = convertView.findViewById(R.id.serachassetinformation_text_assettype);
        TextView assetspex = convertView.findViewById(R.id.serachassetinformation_text_assetspex);
        TextView assetunit = convertView.findViewById(R.id.serachassetinformation_text_unit);
        TextView belongdepartment = convertView.findViewById(R.id.serachassetinformation_text_belongdepartment);
        TextView uselocation = convertView.findViewById(R.id.serachassetinformation_text_uselocation);
        TextView savelocation = convertView.findViewById(R.id.serachassetinformation_text_savelocation);
        assetname.setText(data.get(position).getAsset_name());
        assetcode.setText(data.get(position).getAsset_code());
        usename.setText(data.get(position).getAsset_usename());
        assettype.setText(data.get(position).getAsset_type());
        assetspex.setText(data.get(position).getAsset_spex());
        assetunit.setText(data.get(position).getAsset_unit());
        belongdepartment.setText(data.get(position).getAsset_belongdepart());
        uselocation.setText(data.get(position).getAsset_uselocation());
        savelocation.setText(data.get(position).getAsset_savelocation());
     return convertView;
    }

    public SerachAssetAdapter(List<AssetInformationBean> data,Context context) {
       this.data= data;
       this.mcontext = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
