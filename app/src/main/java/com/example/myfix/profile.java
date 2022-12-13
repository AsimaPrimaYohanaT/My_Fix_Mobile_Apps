package com.example.myfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class profile extends AppCompatActivity {

    private String nama,email;
    private EditText getPoint,input_email;
    private String KEY_NAME = "NAMA";
    private DatabaseReference DB;
    TextView txtHello1, txtPointku, txtUsername, txtEmail, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DB = FirebaseDatabase.getInstance().getReference("User");

        txtHello1 = (TextView) findViewById(R.id.txtHello);
        txtUsername = (TextView) findViewById(R.id.editUsername);
        txtEmail = (TextView) findViewById(R.id.editEmail);
        txtPointku=(TextView) findViewById(R.id.jumlahPointku);
        Bundle extras = getIntent().getExtras();
        nama = extras.getString(KEY_NAME);
        txtHello1.setText(" nama ");
        setter(nama);
        setEmail(nama);
//        setPassword(nama);


        getPoint = findViewById(R.id.jumlahPointTambah);
        input_email = findViewById(R.id.edittextusername);

        ImageButton ubah = (ImageButton) findViewById(R.id.btnEditUsername);
        ubah.setOnClickListener(selanjutnya);

        ImageButton ubah1 = (ImageButton) findViewById(R.id.btnEditEmail);
        ubah1.setOnClickListener(selanjutnya);

        ImageButton ubah2 = (ImageButton) findViewById(R.id.btnEditPassword);
        ubah2.setOnClickListener(selanjutnya);

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


    private void setEmail(String nama){

        DB.child(nama).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(user == null){
                    Toast.makeText(getApplicationContext(), "User tidak ditemukan", Toast.LENGTH_SHORT).show();
                }else{
                    txtEmail.setText(user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void setPassword(String nama){
//
//        DB.child(nama).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//
//                if(user == null){
//                    Toast.makeText(getApplicationContext(), "User tidak ditemukan", Toast.LENGTH_SHORT).show();
//                }else{
//                    txtPassword.setText(user.getUsername());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    View.OnClickListener selanjutnya = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnEditEmail:
                    goEditEmail();
                    break;
//                case R.id.btnEditPassword:
//                    goEditPassword(nama, point);
//                    break;
            }
        }
    };

    private void goEditEmail(){
        LayoutInflater li = LayoutInflater.from(this);
        View inputnya = li.inflate(R.layout.input_username, null);

        AlertDialog.Builder dialognya = new AlertDialog.Builder(this);
        dialognya.setView(inputnya);

        input_email = (EditText) inputnya.findViewById(R.id.edittextusername);

        dialognya.setCancelable(false)
                .setPositiveButton("Ok", oknya)
                .setNegativeButton("Batal", oknya);
        dialognya.show();
    }

    DialogInterface.OnClickListener oknya = new DialogInterface.OnClickListener() {
        @Override
        public void onClick (DialogInterface dialog, int which){
            switch(which){
                case -1: goEmail(nama, email); break;
                case -2: break;
            }
        }
    };

    private void goEmail(String nama, String email) {

        email = input_email.getText().toString();
        String temp_email = email;
        DB.child(nama).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //iFbmr = Float.parseFloat(String.valueOf(ibmr));

                snapshot.getRef().child("email").setValue(temp_email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    };

    private void goProfile(String nama, String point) {

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