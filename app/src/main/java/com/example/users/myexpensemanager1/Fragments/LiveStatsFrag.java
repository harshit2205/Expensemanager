package com.example.users.myexpensemanager1.Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.users.myexpensemanager1.R;

import java.io.File;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveStatsFrag extends BaseFragment {


    public LiveStatsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_live_stats, container, false);
        File imgFile = new File("/storage/emulated/0/file1514378313618.jpg");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageViewTouch myImage = (ImageViewTouch) view.findViewById(R.id.temp_ImageView);

            myImage.setImageBitmap(myBitmap);

        }else{
            Toast.makeText(getActivity(),"the image can't be load, might have been tampered!!",Toast.LENGTH_SHORT).show();
        }
        return view;

    }

}
