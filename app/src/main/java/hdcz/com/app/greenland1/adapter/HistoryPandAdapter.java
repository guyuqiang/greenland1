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
 * Created by guyuqiang on 2018/1/15.16:52
 */

public class HistoryPandAdapter extends BaseAdapter {
    private List<CheckInformationBean> data;
    private Context mcontext;

    public HistoryPandAdapter(List<CheckInformationBean> data, Context context) {
        this.data = data;
        this.mcontext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mcontext).inflate(R.layout.historypand_list_item, parent, false);
        TextView code = convertView.findViewById(R.id.historypand_code);
        TextView fqr = convertView.findViewById(R.id.historypand_fqr);
        TextView wcsj = convertView.findViewById(R.id.historypand_wcsj);
        TextView status = convertView.findViewById(R.id.historypand_lx);
       // TextView pdsj = convertView.findViewById(R.id.historypand_pdsj);
        code.setText(data.get(position).getCode());
        fqr.setText(data.get(position).getFqr());
        wcsj.setText(data.get(position).getDeffind1());
        status.setText(data.get(position).getPdlx());
       // pdsj.setText(data.get(position).getPdsj());
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
