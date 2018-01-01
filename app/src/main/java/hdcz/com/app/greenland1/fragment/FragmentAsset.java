package hdcz.com.app.greenland1.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hdcz.com.app.greenland1.R;

/**
 * Created by guyuqiang on 2017/12/28.12:32
 */

public class FragmentAsset extends android.support.v4.app.Fragment{
    public FragmentAsset(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.asset_content,container,false);
       return view;
    }
}
