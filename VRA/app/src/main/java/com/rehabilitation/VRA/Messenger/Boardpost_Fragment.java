package com.rehabilitation.VRA.Messenger;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.ProgressBar;

import com.example.darren.VRA.R;

import java.util.List;

public abstract class Boardpost_Fragment extends Fragment {

    Azure azure;

    /**
     * Progress spinner to use for table operations
     */
    ProgressBar progressbar;

    Fragment newFragment;

    public void add_boardpost(Type_SMS Topic){}

    public void remove_boardpost(Type_SMS Topic){}

    public void dismissProgressBar() {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (progressbar != null) progressbar.setVisibility(ProgressBar.GONE);
            }
        });
    }

    public void runProgressBar(){
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (progressbar != null) progressbar.setVisibility(ProgressBar.VISIBLE);
            }
        });
    }

    public void loadFragment_replies(Type_SMS currentItem, List<Type_SMS> reponses){

        newFragment = new Fragment_replies().newInstance(new Type_Boardpost(currentItem, reponses));
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction ft1 = fm1.beginTransaction();
        ft1.replace(R.id.content_layout, newFragment)
                .addToBackStack(null);
        ft1.commit();
    }
}
