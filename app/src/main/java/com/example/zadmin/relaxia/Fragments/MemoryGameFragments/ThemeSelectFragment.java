package com.example.zadmin.relaxia.Fragments.MemoryGameFragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.zadmin.relaxia.Common.Shared;
import com.example.zadmin.relaxia.Models.MemoryGame.Theme.Theme;
import com.example.zadmin.relaxia.Models.MemoryGame.Theme.Themes;
import com.example.zadmin.relaxia.R;
import com.example.zadmin.relaxia.events.MemoryGameEvents.ui.ThemeSelectedEvent;

import java.util.Locale;

public class ThemeSelectFragment extends Fragment{

    private static final String TAG = "ThemeSelectFragmentClass";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "create theme select fragment");

        View view = LayoutInflater.from(Shared.context).inflate(R.layout.theme_select_fragment, container, false);
        View animals = view.findViewById(R.id.theme_animals_container);
        View monsters = view.findViewById(R.id.theme_monsters_container);

        final Theme themeAnimals = Themes.createAnimalsTheme();
        setStars((ImageView) animals.findViewById(R.id.theme_animals), themeAnimals, "animals");
        final Theme themeMonsters = Themes.createMosterTheme();
        setStars((ImageView) monsters.findViewById(R.id.theme_monsters), themeMonsters, "monsters");

        animals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new ThemeSelectedEvent(themeAnimals));
            }
        });

        monsters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.eventBus.notify(new ThemeSelectedEvent(themeMonsters));
            }
        });

        /**
         * Imporove performance first!!!
         */
        animateShow(animals);
        animateShow(monsters);

        return view;
    }

    private void animateShow(View view) {
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.playTogether(animatorScaleX, animatorScaleY);
        animatorSet.setInterpolator(new DecelerateInterpolator(2));
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animatorSet.start();
    }

    private void setStars(ImageView imageView, Theme theme, String type) {
        int sum = 0;
        for (int difficulty = 1; difficulty <= 6; difficulty++) {
            //sum += Memory.getHighStars(theme.id, difficulty);
        }
        int num = sum / 6;
        if (num != 0) {
            String drawableResourceName = String.format(Locale.US, type + "_theme_star_%d", num);
            int drawableResourceId = Shared.context.getResources().getIdentifier(drawableResourceName, "drawable", Shared.context.getPackageName());
            imageView.setImageResource(drawableResourceId);
        }
    }
}
