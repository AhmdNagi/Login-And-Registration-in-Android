package com.nagi.socyle.socyleapp.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nagi.socyle.socyleapp.Fragment.Login;
import com.nagi.socyle.socyleapp.Fragment.Register;
import com.nagi.socyle.socyleapp.R;

public class MainActivity extends AppCompatActivity {

    String userChoose;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() !=null) {
            userChoose = (String) getIntent().getExtras().getSerializable("UserChoose");
        }
        if (userChoose.equals("login")){
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new Login()).commit();
        }else if (userChoose.equals("register")){
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new Register()).commit();
        }
    }

}
