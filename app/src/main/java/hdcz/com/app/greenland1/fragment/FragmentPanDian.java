package hdcz.com.app.greenland1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hdcz.com.app.greenland1.R;

/**
 * Created by guyuqiang on 2017/12/28.12:32
 */

public class FragmentPanDian extends android.support.v4.app.Fragment {
    public FragmentPanDian(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pandian_content,container,false);
        return view;
    }
}
