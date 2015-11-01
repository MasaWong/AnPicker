package mw.ankara.picker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import mw.ankara.picker.lib.WheelTime;

/**
 * @author masawong
 * @since 7/12/15.
 */
public class TimePickerDialog extends PickerDialog implements OnClickListener {

    private WheelTime mWheelTime;
    private OnTimeSelectListener mTimeSelectListener;

    private Type mType = Type.ALL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setGravityBottom();

        View view = inflater.inflate(R.layout.dialog_time_picker, container);

        // 确定和取消按钮
        view.findViewById(R.id.timepicker_b_submit).setOnClickListener(this);
        view.findViewById(R.id.timepicker_b_cancel).setOnClickListener(this);

        // 时间滚轮
        View timepickerview = view.findViewById(R.id.timepicker);
        mWheelTime = new WheelTime(timepickerview, mType);
        mWheelTime.screenheight = getResources().getDisplayMetrics().heightPixels;

        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mWheelTime.setPicker(year, month, day, hours, minute);

        return view;
    }

    public void setType(Type type) {
        mType = type;
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.mTimeSelectListener = timeSelectListener;
    }

    /**
     * 设置可以选择的时间范围
     *
     * @param START_YEAR
     * @param END_YEAR
     */
    public void setRange(int START_YEAR, int END_YEAR) {
        WheelTime.setSTART_YEAR(START_YEAR);
        WheelTime.setEND_YEAR(END_YEAR);
    }

    /**
     * 设置选中时间
     *
     * @param date
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mWheelTime.setPicker(year, month, day, hours, minute);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        mWheelTime.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.timepicker_b_submit && mTimeSelectListener != null) {
            try {
                Date date = WheelTime.dateFormat.parse(mWheelTime.getTime());
                mTimeSelectListener.onTimeSelect(date);
            } catch (ParseException ignored) {
            }
        }
        dismiss();
    }

    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH
    }// 四种选择模式，年月日时分，年月日，时分，月日时分

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
    }
}
