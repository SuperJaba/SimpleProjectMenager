package pl.skg.simpleprojectmanager.utils;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


public final class ListenerUtils {

    public static void myTextChangesListener(final EditText editText, final TextInputLayout label) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable edt) {
                if (editText.getText().length() > 0) {
                    label.setError(null);
                }
            }
        });
    }
}
