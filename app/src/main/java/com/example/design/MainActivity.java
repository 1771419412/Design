package com.example.design;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    // 控制ToolBar的变量
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private ImageView mIvPlaceholder;
    private TextView mLlTitleContainer;
    private FrameLayout mFlTitleContainer;
    private TextView mTvToolbarTitle;
    private Toolbar  mTbToolbar;
    private AppBarLayout mAblAppBar;
    private FloatingActionButton mFabSearch;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private CoordinatorLayout mCoordinatorLayout;
    private FloatingActionButton mFabSearch1;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
        snack();
    }

    private void snack() {
        mFabSearch1= (FloatingActionButton) findViewById(R.id.fab_search1);
        mFabSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mDrawerLayout, "SnackbarClicked", Snackbar.LENGTH_SHORT).setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "我是鞠婧祎", Toast.LENGTH_SHORT).show();
                    }
                }).setActionTextColor(Color.RED).show();
            }
        });

    }

    private void init() {
        mCoordinatorLayout= (CoordinatorLayout) findViewById(R.id.crdlayout);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drlayout);
        mTbToolbar= (Toolbar) findViewById(R.id.toolbar);
        mAblAppBar= (AppBarLayout) findViewById(R.id.appbarLayout);
        mIvPlaceholder= (ImageView) findViewById(R.id.ctl_image);
        mLlTitleContainer= (TextView) findViewById(R.id.linearLayout);
        mFlTitleContainer= (FrameLayout) findViewById(R.id.frameLayout);
        mTvToolbarTitle= (TextView) findViewById(R.id.tb_tv);

        mTbToolbar.setTitle("");

        //AppBar的监听
        mAblAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll=appBarLayout.getTotalScrollRange();
                float percentage=(float) Math.abs(verticalOffset)/(float) maxScroll;
                handleAlphaOnTitle(percentage);
                handleToolbarTitleVisibility(percentage);
            }
        });
        initParallaxValues();
        mFabSearch= (FloatingActionButton) findViewById(R.id.fab_search);
        mFabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mNavigationView= (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_item_home:

                        mCoordinatorLayout.setBackgroundColor(Color.RED);
                        break;

                    case R.id.navigation_item_blog:

                        mCoordinatorLayout.setBackgroundColor(Color.BLUE);
                        break;

                    case R.id.navigation_item_about:

                        mCoordinatorLayout.setBackgroundColor(Color.WHITE);
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;



            }
        });
    }

    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp
                = (CollapsingToolbarLayout.LayoutParams) mIvPlaceholder.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp
                = (CollapsingToolbarLayout.LayoutParams) mFlTitleContainer.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        mIvPlaceholder.setLayoutParams(petDetailsLp);
        mFlTitleContainer.setLayoutParams(petBackgroundLp);


    }

    private void handleToolbarTitleVisibility(float percentage) {
        if(percentage>=PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR){
            if(!mIsTheTitleVisible){
                startAlphaAnimation(mTvToolbarTitle,ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible=true;
            }
        }else {
            if(mIsTheTitleVisible){
                startAlphaAnimation(mTvToolbarTitle,ALPHA_ANIMATIONS_DURATION,View.INVISIBLE);
                mIsTheTitleVisible=false;
            }
        }

    }

    private void startAlphaAnimation(TextView tvToolbarTitle, int alphaAnimationsDuration, int visible) {
        AlphaAnimation alphaAnimation=(visible==View.VISIBLE)
        ? new AlphaAnimation(0f,1f)
        : new AlphaAnimation(1f,0f);

        alphaAnimation.setDuration(alphaAnimationsDuration);
        alphaAnimation.setFillAfter(true);
        tvToolbarTitle.startAnimation(alphaAnimation);
    }

    private void handleAlphaOnTitle(float percentage) {
        if(percentage>=PERCENTAGE_TO_HIDE_TITLE_DETAILS){
            if(mIsTheTitleContainerVisible){
                startAlphaAnimation(mLlTitleContainer,ALPHA_ANIMATIONS_DURATION,View.INVISIBLE);
                mIsTheTitleContainerVisible=false;
            }
        }else {
            if(!mIsTheTitleContainerVisible){
                startAlphaAnimation(mLlTitleContainer,ALPHA_ANIMATIONS_DURATION,View.VISIBLE);
                mIsTheTitleContainerVisible=true;
            }
        }

    }


}
