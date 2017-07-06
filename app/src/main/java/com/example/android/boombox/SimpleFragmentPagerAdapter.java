package com.example.android.boombox;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {



    //private String tabTitles[] = new String[] { "Albums", "Songs", "Artists","Playlists" };
    private Context mContext;



    public SimpleFragmentPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AlbumsFragment();
        } else if (position == 1) {
            return new SongsFragment();
        } else if (position == 2) {
            return new ArtistsFragment();
        } else {
            return new PlaylistsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_albums);
        } else if (position == 1) {
            return mContext.getString(R.string.category_songs);
        } else if (position == 2) {
            return mContext.getString(R.string.category_artists);
        } else {
            return mContext.getString(R.string.category_playlists);
        }
    }


}
