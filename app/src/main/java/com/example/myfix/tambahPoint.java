package com.example.myfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tambahPoint extends AppCompatActivity {

    private String nama;
    private String KEY_NAME = "NAMA";
    private DatabaseReference DB;
    TextView txtHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_point);


        DB = FirebaseDatabase.getInstance().getReference("User");

        txtHello = (TextView) findViewById(R.id.txtHello);
        Bundle extras = getIntent().getExtras();
        nama = extras.getString(KEY_NAME);
        txtHello.setText("Hello, " + nama + " !");

        Button add = (Button) findViewById(R.id.btnTambahPoint);
        add.setOnClickListener(selanjutnya);

    }

    View.OnClickListener selanjutnya = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            switch(view.getId()){
                case R.id.btnTambahPoint:
                    Intent intent = new Intent(getApplicationContext(), dashboard.class);
                    startActivity(intent);
                    break;
            }
        }
    };

//    private void tambah(String nama, String jlhPoint) {
//
//        DB.child(nama).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //iFbmr = Float.parseFloat(String.valueOf(ibmr));
//
//                snapshot.getRef().child("point").setValue(jlhPoint);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    };


//
//    private void tambah(String Username, String jlhPoint){
//        Username = nama;
//        jlhPoint = getJumlahPoint.getText().toString();
//
//        String temp_name = Username;
//        DB.child(Username).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//
//                if(user == null){
//                    Toast.makeText(getApplicationContext(), "User doesn't exists", Toast.LENGTH_SHORT).show();
//                }else{
//                    if(user.getUsername().equals(temp_name)){
//                        txtPoint.setText(user.getPoint());
//                    }else{
//                        Toast.makeText(getApplicationContext(), "Password wrong", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}