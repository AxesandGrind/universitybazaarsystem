package com.advse.universitybazaar.bean;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import java.lang.reflect.Field;

// https://stackoverflow.com/questions/40176244/how-to-disable-bottomnavigationview-shift-mode
// All thanks to the link above. It helped disable the shift mode and also the checked item hell
public class BottomNavigationShiftHelper {

    private final static String TAG = "DEBUG_BOTTOM_NAV_UTIL";

    static void removeShiftMode(BottomNavigationView view)
    {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try
        {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++)
            {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(false); // <--- This line changed
                item.setCheckable(false);  // <-- This line was added
            }
        }
        catch (NoSuchFieldException e)
        {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        }
        catch (IllegalAccessException e)
        {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }

    }
}
