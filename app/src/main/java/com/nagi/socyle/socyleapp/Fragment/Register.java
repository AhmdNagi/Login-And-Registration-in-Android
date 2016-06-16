package com.nagi.socyle.socyleapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nagi.socyle.socyleapp.Activity.Home;
import com.nagi.socyle.socyleapp.DataBase.DatabaseHandler;
import com.nagi.socyle.socyleapp.ModelClass.User;
import com.nagi.socyle.socyleapp.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Fehoo on 6/15/2016.
 */
public class Register extends Fragment {

    EditText userName, email, password, phone;
    Button register;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    User user;
    DatabaseHandler db;

    public Register() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_register, container, false);

        userName = (EditText) v.findViewById(R.id.input_user);
        email = (EditText) v.findViewById(R.id.input_email);
        password = (EditText) v.findViewById(R.id.input_password);
        phone = (EditText) v.findViewById(R.id.input_phone);
        register = (Button) v.findViewById(R.id.btn_register);

        user = new User();
        db = new DatabaseHandler(getContext());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userName.getText().toString().isEmpty()) {
                    userName.setError("User Name is required !");
                } else if (email.getText().toString().isEmpty()) {
                    email.setError("E-mail is required !");
                } else if (!email.getText().toString().matches(EMAIL_PATTERN)) {
                    email.setError("invalid mail !");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Password is required !");
                } else if (phone.getText().toString().isEmpty()) {
                    phone.setError("Phone is required !");
                } else {
                    user.setUserName(userName.getText().toString());
                    user.setUserEmail(email.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setPhone(phone.getText().toString());
                    if (isNetworkAvailable()) {
                        new RegisterTask().execute();
                    } else {
                        Toast.makeText(getContext(),"Check Internet Connection" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class RegisterTask extends AsyncTask<Void, Void, String> {

        public RegisterTask() {
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            String response = null;
            try {
                URL url = new URL("http://socyle.com/intership/index.php/register");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", user.getUserName() + "")
                        .appendQueryParameter("email", user.getUserEmail() + "")
                        .appendQueryParameter("password", user.getPassword() + "")
                        .appendQueryParameter("phone", user.getPhone() + "");
                String query = builder.build().getEncodedQuery();
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();
                os.close();
                InputStream in = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                response = sb.toString();
                Log.v("debug register", response);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response.equals("0")) {
                Toast.makeText(getContext(), "Email Exist", Toast.LENGTH_SHORT).show();
            } else if (response.equals("1")){
                db.addContact(new User(user.getUserName(),user.getUserEmail(),user.getPassword(),user.getPhone()));
                startActivity(new Intent(getContext(), Home.class));
            }else{
                Toast.makeText(getContext(), "Error with server", Toast.LENGTH_SHORT).show();
            }
        }
    }
}