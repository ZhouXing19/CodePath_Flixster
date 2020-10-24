package com.example.myinstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private EditText etDescrip;
    private Button btnCaptureImage;
    private ImageView ivPostImage;
    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDescrip = findViewById(R.id.etDescrip);
        btnCaptureImage = findViewById(R.id.btnCaptureImage);
        ivPostImage = findViewById(R.id.ivPic);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
}