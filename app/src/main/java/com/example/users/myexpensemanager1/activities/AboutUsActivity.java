package com.example.users.myexpensemanager1.activities;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.example.users.myexpensemanager1.R;
import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
//        AboutView view = AboutBuilder.with(this)
//                .setPhoto(R.mipmap.profile_picture)
//                .setCover(R.mipmap.profile_cover)
//                .setName("Your Full Name")
//                .setSubTitle("Mobile Developer")
//                .setBrief("I'm warmed of mobile technologies. Ideas maker, curious and nature lover.")
//                .setAppIcon(R.mipmap.ic_launcher)
//                .setAppName(R.string.app_name)
//                .addGooglePlayStoreLink("8002078663318221363")
//                .addGitHubLink("user")
//                .addFacebookLink("user")
//                .addFiveStarsAction()
//                .setVersionNameAsAppSubTitle()
//                .addShareAction(R.string.app_name)
//                .setWrapScrollView(true)
//                .setLinksAnimated(true)
//                .setShowAsCard(true)
//                .build();
//
//        addContentView(view, new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT ));
//
        Toolbar toolbar = findViewById(R.id.main_application_toolbar);
        setSupportActionBar(toolbar);
//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About Us");
    }
}
