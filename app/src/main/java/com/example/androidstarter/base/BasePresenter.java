package com.example.androidstarter.base;

import android.arch.lifecycle.LifecycleObserver;

/**
 * Created by samvedana on 3/1/18.
 */

public interface BasePresenter<V> {
    void onAttach();
    void onDetach();
}
