package com.nagi.socyle.socyleapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.nagi.socyle.socyleapp.R;

public class Start extends AppCompatActivity {

    Button login , register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("UserChoose", "login");
                Intent intent = new Intent(Start.this, MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("UserChoose", "register");
                Intent intent = new Intent(Start.this, MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
