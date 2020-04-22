package com.erank.tasks.interfaces;

import android.text.Editable;
import android.text.TextWatcher;

public interface TextWatcherAdapter extends TextWatcher {
    @Override
    default void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    default void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    default void afterTextChanged(Editable editable) {

    }
}
