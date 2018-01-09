package hdcz.com.app.greenland1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.bean.CheckInformationBean;

/**
 * Created by guyuqiang on 2017/12/31.17:13
 */

public class CheckInformationAdapter extends BaseAdapter {
    private List<CheckInformationBean> data;
    private Context mcontext;
    public  CheckInformationAdapter (List<CheckInformationBean> data , Context context){
        this.data = data;
        this.mcontext = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mcontext).inflate(R.layout.checkinformation_list_item,parent,false);
        TextView code = convertView.findViewById(R.id.xiaoxi_code);
        TextView fqr = convertView.findViewById(R.id.xiaoxi_fqr);
        TextView fqsj = convertView.findViewById(R.id.xiaoxi_fqsj);
        TextView status = convertView.findViewById(R.id.xiaoxi_satus);
        TextView jdt = convertView.findViewById(R.id.xiaoxi_jdt);
        TextView pdsj = convertView.findViewById(R.id.xiaoxi_pdsj);
        code.setText(data.get(position).getCode());
        fqr.setText(data.get(position).getFqr());
        fqsj.setText(data.get(position).getFqsj());
        status.setText(data.get(position).getPdlx());
        pdsj.setText(data.get(position).getPdsj());
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return data.size();
    }

}
