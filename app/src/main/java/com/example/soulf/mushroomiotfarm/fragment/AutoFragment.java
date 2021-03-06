package com.example.soulf.mushroomiotfarm.fragment;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toolbar;

import com.example.soulf.mushroomiotfarm.MainActivity;
import com.example.soulf.mushroomiotfarm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AutoFragment extends Fragment{
    private  String tempString, humidityString, tempString1, humidityString1;
    private  EditText tempEditText, humiEditText ,tempEditText1 ,humiEditText1;
    private ProgressDialog progressDialog;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        Show View
        showView();

//Create Toolbar

        android.support.v7.widget.Toolbar toolbar = getView().findViewById(R.id.toolbarAuto);
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

    }//Main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemUpdate) {
            updateNewValueToFirebase();
        }

//        Next
        if (item.getItemId() == R.id.itemNext) {
            nextToGragph();
        }

//        Sign Out
        if (item.getItemId() == R.id.itemSignOut){
            mySignOut();
        }

        return super.onOptionsItemSelected(item);
    }

    private void nextToGragph() {

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentMainFragment, new GraphAutoFragment())
                .addToBackStack(null)
                .commit();
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


    private void updateNewValueToFirebase() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Process Upload Value to Firebase ... ");
        progressDialog.show();

        tempString = tempEditText.getText().toString().trim();
        humidityString = humiEditText.getText().toString().trim();
        //more
        tempString1 = tempEditText1.getText().toString().trim();
        humidityString1 = humiEditText1.getText().toString().trim();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> stringObjectMap = new HashMap<>();
                stringObjectMap.put("Temp_Low", tempString);
                stringObjectMap.put("Humidity_Low", humidityString);
                //more
                stringObjectMap.put("Temp_High", tempString1);
                stringObjectMap.put("Humidity_High", humidityString1);
                databaseReference.updateChildren(stringObjectMap);
                progressDialog.dismiss();
                nextToGragph();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_auto, menu);
    }

    private void showView() {
        tempEditText =getView().findViewById(R.id.edtTempl);
        humiEditText  = getView().findViewById(R.id.edtHumidityl);
        //more
        tempEditText1 =getView().findViewById(R.id.edtTemph);
        humiEditText1  = getView().findViewById(R.id.edtHumidityh);

        FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                tempString = String.valueOf(map.get("Temp_Low"));
                humidityString = String.valueOf(map.get("Humidity_Low"));
                //more
                tempString1 = String.valueOf(map.get("Temp_High"));
                humidityString1 = String.valueOf(map.get("Humidity_High"));

                tempEditText.setText(tempString);
                humiEditText.setText(humidityString);
                //more
                tempEditText1.setText(tempString1);
                humiEditText1.setText(humidityString1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auto, container, false);

        return view;
    }
}
