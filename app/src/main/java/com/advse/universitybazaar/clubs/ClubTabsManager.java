package com.advse.universitybazaar.clubs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by shrey on 2/27/2018.
 */

public class ClubTabsManager extends FragmentStatePagerAdapter {

    int tabCount;

    public ClubTabsManager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: AllClubsFragment allClubsTab = new AllClubsFragment();
                    return allClubsTab;

            case 1: MemberClubFragment memberClubTab = new MemberClubFragment();
                    return memberClubTab;

            case 2: OwnerClubFragment ownerClubTab = new OwnerClubFragment();
                    return ownerClubTab;

            default:return null;
        }

    }

    @Override
    public int getCount() {

        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Not a Member";

            case 1: return "Member";

            case 2: return "Owned";
        }
        return null;
    }
}
