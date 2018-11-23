package com.example.ewd.diagram.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ewd.diagram.R;
import com.example.ewd.diagram.utils.CustomViewPager;
import com.example.ewd.diagram.utils.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    public BottomNavigationView navigation;

    @BindView(R.id.viewpager)
    public CustomViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;

    private String token;
    private String userId;
    private String userType;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    viewPager.setCurrentItem(0);

                    return true;
                case R.id.navigation_timeline:

                    viewPager.setCurrentItem(1);

                    return true;
                case R.id.navigation_profile:

                    viewPager.setCurrentItem(2);

                    return true;
                case R.id.navigation_post:

                    viewPager.setCurrentItem(3);

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        userId = intent.getStringExtra("userId");
        userType = intent.getStringExtra("userType");

        setupViewPagerAdapter();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    /**
     * Method that sets up ViewPagerAdapter
     */
    private void setupViewPagerAdapter() {
        viewPagerAdapter = new ViewPagerAdapter(NavigationActivity.this.getSupportFragmentManager());

        //Creating instances of all fragments
        FeedFragment feedFragment = FeedFragment.newInstance(userId, token);
        TimelineFragment timelineFragment = TimelineFragment.newInstance(userId, token);
        UserFragment userFragment = UserFragment.newInstance(userId, token);
        DoctorFragment doctorFragment = DoctorFragment.newInstance(userId, token);
        AddPostFragment addPostFragment = AddPostFragment.newInstance(userId, token);


        //Add all fragments to viewpager adapter
        viewPagerAdapter.addFragment(feedFragment, "Feed");
        viewPagerAdapter.addFragment(timelineFragment, "Timeline");

        if (userType.equals("patient"))
            viewPagerAdapter.addFragment(userFragment, "User");
        else if (userType.equals("doctor"))
            viewPagerAdapter.addFragment(doctorFragment, "User");

        viewPagerAdapter.addFragment(addPostFragment, "Add post");

        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(viewPagerAdapter);

    }
}
