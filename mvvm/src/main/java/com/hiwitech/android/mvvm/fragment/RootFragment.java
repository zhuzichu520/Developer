package com.hiwitech.android.mvvm.fragment;

import android.os.Bundle;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import dagger.android.support.DaggerFragment;

public abstract class RootFragment extends DaggerFragment implements OnNewIntent {


    public void open(@NonNull RootFragment fragment) {
        getRoot().manager.addFragment(this, fragment, null);
    }


    public void open(@NonNull RootFragment fragment, Bundle bundle) {
        getRoot().manager.addFragment(this, fragment, bundle);
    }

    public void open(@NonNull RootFragment fragment, Bundle bundle, int stackMode) {
        getRoot().manager.addFragment(this, fragment, bundle, stackMode);
    }

    public void dialogFragment(Fragment to) {
        getRoot().manager.dialogFragment(to);
    }

    public void setDialogAnim(@AnimRes int dialog_in, @AnimRes int dialog_out) {
        getRoot().manager.setDialogAnim(dialog_in, dialog_out);
    }

    public void close() {
        getRoot().manager.close(this);
    }

    public void close(RootFragment fragment) {
        getRoot().manager.close(fragment);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            onNowHidden();
        } else {
            onNextShow();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void onNowHidden() {

    }

    private void onNextShow() {

    }

    public RootActivity getRoot() {
        FragmentActivity activity = getActivity();
        if (activity instanceof RootActivity) {
            return (RootActivity) activity;
        } else {
            throw new ClassCastException("this activity mast be extends RootActivity");
        }
    }

    @Override
    public void onNewIntent() {
    }

}