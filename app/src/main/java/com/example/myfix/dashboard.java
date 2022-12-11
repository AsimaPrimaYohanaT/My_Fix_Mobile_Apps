package com.example.myfix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class dashboard extends AppCompatActivity {
    TextView txtHello;
    private String nama;
    private String KEY_NAME = "NAMA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        txtHello = (TextView) findViewById(R.id.txtHello);

        Bundle extras = getIntent().getExtras();
        nama = extras.getString(KEY_NAME);
        txtHello.setText("Hello, " + nama + " !");

        Button bUpgradePoint = (Button) findViewById(R.id.btnTambahPoint);
        bUpgradePoint.setOnClickListener(go);

        Button bMaps = (Button) findViewById(R.id.btnmaps);
        bMaps.setOnClickListener(go);

        Button bScan = (Button) findViewById(R.id.btnCamera);
        bScan.setOnClickListener(go);
    }

    View.OnClickListener go = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnTambahPoint: bukaTambah(); break;
                case R.id.btnmaps:bukaMaps();break;
                case R.id.btnCamera:bukaCamera();break;
            }
        }

        public void bukaTambah(){
            Intent intent = new Intent(getApplicationContext(), addPoint.class);
            intent.putExtra(KEY_NAME, nama);
            startActivity(intent);
        }

        public void bukaMaps(){
            Intent intent = new Intent(getBaseContext(),maps.class);
            startActivityForResult(intent,0);
        }

        public void bukaCamera(){
            Intent intent = new Intent(getBaseContext(),camera.class);
            startActivityForResult(intent,0);
        }
    };
}