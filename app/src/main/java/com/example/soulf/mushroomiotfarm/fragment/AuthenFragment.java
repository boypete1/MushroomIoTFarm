package com.example.soulf.mushroomiotfarm.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soulf.mushroomiotfarm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by soulf on 3/2/2018.
 */

public class AuthenFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        Login Controller
        loginController();

//        Check Status
        checkStatus();
    }  //Main Method

    private void checkStatus() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentMainFragment, new MainFragment())
                    .commit();
        }

    }

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText userEditText = getView().findViewById(R.id.editUser);
                EditText PasswordEdiText = getView().findViewById(R.id.editPassword);

                String userString = userEditText.getText().toString().trim();
                String passwordString = PasswordEdiText.getText().toString().trim();

                if (userString.isEmpty() || passwordString.isEmpty()) {
//                    Have space
                    Toast.makeText(getActivity(), "Please Fill All Blank", Toast.LENGTH_SHORT).show();
                } else {
//                    No Space
                    checkAuthen(userString, passwordString);
                }
            }
        });
    }

    private void checkAuthen(String userString, String passwordString) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(userString, passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            Success Authen
                            Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMainFragment, new MainFragment()).commit();
                        } else {
//                            UnSuccess Authen
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    } //checkAuthen

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authen, container, false);

        return view;
    }
}
