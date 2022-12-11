package com.example.myfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    public String Username,Password;
    private DatabaseReference DB;
    private EditText getUsername, getPassword;
    private String KEY_NAME = "NAMA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DB = FirebaseDatabase.getInstance().getReference("User");

        getUsername = findViewById(R.id.username);
        getPassword = findViewById(R.id.password);


        Button bLogin = findViewById(R.id.btnLogin);
        bLogin.setOnClickListener(next);

        Button signUp = findViewById(R.id.btnRegister);
        signUp.setOnClickListener(next);
    }



    View.OnClickListener next = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            switch(view.getId()){
                case R.id.btnLogin:
                    simpan(Username, Password);
                    break;
                case R.id.btnRegister:
                    Intent signUp = new Intent(getApplicationContext(), register.class);
                    startActivity(signUp);
                    break;
            }
        }
    };

    private boolean validateUsername(String Username){
        if(Username.isEmpty()){
            getUsername.setError("Please enter email!");
            getUsername.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }


    private boolean validatePassword(String Password){
        if(Password.isEmpty()){
            getPassword.setError("Please enter password!");;
            getPassword.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }

    private void simpan(String Username, String Password){

        Username = getUsername.getText().toString();
        Password = getPassword.getText().toString();

        if(!validateUsername(Username) | !validatePassword(Password)){
            return;
        }
        String temp_pass = Password;
        String temp_uname = Username;
        DB.child(Username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(user == null){
                    Toast.makeText(getApplicationContext(), "User doesn't exists", Toast.LENGTH_SHORT).show();
                }else{
                    if(user.getPassword().equals(temp_pass)){
                        Intent intent = new Intent(getApplicationContext(), dashboard.class);
                        intent.putExtra(KEY_NAME, temp_uname);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Password wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}