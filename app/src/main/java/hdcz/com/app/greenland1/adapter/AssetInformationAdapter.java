package hdcz.com.app.greenland1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.bean.AssetInformationBean;

/**
 * Created by guyuqiang on 2018/1/3.17:11
 */

public class AssetInformationAdapter extends BaseAdapter {
    private List<AssetInformationBean> data;
    private Context mcontext;
    public AssetInformationAdapter(List<AssetInformationBean> data,Context context){
        this.data=data;
        this.mcontext=context;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mcontext).inflate(R.layout.assetinformation_list_item,parent,false);
        TextView assetname =convertView.findViewById(R.id.assetinformation_text_assetname);
        TextView status = convertView.findViewById(R.id.assetinformation_text_status);
        ImageView image = convertView.findViewById(R.id.assetinformation_image);
        TextView assetcode = convertView.findViewById(R.id.assetinformation_text_assetcode);
        TextView usename = convertView.findViewById(R.id.assetinformation_text_usename);
        TextView assettype = convertView.findViewById(R.id.assetinformation_text_assettype);
        TextView assetspex = convertView.findViewById(R.id.assetinformation_text_assetspex);
        TextView assetunit = convertView.findViewById(R.id.assetinformation_text_unit);
        TextView belongdepartment = convertView.findViewById(R.id.assetinformation_text_belongdepartment);
        TextView uselocation = convertView.findViewById(R.id.assetinformation_text_uselocation);
        TextView savelocation = convertView.findViewById(R.id.assetinformation_text_savelocation);
        assetname.setText(data.get(position).getAsset_name());
        status.setText(data.get(position).getAsset_status());
        if("手动报损".equals(data.get(position).getAsset_status())){
            status.setBackgroundColor(Color.parseColor("#bd3131"));
        }
        assetcode.setText(data.get(position).getAsset_code());
        usename.setText(data.get(position).getAsset_usename());
        assettype.setText(data.get(position).getAsset_type());
        assetspex.setText(data.get(position).getAsset_spex());
        assetunit.setText(data.get(position).getAsset_unit());
        belongdepartment.setText(data.get(position).getAsset_belongdepart());
        uselocation.setText(data.get(position).getAsset_uselocation());
        savelocation.setText(data.get(position).getAsset_savelocation());
        byte [] imagedate = data.get(position).getAsset_pdphoto();
        if(imagedate==null||"".equals(imagedate)||"null".equals(imagedate)) {
        }else{
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagedate, 0, imagedate.length);
            image.setImageBitmap(bitmap);
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
