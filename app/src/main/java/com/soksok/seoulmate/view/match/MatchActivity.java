package com.soksok.seoulmate.view.match;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.soksok.seoulmate.R;
import com.soksok.seoulmate.common.BasicUtils;
import com.soksok.seoulmate.databinding.ActivityMatchBinding;

import java.util.Calendar;

/**
 * Mh1
 */
public class MatchActivity extends AppCompatActivity {

    /** 여행 앨벙을 생성하기 위한 데이터
     * EXTRA_FIRST_DATE: "yyyy-MM-dd HH:mm" 형식의 여행 시작날짜 / Type: String
     * EXTRA_LAST_DATE: "yyyy-MM-dd HH:mm" 형식의 여행 종료날짜 / Type: String
     * EXTRA_ADULT_COUNT: 성인 동행인원 / Type: int
     * EXTRA_CHILD_COUNT: 어린이 동행인원 / Type: int
     * EXTRA_BABY_COUNT: 유아 동행인원 / Type: int
     *
     * 최종 저장을 위해 메이트 신청 탭까지 넘겨야함
     */
    public static String EXTRA_FIRST_DATE = "EXTRA_FIRST_DATE";
    public static String EXTRA_LAST_DATE = "EXTRA_LAST_DATE";
    public static String EXTRA_ADULT_COUNT = "EXTRA_ADULT_COUNT";
    public static String EXTRA_CHILD_COUNT = "EXTRA_CHILD_COUNT";
    public static String EXTRA_BABY_COUNT = "EXTRA_BABY_COUNT";

    private int adultCount = 0;
    private int childCount = 0;
    private int babyCount = 0;

    private ActivityMatchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDataBinding();
        setupViews();
    }

    private void onDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match);
    }

    private void setupViews() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String monthh = String.valueOf(month);
        if (month / 10 == 0) {
            monthh = "0" + month;
        }

        String dayy = String.valueOf(day);
        if (day / 10 == 0) {
            dayy = "0" + day;
        }

        String hourr = String.valueOf(hour);
        if (hour / 10 == 0) {
            hourr = "0" + hour;
        }

        String minutee = String.valueOf(minute);
        if (minute / 10 == 0) {
            minutee = "0" + minute;
        }

        setFirstDate(year, monthh, dayy);
        setFirstTime(hourr, minutee);
        setLastDate(year, monthh, dayy);
        setLastTime(hourr, minutee);
    }

    private void setFirstDate(int year, String month, String day) {
        binding.tvDateFirst.setText(getString(R.string.match_tv_date, year, month, day));
    }

    private void setFirstTime(String hour, String minute) {
        binding.tvTimeFirst.setText(getString(R.string.match_tv_time, hour, minute));
    }

    private void setLastDate(int year, String month, String day) {
        binding.tvDateLast.setText(getString(R.string.match_tv_date, year, month, day));
    }

    private void setLastTime(String hour, String minute) {
        binding.tvTimeLast.setText(getString(R.string.match_tv_time, hour, minute));
    }

    /*
     * 클릭 이벤트
     */
    // 날짜 선택
    public void onFirstDateClick(View v) {

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month += 1;
                String monthh = String.valueOf(month);
                if (month / 10 == 0) {
                    monthh = "0" + month;
                }

                String dayy = String.valueOf(dayOfMonth);
                if (dayOfMonth / 10 == 0) {
                    dayy = "0" + dayOfMonth;
                }
                setFirstDate(year, monthh, dayy);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();
    }

    public void onFirstTimeClick(View v) {

        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String hourr = String.valueOf(hourOfDay);
                if (hourOfDay / 10 == 0) {
                    hourr = "0" + hourOfDay;
                }

                String minutee = String.valueOf(minute);
                if (minute / 10 == 0) {
                    minutee = "0" + minute;
                }

                setFirstTime(hourr, minutee);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        dialog.show();
    }

    public void onLastDateClick(View v) {

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month += 1;
                String monthh = String.valueOf(month);
                if (month / 10 == 0) {
                    monthh = "0" + month;
                }

                String dayy = String.valueOf(dayOfMonth);
                if (dayOfMonth / 10 == 0) {
                    dayy = "0" + dayOfMonth;
                }
                setLastDate(year, monthh, dayy);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();
    }

    public void onLastTimeClick(View v) {

        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String hourr = String.valueOf(hourOfDay);
                if (hourOfDay / 10 == 0) {
                    hourr = "0" + hourOfDay;
                }

                String minutee = String.valueOf(minute);
                if (minute / 10 == 0) {
                    minutee = "0" + minute;
                }
                setLastTime(hourr, minutee);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        dialog.show();
    }

    // 동행인원 선택
    public void onAdultDownClick(View v) {
        if (adultCount > 0) {
            adultCount -= 1;
            binding.tvAdultCount.setText(getString(R.string.match_tv_count, adultCount));
        }
    }
    public void onAdultUpClick(View v) {
        adultCount += 1;
        binding.tvAdultCount.setText(getString(R.string.match_tv_count, adultCount));
    }

    public void onChildDownClick(View v) {
        if (childCount > 0) {
            childCount -= 1;
            binding.tvChildCount.setText(getString(R.string.match_tv_count, childCount));
        }
    }
    public void onChildUpClick(View v) {
        childCount += 1;
        binding.tvChildCount.setText(getString(R.string.match_tv_count, childCount));
    }

    public void onBabyDownClick(View v) {
        if (babyCount > 0) {
            babyCount -= 1;
            binding.tvBabyCount.setText(getString(R.string.match_tv_count, babyCount));
        }
    }
    public void onBabyUpClick(View v) {
        babyCount += 1;
        binding.tvBabyCount.setText(getString(R.string.match_tv_count, babyCount));
    }

    public void onNextClick(View v) {
        goToSelectActivity();
    }

    private void goToSelectActivity() {

        String[] dateFirst = BasicUtils.split(binding.tvDateFirst.getText().toString(), "\\.");
        String timeFirst = binding.tvTimeFirst.getText().toString();
        String firstDateString = getString(R.string.common_date, dateFirst[0], dateFirst[1], dateFirst[2], timeFirst);

        String[] dateLast = BasicUtils.split(binding.tvDateLast.getText().toString(), "\\.");
        String timeLast = binding.tvTimeLast.getText().toString();
        String lastDateString = getString(R.string.common_date, dateLast[0], dateLast[1], dateLast[2], timeLast);

        Intent intent = new Intent(this, SelectActivity.class);
        intent.putExtra(EXTRA_FIRST_DATE, firstDateString);
        intent.putExtra(EXTRA_LAST_DATE, lastDateString);
        intent.putExtra(EXTRA_ADULT_COUNT, adultCount);
        intent.putExtra(EXTRA_CHILD_COUNT, childCount);
        intent.putExtra(EXTRA_BABY_COUNT, babyCount);
        startActivity(intent);
    }
}
