package com.example.nzlive.viewPager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nzlive.R;

/**
 * Created by YouZi on 2018/5/30.
 */

public class OneFragment extends Fragment {

    private ImageView image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpage_fragment, container, false);
        image = (ImageView) view.findViewById(R.id.image);
        image.setImageResource(R.drawable.p);
        return view;
    }
}
