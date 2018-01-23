package hdcz.com.app.greenland1.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.bean.AssetInformationBean;

/**
 * Created by guyuqiang on 2018/1/14.15:06
 */

public class HistoryAssetInfoAdapter extends BaseAdapter {
    private List<AssetInformationBean> data;
    private Context mcontext;
    public HistoryAssetInfoAdapter(List<AssetInformationBean> data,Context context){
        this.data = data;
        mcontext = context;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        convertView = LayoutInflater.from(mcontext).inflate(R.layout.historyassetinformation_list_item,parent,false);
        TextView assetname =convertView.findViewById(R.id.historyassetinformation_text_assetname);
        TextView status = convertView.findViewById(R.id.historyassetinformation_text_status);
        final ImageView image = convertView.findViewById(R.id.historyassetinformation_image);
        TextView assetcode = convertView.findViewById(R.id.historyassetinformation_text_assetcode);
        TextView usename = convertView.findViewById(R.id.historyassetinformation_text_usename);
        TextView assettype = convertView.findViewById(R.id.historyassetinformation_text_assettype);
        TextView assetspex = convertView.findViewById(R.id.historyassetinformation_text_assetspex);
        TextView assetunit = convertView.findViewById(R.id.historyassetinformation_text_unit);
        TextView belongdepartment = convertView.findViewById(R.id.historyassetinformation_text_belongdepartment);
        TextView uselocation = convertView.findViewById(R.id.historyassetinformation_text_uselocation);
        TextView savelocation = convertView.findViewById(R.id.historyassetinformation_text_savelocation);
        TextView pdsj = convertView.findViewById(R.id.historyassetinformation_text_pandsj);
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
        pdsj.setText(data.get(position).getAsset_deffind2());
        final byte [] imagedate = data.get(position).getAsset_pdphoto();
        if(imagedate==null||"".equals(imagedate)||"null".equals(imagedate)) {
        }else{
          Bitmap bitmap = BitmapFactory.decodeByteArray(imagedate, 0, imagedate.length);
            image.setImageBitmap(bitmap);
        }
        //imageview添加事件
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View imageview = LayoutInflater.from(mcontext).inflate(R.layout.largimage,parent,false);
                final Dialog dialog = new Dialog(mcontext);
                //去掉标题
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                ImageView imageView = imageview.findViewById(R.id.largimage);
                byte [] imagedate = data.get(position).getAsset_pdphoto();
                if(imagedate==null||"".equals(imagedate)||"null".equals(imagedate)) {
                }else{
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagedate, 0, imagedate.length);
                    imageView.setImageBitmap(bitmap);
                    dialog.setContentView(imageview);
                    //获取弹出框窗口
                    Window dialogwindow = dialog.getWindow();
                    //获取屏幕参数
                    Display display = ((Activity)mcontext).getWindowManager().getDefaultDisplay();
                    dialogwindow.getDecorView().setPadding(0,0,0,0);
                    //设置弹出窗口占屏幕比
                    WindowManager.LayoutParams p = dialogwindow.getAttributes();
                    p.height = (int) (display.getHeight() * 1);
                    p.width = (int) (display.getWidth() * 1);
                    dialogwindow.setAttributes(p);
                    dialog.show();
                    imageview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }
            }
        });
        return convertView;
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
