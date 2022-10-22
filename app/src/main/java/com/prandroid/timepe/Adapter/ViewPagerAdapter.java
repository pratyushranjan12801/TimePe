package com.prandroid.timepe.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.prandroid.timepe.Fragment.*;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
         if(position==0)
             return new ChatFragment();
         else if(position==1)
             return new StatusFragment();
         else
             return new CallFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "Chats";
        else if(position==1)
            return "Status";
        else
            return "Calls";
    }
}
