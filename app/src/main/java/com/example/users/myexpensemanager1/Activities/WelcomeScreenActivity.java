package com.example.users.myexpensemanager1.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.PageOptions;
import com.cleveroad.slidingtutorial.TransformItem;
import com.cleveroad.slidingtutorial.TutorialFragment;
import com.cleveroad.slidingtutorial.TutorialOptions;
import com.cleveroad.slidingtutorial.TutorialPageOptionsProvider;
import com.example.users.myexpensemanager1.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    TutorialOptions tutorialOptions;
    TutorialPageOptionsProvider tutorialPageOptionsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        int[] colors = new int[]{Color.RED, Color.BLUE};

        Log.d("EXPM_WelcomeScreen","color set");
         tutorialPageOptionsProvider = new TutorialPageOptionsProvider() {
            @NonNull
            @Override
            public PageOptions provide(int position) {
                @LayoutRes int pageLayoutResId;
                TransformItem[] tutorialItems;
                switch (position) {
                    case 0: {
                        pageLayoutResId = R.layout.fragment_page_first;
                        tutorialItems = new TransformItem[]{
                                TransformItem.create(R.id.graph_fall, Direction.LEFT_TO_RIGHT, 0.07f),
                        TransformItem.create(R.id.sad_girl_back, Direction.LEFT_TO_RIGHT, 0.5f)
                        };
                        break;
                    }
                    case 1: {
                        pageLayoutResId = R.layout.fragment_page_second;
                        tutorialItems = new TransformItem[]{
                                TransformItem.create(R.id.save_transactions, Direction.LEFT_TO_RIGHT, 0.07f),
                                TransformItem.create(R.id.save_earnings, Direction.LEFT_TO_RIGHT, 0.06f),
                                TransformItem.create(R.id.currency_converter, Direction.LEFT_TO_RIGHT, 0.05f),
                                TransformItem.create(R.id.split_bills, Direction.LEFT_TO_RIGHT, 0.07f),
                                TransformItem.create(R.id.take_bill_snaps, Direction.LEFT_TO_RIGHT, 0.09f),
                                TransformItem.create(R.id.set_reminders, Direction.LEFT_TO_RIGHT, 0.08f),
                                TransformItem.create(R.id.phone_screen, Direction.LEFT_TO_RIGHT, 0.05f)
                        };
                        break;
                    }
                    case 2: {
                        pageLayoutResId = R.layout.fragment_page_third;
                        tutorialItems = new TransformItem[]{
                                TransformItem.create(R.id.save_memo_screen, Direction.LEFT_TO_RIGHT, 0.12f),
                                TransformItem.create(R.id.save_bill_snap_screen, Direction.LEFT_TO_RIGHT, 0.10f),
                                TransformItem.create(R.id.back_circle, Direction.LEFT_TO_RIGHT, 0.05f),
                                TransformItem.create(R.id.notebook_girl_back, Direction.LEFT_TO_RIGHT, 0.12f)
                        };
                        break;
                    }
                    case 3: {
                        pageLayoutResId = R.layout.fragment_page_forth;
                        tutorialItems = new TransformItem[]{
                                TransformItem.create(R.id.back_graph_circle, Direction.LEFT_TO_RIGHT, 0.55f),
                                TransformItem.create(R.id.money_icon, Direction.LEFT_TO_RIGHT, 0.6f),
                                TransformItem.create(R.id.handshake_icon, Direction.LEFT_TO_RIGHT, 0.4f),
                                TransformItem.create(R.id.on_phone_icon, Direction.LEFT_TO_RIGHT, 0.35f),
                                TransformItem.create(R.id.toolbox_icon, Direction.LEFT_TO_RIGHT, 0.2f),
                                TransformItem.create(R.id.winking_girl_back, Direction.LEFT_TO_RIGHT, 0.5f)
                        };
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Unknown position: " + position);
                    }
                }
                return PageOptions.create(pageLayoutResId, position, tutorialItems);
            }
        };
        Log.d("EXPM_WelcomeScreen","transaction page provider object created");

        tutorialOptions = TutorialFragment.newTutorialOptionsBuilder(this)
                .setUseAutoRemoveTutorialFragment(true)
                .setUseInfiniteScroll(false)
                .setPagesCount(4)
                .setTutorialPageProvider(tutorialPageOptionsProvider)
                .build();


        final TutorialFragment tutorialFragment = TutorialFragment.newInstance(tutorialOptions);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, tutorialFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
