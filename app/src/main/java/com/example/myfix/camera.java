package com.example.myfix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfix.ml.Model4;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import kotlin.jvm.internal.Intrinsics;

public class camera extends AppCompatActivity {

    Button selectBtn, predictBtn, captureBtn;
    TextView result;
    ImageView imageView;
    Bitmap bitmap;
    public int imgSize = 224;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

//        get permission
        getPermission();

        selectBtn = findViewById(R.id.selectBtn);
        predictBtn = findViewById(R.id.predictBtn);
        captureBtn = findViewById(R.id.captureBtn);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 12);
            }
        });

        predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                model4.tflite
                try {
                    Model4 model = Model4.newInstance(camera.this);
                    Intrinsics.checkNotNullExpressionValue(model, "Model4.newInstance(camera.this)");
//                    Model4 model = var10000;
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                    Intrinsics.checkNotNullExpressionValue(inputFeature0, "TensorBuffer.createFixed…24, 3), DataType.FLOAT32)");
//                    TensorBuffer inputFeature0 = var24;
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imgSize * imgSize * 3);
                    byteBuffer.order(ByteOrder.nativeOrder());
                    int[] intValues = new int[imgSize * imgSize];
                    bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
                    int pixel = 0;
                    int var7 = 0;

                    int maxPos;
                    for(int var8 = imgSize; var7 < var8; ++var7) {
                        int var9 = 0;

                        for(maxPos = imgSize; var9 < maxPos; ++var9) {
                            int val = intValues[pixel++];
                            byteBuffer.putFloat((float)(val >> 16 & 255) * 0.003921569F);
                            byteBuffer.putFloat((float)(val >> 8 & 255) * 0.003921569F);
                            byteBuffer.putFloat((float)(val & 255) * 0.003921569F);
                        }
                    }

                    inputFeature0.loadBuffer(byteBuffer);
                    Model4.Outputs outputs = model.process(inputFeature0);
                    Intrinsics.checkNotNullExpressionValue(outputs, "model.process(inputFeature0)");
//                    Outputs outputs = var25;
                    inputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                    Intrinsics.checkNotNullExpressionValue(inputFeature0, "outputs.outputFeature0AsTensorBuffer");
                    TensorBuffer outputFeature0 = inputFeature0;
                    float[] var26 = outputFeature0.getFloatArray();
                    Intrinsics.checkNotNullExpressionValue(var26, "outputFeature0.floatArray");
                    float[] confidences = var26;
                    maxPos = 0;
                    float maxConfidence = -10.0F;
                    int i = 0;

                    for(int var13 = confidences.length; i < var13; ++i) {
                        if (confidences[i] > maxConfidence) {
                            maxConfidence = confidences[i];
                            maxPos = i;
                        }
                    }

                    float petc = confidences[0];
                    float hdpec = confidences[1];
                    float otherc = confidences[2];
                    Log.d("confidences", "PET: " + petc);
                    Log.d("confidences", "HDPE: " + hdpec);
                    Log.d("confidences", "other: " + otherc);
                    Log.d("confidences", "maxcon: " + maxConfidence);
                    Log.d("confidences", "maxpos: " + maxPos);
                    String[] classes = new String[]{"PET", "HDPE", "Other"};
                    String class_ = classes[maxPos];
                    Log.d("confidences", "CLASS: " + class_);
                    model.close();
                    result.setText(classes[maxPos]);
                } catch (IOException e) {
//                    return "";
                }
            }
        });
    }

    int getMax(float[] arr){
        int max = 0;
        for (int i = 0; i<arr.length; i++){
            if (arr[i] > arr[max]) max = i;
        }
        return max;
    }

    void getPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(camera.this, new String[]{Manifest.permission.CAMERA}, 11);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==11){
            if (grantResults.length>0){
                if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10){
            if (data!=null){
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode==12){
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}