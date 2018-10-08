package com.example.nzlive.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nzlive.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonageFragment extends Fragment {


    public PersonageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_personage, container, false);
        TextView textView=view.findViewById(R.id.textview);
        textView.setText("f4");
        return view;
    }

}
