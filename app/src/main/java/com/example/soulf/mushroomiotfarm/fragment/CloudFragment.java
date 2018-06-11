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
 * Created by soulf on 3/9/2018.
 */

public class CloudFragment extends Fragment {
    private DatabaseReference databaseReference;
    private String CloudString;
    private FirebaseDatabase firebaseDatabase;
    private String urlField5 = "https://thingspeak.com/channels/437884/charts/4?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=FogSw&type=line";
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                Get Value From Firebase
        getValueFromFirebase();
//      On Controller
        onController();

//        Off Controller
        OffController();

        createWebView();

//        Create Toolbar
        createToolbar();

    }  //Main method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemSignOut) {
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
        Toolbar toolbar = getView().findViewById(R.id.toolbarCloud);
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

    private void createWebView() {
        WebView field5WebView = getView().findViewById(R.id.webViewCloud);

        WebViewClient field5WebViewClient = new WebViewClient();
        field5WebView.setWebViewClient(field5WebViewClient);
        field5WebView.loadUrl(urlField5);
        field5WebView.getSettings().setJavaScriptEnabled(true);
    }


    private void OffController() {
        Button button = getView().findViewById(R.id.btnOff);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCloud(0);
            }
        });
    }

    private void updateCloud(int CloudString) {
        Map<String, Object> stringObjectsMap = new HashMap<>();
        stringObjectsMap.put("Cloud", CloudString);
        databaseReference.updateChildren(stringObjectsMap);
    }

    private void onController() {
            Button button =getView().findViewById(R.id.btnOn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateCloud(1);
                }
            });
    }
    private void getValueFromFirebase() {
    final  TextView textView = getView().findViewById(R.id.txtCloud);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                CloudString = String.valueOf(map.get("Cloud"));

                textView.setText(CloudString);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cloud, container, false);
        return view;
    }
}


