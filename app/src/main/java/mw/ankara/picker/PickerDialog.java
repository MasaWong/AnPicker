package mw.ankara.picker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * @author masawong
 * @since 11/1/15
 */
public class PickerDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Picker_Constant);
        setCancelable(true);
    }

    protected void setGravityBottom() {
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.PickerAnim);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void show(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
            Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }

        show(activity.getSupportFragmentManager(), getClass().getSimpleName());
    }
}
