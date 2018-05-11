package com.example.soulf.mushroomiotfarm.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.soulf.mushroomiotfarm.MainActivity;
import com.example.soulf.mushroomiotfarm.R;

/**
 * Created by soulf on 3/1/2018.
 */

public class MainFragment extends Fragment{
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        Manual Controller
        manualController();

//        Auto Controller
        autoController();

    } //Main Method

    private void autoController() {
        Button button = getView().findViewById(R.id.btnAuto);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Replace Fragment
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new AutoFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void manualController() {
        Button button = getView().findViewById(R.id.btnManual);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new ManualFragment())
                        .addToBackStack(null).commit();
            }
        });
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}
