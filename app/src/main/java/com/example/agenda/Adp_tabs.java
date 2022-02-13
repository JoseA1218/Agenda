package com.example.agenda;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Adp_tabs extends FragmentStateAdapter {


    public Adp_tabs(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public Adp_tabs(@NonNull Fragment fragment) {
        super(fragment);
    }

    public Adp_tabs(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                Modulos m = new Modulos();
                return m;
            case 1:
                Eventos e = new Eventos();
                return  e;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public CharSequence titulotab(int pos){
        switch (pos){
            case 0:
                return "Modulos";
            case 1:
                return "Eventos";
            default:
                return "";
        }
    }

}
