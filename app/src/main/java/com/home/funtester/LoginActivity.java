package com.home.funtester;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText ed_a;
    private EditText ed_p;
    private CheckBox cb_r;
    private Boolean account_proset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findviews();


    }

    private void findviews() {
        ed_a = findViewById(R.id.ed_account);
        ed_p = findViewById(R.id.ed_password);
        cb_r = findViewById(R.id.cb_remember);
        account_proset = getSharedPreferences("funActivity",MODE_PRIVATE).getBoolean("account_result",false);
        ed_a.setText(getSharedPreferences("funActivity",MODE_PRIVATE).getString("account",""));
        cb_r.setChecked(getSharedPreferences("funActivity",MODE_PRIVATE).getBoolean("account_result",false));

        cb_r.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedPreferences("funActivity",MODE_PRIVATE).edit().putBoolean("account_result",isChecked).commit();
                account_proset = getSharedPreferences("funActivity",MODE_PRIVATE).getBoolean("account_result",false);
            }
        });
    }

    public void  login(View view){
        String s_a=ed_a.getText().toString();
        String s_p=ed_p.getText().toString();

        FirebaseDatabase.getInstance().getReference("users").child(s_a).child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot snapshot) {
                        if(snapshot.getValue()!=null)
                        {
                            String password=snapshot.getValue().toString();
                            if(password.equals(s_p)){
                                if(account_proset){
                                    getSharedPreferences("funActivity",MODE_PRIVATE).edit().putString("account",s_a).commit();
                                }else {
                                    getSharedPreferences("funActivity",MODE_PRIVATE).edit().putString("account","").commit();
                                }
                                MainActivity.LOGIN_OK=50;
                                finish();
                                Toast.makeText(LoginActivity.this,"登入成功",Toast.LENGTH_LONG).show();
                            }else {
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("Error")
                                        .setMessage("The account password you entered is wrong")
                                        .setPositiveButton("ok",null)
                                        .show();
                                ed_p.setText("");
                            }
                        }
                        else
                        {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Error")
                                    .setMessage("The account password you entered is wrong")
                                    .setPositiveButton("ok",null)
                                    .show();
                            ed_a.setText("");
                            ed_p.setText("");
                        }

                    }

                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });


    }
}