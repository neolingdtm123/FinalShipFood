package com.leekien.shipfoodfinal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.leekien.shipfoodfinal.history.HistoryFragment;
import com.leekien.shipfoodfinal.home.HomeFragment;
import com.leekien.shipfoodfinal.logout.LogOutFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                LogOutFragment home = new LogOutFragment();
                return home;
            case 1:
                HistoryFragment historyFragment = new HistoryFragment();
                return historyFragment;
            default:
                return null;
        }
    }
}