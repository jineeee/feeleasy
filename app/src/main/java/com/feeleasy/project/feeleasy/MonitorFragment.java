package com.feeleasy.project.feeleasy;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MonitorFragment extends android.app.Fragment {
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_monitor, container, false);

        final Switch sw = v.findViewById(R.id.switchOut);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String msg = b ? "외출모드가 켜졌습니다." : "외출모드가 꺼졌습니다.";
                Toast.makeText(v.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
/*
 AlertDialog.Builder dlg = new AlertDialog.Builder(getView().getContext());
 dlg.setTitle("외출모드 설정").setPositiveButton("확인", null);
                if (b) {
                    dlg.setMessage("외출모드를 켜시겠습니까?")
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    sw.setChecked(false);
                                }
                            }).show();
                } else {
                    dlg.setMessage("외출모드를 끄시겠습니까?")
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    sw.setChecked(true);
                                }
                            }).show();
                }
*/