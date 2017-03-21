package com.example.zadmin.relaxia.Engine;

/**
 * Created by zAdmin on 21/3/2017.
 */
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import com.example.zadmin.relaxia.Common.Shared;
import com.example.zadmin.relaxia.Fragments.MemoryGameFragments.DifficultySelectFragment;
import com.example.zadmin.relaxia.Fragments.MemoryGameFragments.MemoryGameFragment;
import com.example.zadmin.relaxia.Fragments.MemoryGameFragments.MenuFragment;
import com.example.zadmin.relaxia.Fragments.MemoryGameFragments.ThemeSelectFragment;
import com.example.zadmin.relaxia.R;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.ResetBackgroundEvent;


public class ScreenController {

    private static ScreenController mInstance = null;
    private static List<Screen> openedScreens = new ArrayList<Screen>();
    private FragmentManager mFragmentManager;

    private ScreenController() {
    }

    public static ScreenController getInstance() {
        if (mInstance == null) {
            mInstance = new ScreenController();
        }
        return mInstance;
    }

    public static enum Screen {
        MENU,
        MEMORYGAME,
        CONNECTDOTS,
        PIC2WORDS,
        DIFFICULTY,
        THEME_SELECT
    }


    public static Screen getLastScreen() {
        return openedScreens.get(openedScreens.size() - 1);
    }

    public void openScreen(Screen screen) {
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
            if ((screen == Screen.THEME_SELECT || screen == Screen.MENU) &&
                    (screenToRemove == Screen.DIFFICULTY || screenToRemove == Screen.MEMORYGAME)) {
                Shared.eventBus.notify(new ResetBackgroundEvent());
            }
            return false;
        }
        return true;
    }

    private Fragment getFragment(Screen screen) {
        switch (screen) {
            case MENU:
                return new MenuFragment();
            case DIFFICULTY:
                return new DifficultySelectFragment();
            case MEMORYGAME:
                return new MemoryGameFragment();
            case CONNECTDOTS:
                //return new ConnectDotsFragment();
            case PIC2WORDS:
                //return new Pic2WordsFragment();
            case THEME_SELECT:
                return new ThemeSelectFragment();
            default:
                break;
        }
        return null;
    }
}