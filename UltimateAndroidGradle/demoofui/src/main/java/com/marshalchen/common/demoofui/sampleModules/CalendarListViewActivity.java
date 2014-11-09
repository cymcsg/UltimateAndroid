package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.calendarlistview.DatePickerController;
import com.marshalchen.common.uimodule.calendarlistview.DayPickerView;


public class CalendarListViewActivity extends Activity implements DatePickerController {

    private DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_list_view_activity);

        dayPickerView = (DayPickerView) findViewById(R.id.pickerView);
        dayPickerView.setmController(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.android_hover, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getMaxYear()
    {
        return 2015;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day)
    {
        Log.e("Day Slected", day + " / " + month + " / " + year);
    }
}
