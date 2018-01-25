package com.example.androidstarter.base.activities;

import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.example.androidstarter.MyApplication;

import java.util.ArrayList;
import java.util.Stack;

import timber.log.Timber;

/**
 * Created by samvedana on 3/1/18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    MyApplication myApplication;
    private Stack<String> fragmentStack = new Stack<String>();

    private static final String fragmentStackStr = "fragmentStack";

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    public MyApplication getMyApplication() {
        return (MyApplication)getApplication();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            ArrayList<String> fragStack = savedInstanceState.getStringArrayList(fragmentStackStr);
            fragmentStack.clear();
            for (String str : fragStack) {
                fragmentStack.push(str);
            }
        }
        else {
            fragmentStack.clear();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList(fragmentStackStr, new ArrayList<String>(fragmentStack));
        super.onSaveInstanceState(outState);
    }

    public void setFragment(int fragmentContainerId, Fragment frag,
                            String tag, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(fragmentContainerId) == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(fragmentContainerId, frag, tag);
            if (addToBackStack) {
                ft.addToBackStack(null);
                fragmentStack.push(tag);
            }
            ft.commit();
        }
    }

    protected void replaceFragment(int fragmentContainerViewId, Fragment frag,
                                   String tag, boolean addToBackStack){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(fragmentContainerViewId, frag, tag);
        if (addToBackStack) {
            ft.addToBackStack(null);
            fragmentStack.push(tag);
        }
        ft.commit();
    }

    public String getCurrentTag() {
        String tag = null;
        if (!fragmentStack.isEmpty()) {
            tag = fragmentStack.peek();
        }
//        Timber.v("getCurrentTag returned %s", tag);
        return tag;
    }
}
