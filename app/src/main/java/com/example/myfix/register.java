package com.example.myfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

public class register extends AppCompatActivity {

    public String addUsername, addEmail, addPassword, point;
    private EditText getUname, getPass, getEmail;
    private DatabaseReference DatabaseReference;
    private FirebaseDatabase FirebaseInstance;
    private DatabaseReference DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseInstance = FirebaseDatabase.getInstance();
        DB = FirebaseDatabase.getInstance().getReference();
        DatabaseReference = FirebaseInstance.getReference("User");

        getUname = findViewById(R.id.addUsername);
        getEmail    = findViewById(R.id.addEmail);
        getPass = findViewById(R.id.addPassword);

        Button continueSignUp = findViewById(R.id.btnRegis);
        continueSignUp.setOnClickListener(selanjutnya);
    }

    View.OnClickListener selanjutnya = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            pushSignUp(addUsername, addEmail, addPassword);
        }
    };

    private void goToSignIn(User data){
        Intent toSignIn = new Intent(getApplicationContext(), com.example.myfix.login.class).putExtra("data", (Parcelable) data);
        startActivity(toSignIn);
    };

    private boolean validateUsername(String addUsername){
        if(addUsername.isEmpty()){
            getUname.setError("Silahkan masukkan username anda!");;
            getUname.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    private boolean validateEmail(String addEmail){

        if(addEmail.isEmpty()){
            getEmail.setError("Silahkan masukkan email anda!");;
            getEmail.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    private boolean validatePassword(String addPassword){
        if(addPassword.isEmpty()){
            getPass.setError("Silahkan masukkan password anda!");;
            getPass.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    private void pushSignUp(String addUsername, String addEmail, String addPassword){
        addUsername = getUname.getText().toString();
        addEmail    = getEmail.getText().toString();
        addPassword = getPass.getText().toString();
        point = "0";

        if(!validateUsername(addUsername) | !validateEmail(addEmail) | !validatePassword(addPassword)){
            return;
        }
        simpanData(addUsername, addEmail, addPassword, point);
    }

    private void simpanData(String addUsername, String addEmail, String addPassword, String point) {
        User user = new User(addUsername, addEmail, addPassword, point);

        if(addUsername != null){
            checkingUsername(addUsername, user);
        }
    }

    private void checkingUsername(String addUsername, User data) {
        DatabaseReference.child(addUsername).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(user == null){
                    DatabaseReference.child(addUsername).setValue(data);
                    goToSignIn(data);
                }else{
                    Toast.makeText(getApplicationContext(), "User exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    ;
}