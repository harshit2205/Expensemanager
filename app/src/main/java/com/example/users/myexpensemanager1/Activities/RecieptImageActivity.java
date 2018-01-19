package com.example.users.myexpensemanager1.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.users.myexpensemanager1.R;

import java.io.File;

import ooo.oxo.library.widget.TouchImageView;


public class RecieptImageActivity extends AppCompatActivity {
    TouchImageView imageViewTouch;
    ImageView deleteReciept, backbutton;
    String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept_image);

        imageViewTouch = (TouchImageView) findViewById(R.id.reciept_Image);
        deleteReciept =(ImageView)findViewById(R.id.delete_reciept);
        backbutton = (ImageView)findViewById(R.id.back_button);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
        if(i.hasExtra("imagePath")){
            str = i.getStringExtra("imagePath");
        }



        imageViewTouch.setImageBitmap(getImage());

    }

    private Bitmap getImage(){
        File imgFile = new File(str);

        Bitmap myBitmap;

        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;

        }else{
            Toast.makeText(this,"the image can't be load, might have been tampered!!",Toast.LENGTH_SHORT).show();
            return null;
        }

    }
}
