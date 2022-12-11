package com.example.myfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addPoint extends AppCompatActivity {

    private String nama,point;
    private EditText getPoint;
    private String KEY_NAME = "NAMA";
    private DatabaseReference DB;
    TextView txtHello, txtPointku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);

        DB = FirebaseDatabase.getInstance().getReference("User");

        txtHello = (TextView) findViewById(R.id.txtHello);
        Bundle extras = getIntent().getExtras();
        nama = extras.getString(KEY_NAME);
        txtHello.setText("Hello, " + nama + " !");

        txtPointku=(TextView) findViewById(R.id.jumlahPointku);
        setter(nama);

        getPoint = findViewById(R.id.jumlahPointTambah);

        Button add = (Button) findViewById(R.id.btnTambah);
        add.setOnClickListener(selanjutnya);

    }

    private void setter(String nama){

        DB.child(nama).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(user == null){
                    Toast.makeText(getApplicationContext(), "User tidak ditemukan", Toast.LENGTH_SHORT).show();
                }else{
                    txtPointku.setText(user.getPoint());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    View.OnClickListener selanjutnya = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnTambah:
                    tambah(nama, point);
                    break;
            }
        }
    };

    private void tambah(String nama, String point) {

        point = getPoint.getText().toString();
        String temp_point = point;
        DB.child(nama).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //iFbmr = Float.parseFloat(String.valueOf(ibmr));

                snapshot.getRef().child("point").setValue(temp_point);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    };
}