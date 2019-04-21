package com.example.zadmin.relaxia.Engine;

/**
 * Created by zAdmin on 21/3/2017.
 */
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

import com.example.zadmin.relaxia.Common.Shared;
import com.example.zadmin.relaxia.DataAnalysis.ViewDataFragment;
import com.example.zadmin.relaxia.Fragments.MemoryGameFragments.ConnectTheDotsFragments.CTDDifficultySelectFrag;
import com.example.zadmin.relaxia.Fragments.MemoryGameFragments.ConnectTheDotsFragments.CTDGameFragment;
import com.example.zadmin.relaxia.Fragments.MemoryGameFragments.ConnectTheDotsFragments.CTDMenuFragment;
import com.example.zadmin.relaxia.Fragments.MemoryGameFragments.DifficultySelectFragment;
import com.example.zadmin.relaxia.Fragments.MemoryGameFragments.MGMenuFragment;
import com.example.zadmin.relaxia.Fragments.MemoryGameFragments.MemoryGameFragment;
import com.example.zadmin.relaxia.Fragments.MemoryGameFragments.ThemeSelectFragment;
import com.example.zadmin.relaxia.MenuFragment;
import com.example.zadmin.relaxia.R;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.ResetBackgroundEvent;

import java.util.ArrayList;
import java.util.List;


public class ScreenController {

    private static final String TAG = "ScreenControllerClass";

    private static ScreenController mInstance = null;
    private static List<Screen> openedScreens = new ArrayList<Screen>();
    private FragmentManager mFragmentManager;

    private ScreenController() {
    }

    public static ScreenController getInstance() {
        Log.i(TAG, "get screenController Instance");
        if (mInstance == null) {
            mInstance = new ScreenController();
            Log.i(TAG, "creating new screenController");
        }
        return mInstance;
    }

    public static enum Screen {
        MAIN_MENU,
        MG_MENU,
        MEMORYGAME,
        DIFFICULTY,
        THEME_SELECT,
        CTD_MENU,
        CTD_DIFFICULTY,
        CTDGAME,
        PIC2WORDS,
        DATA_ANALYSIS,

    }


    public static Screen getLastScreen() {
        return openedScreens.get(openedScreens.size() - 1);
    }


    //TODO If you check the log, somehow multiple screens of the same fragment are being opened. FIND OUT WHY!
    public void openScreen(Screen screen) {
        Log.i(TAG, "Opening: " + screen);
        mFragmentManager = Shared.activity.getFragmentManager();

        if (screen == Screen.MEMORYGAME && openedScreens.get(openedScreens.size() - 1) == Screen.MEMORYGAME) {
            openedScreens.remove(openedScreens.size() - 1);
        } else if (screen == Screen.DIFFICULTY && openedScreens.get(openedScreens.size() - 1) == Screen.MEMORYGAME) {
            openedScreens.remove(openedScreens.size() - 1);
            openedScreens.remove(openedScreens.size() - 1);
        }
        Fragment fragment = getFragment(screen);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        openedScreens.add(screen);
    }

    public boolean onBack() {
        if (openedScreens.size() > 0) {
            Screen screenToRemove = openedScreens.get(openedScreens.size() - 1);
            openedScreens.remove(openedScreens.size() - 1);
            if (openedScreens.size() == 0) {
                return true;
            }
            Screen screen = openedScreens.get(openedScreens.size() - 1);
            openedScreens.remove(openedScreens.size() - 1);
            openScreen(screen);
            if ((screen == Screen.THEME_SELECT || screen == Screen.MG_MENU) &&
                    (screenToRemove == Screen.DIFFICULTY || screenToRemove == Screen.MEMORYGAME)) {
                Shared.eventBus.notify(new ResetBackgroundEvent());
            }
            return false;
        }
        return true;
    }

    private Fragment getFragment(Screen screen) {
        switch (screen) {
            case MAIN_MENU:
                return new MenuFragment();
            case MG_MENU:
                return new MGMenuFragment();
            case DIFFICULTY:
                return new DifficultySelectFragment();
            case THEME_SELECT:
                return new ThemeSelectFragment();
            case MEMORYGAME:
                return new MemoryGameFragment();
            case CTD_MENU:
                return new CTDMenuFragment();
            case CTD_DIFFICULTY:
                return new CTDDifficultySelectFrag();
            case CTDGAME:
                return new CTDGameFragment();
            case DATA_ANALYSIS:
                return new ViewDataFragment();
            case PIC2WORDS:
                //return new Pic2WordsFragment();


            default:
                break;
        }
        return null;
    }
}