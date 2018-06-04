package com.example.soulf.mushroomiotfarm.fragment;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.soulf.mushroomiotfarm.MainActivity;
import com.example.soulf.mushroomiotfarm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by soulf on 3/2/2018.
 */

public class LightFragment extends Fragment {
    private DatabaseReference databaseReference;
    private String LightString;
    private String urlField3 = "https://thingspeak.com/channels/437884/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=Humidity+&type=line";
    private FirebaseDatabase firebaseDatabase;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

//        Get Value From Firebase
        getValueFromFirebase();
//      On Controller
        onController();

//        Off Controller
        offController();
//   create WebView
        createWebView();
//   create Toolbar
        createToolbar();
    } // Main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemSignOut){
            mySignOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void mySignOut() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

//        Return to AuthenFragment
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentMainFragment, new AuthenFragment())
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_graph, menu);
    }

    private void createToolbar() {

        Toolbar toolbar = getView().findViewById(R.id.toolbarLight);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.temp_th));
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.update_th));

        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);
    }

    private void offController() {
        Button button = getView().findViewById(R.id.btnOff);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    updateLight("OFF");
            }
        });
    }

    private void updateLight(String lightString) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("Light", lightString);
        databaseReference.updateChildren(stringObjectMap);

    }

    private void onController() {
        Button button = getView().findViewById(R.id.btnOn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLight("ON");
            }
        });
    }

    private void getValueFromFirebase() {

        final TextView textView = getView().findViewById(R.id.txtLight);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                LightString = String.valueOf(map.get("Light"));

                textView.setText(LightString);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void createWebView() {

        WebView field3WebView = getView().findViewById(R.id.webViewlight);

        WebViewClient field3WebViewClient = new WebViewClient();
        field3WebView.setWebViewClient(field3WebViewClient);
        field3WebView.loadUrl(urlField3);
        field3WebView.getSettings().setJavaScriptEnabled(true);
//

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_light, container, false);
        return view;
    }
}
