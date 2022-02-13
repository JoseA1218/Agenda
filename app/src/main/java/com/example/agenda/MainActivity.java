package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    TabLayout tabs;
    ViewPager2 vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs=(TabLayout) findViewById(R.id.tabs);
        vp=(ViewPager2) findViewById(R.id.vp);

        Adp_tabs adp_tabs = new Adp_tabs(this);
        vp.setAdapter(adp_tabs);
        new TabLayoutMediator(tabs,vp,
                (tab,position)->tab.setText(adp_tabs.titulotab(position))
        ).attach();

    }
}