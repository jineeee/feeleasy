package com.feeleasy.project.feeleasy;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    View v;
    TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        Intent intent = getActivity().getIntent();
        String id = intent.getStringExtra("uId");

        tv = v.findViewById(R.id.txtIntro);
        tv.setText("No." + id + "님 안녕하세요.");

        return v;
    }
}