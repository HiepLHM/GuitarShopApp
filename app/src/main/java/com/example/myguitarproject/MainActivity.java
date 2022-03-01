package com.example.myguitarproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myguitarproject.DataLocal.DataLocal;
import com.example.myguitarproject.fragment.ViewPager2Adapter;
import com.example.myguitarproject.orderstatus.OrderStatus;
import com.example.myguitarproject.user.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private CircleImageView imgAvatarNav;
    private TextView tvUsernameNav, tvEmailNav;
    private ViewPager2Adapter viewPager2Adapter;
    private Button btnManager;
    private static int mCurrentPage = 0;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_POSIBILITY = 1;
    private static final int FRAGMENT_CART = 2;
    private static final int FRAGMENT_SEARCH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMain();

        //setting toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        List<User> userList = DataLocal.getInstance(this).localDAO().getListUserLocal();
        Glide.with(this).load(userList.get(0).getAvatar()).into(imgAvatarNav);
        tvUsernameNav.setText(userList.get(0).getUsername());
        tvEmailNav.setText(userList.get(0).getEmail());
        String role = userList.get(0).getRoleAccount();
        //Log.d("abh", role);

        if (role.equals("user")) {
            btnManager.setVisibility(View.INVISIBLE);
        }

        btnManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAdminManager();
            }
        });

        //change fragment when click to navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    openHomeFragment();
                    bottomNavigationView.getMenu().findItem(R.id.menuHome).setChecked(true);
                } else if (id == R.id.nav_posibility) {
                    openPossibilityFragment();
                    bottomNavigationView.getMenu().findItem(R.id.menuPosibility).setChecked(true);
                } else if (id == R.id.nav_cart) {
                    openCartFragment();
                    bottomNavigationView.getMenu().findItem(R.id.menuCart).setChecked(true);
                } else if (id == R.id.logout) {
                    handlerLogout();
                } else if (id == R.id.setting) {
                    userProfile();
                } else if (id == R.id.orderstatus) {
                    handlerOrderStatus();
                } else if (id == R.id.nav_search) {
                    openSearchFragment();
                    bottomNavigationView.getMenu().findItem(R.id.menuSearch).setChecked(true);
                }
                return true;
            }
        });

        viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);

        //set change fragment to change icon bottomNavigation and NavView
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mCurrentPage = FRAGMENT_HOME;
                        bottomNavigationView.getMenu().findItem(R.id.menuHome).setChecked(true);
                        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        mCurrentPage = FRAGMENT_POSIBILITY;
                        bottomNavigationView.getMenu().findItem(R.id.menuPosibility).setChecked(true);
                        navigationView.getMenu().findItem(R.id.nav_posibility).setChecked(true);
                        break;
                    case 2:
                        mCurrentPage = FRAGMENT_CART;
                        bottomNavigationView.getMenu().findItem(R.id.menuCart).setChecked(true);
                        navigationView.getMenu().findItem(R.id.nav_cart).setChecked(true);
                        break;

                }
            }
        });

        //change fragment when click icon bottomNavigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menuHome) {
                    openHomeFragment();
                    navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                } else if (id == R.id.menuPosibility) {
                    openPossibilityFragment();
                    navigationView.getMenu().findItem(R.id.nav_posibility).setChecked(true);
                } else if (id == R.id.menuCart) {
                    openCartFragment();
                    navigationView.getMenu().findItem(R.id.nav_cart).setChecked(true);
                } else if (id == R.id.menuSearch) {
                    openSearchFragment();
                    navigationView.getMenu().findItem(R.id.nav_search).setChecked(true);
                }
                return true;
            }
        });
    }

    private void gotoAdminManager() {
        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
        finish();
    }

    private void openSearchFragment() {
        if (mCurrentPage != FRAGMENT_SEARCH) {
            viewPager2.setCurrentItem(3);
            mCurrentPage = FRAGMENT_SEARCH;
        }
    }

    private void handlerOrderStatus() {
        Intent gotoOrderStatus = new Intent(this, OrderStatusActivity.class);
        startActivity(gotoOrderStatus);
        finish();
    }

    private void userProfile() {
        Intent gotoUserProfile = new Intent(this, UserProfile.class);
        startActivity(gotoUserProfile);
    }

    private void handlerLogout() {
        DataLocal.getInstance(this).localDAO().deleteAllUser();
        Intent gotoLoginActivity = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(gotoLoginActivity);
    }

    private void openCartFragment() {
        if (mCurrentPage != FRAGMENT_CART) {
            viewPager2.setCurrentItem(2);
            mCurrentPage = FRAGMENT_CART;
        }
    }

    private void openPossibilityFragment() {
        if (mCurrentPage != FRAGMENT_POSIBILITY) {
            viewPager2.setCurrentItem(1);
            mCurrentPage = FRAGMENT_POSIBILITY;
        }
    }

    private void openHomeFragment() {
        if (mCurrentPage != FRAGMENT_HOME) {
            viewPager2.setCurrentItem(0);
            mCurrentPage = FRAGMENT_HOME;
        }
    }


    private void initMain() {
        drawerLayout = findViewById(R.id.drawableLayout);
        toolbar = findViewById(R.id.toolBar);
        viewPager2 = findViewById(R.id.viewPager2);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        navigationView = findViewById(R.id.navigationView);
        View view = navigationView.getHeaderView(0);
        imgAvatarNav = view.findViewById(R.id.imgAvatarNav);
        tvUsernameNav = view.findViewById(R.id.tvUsernameNav);
        tvEmailNav = view.findViewById(R.id.tvEmailNav);
        btnManager = view.findViewById(R.id.btnManager);
    }
}