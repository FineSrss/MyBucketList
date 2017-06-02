package bibucketlist.com.mybucketlist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import bibucketlist.com.mybucketlist.fragment.CompleteFragment;
import bibucketlist.com.mybucketlist.fragment.ProgressFragment;

/**
 * Created by pineone on 2017-04-25.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                ProgressFragment progressFragment = new ProgressFragment();
                return progressFragment;
            case 1:
                CompleteFragment completeFragment = new CompleteFragment();
                return completeFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
