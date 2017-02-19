package projekt.secguard;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarViewActivity extends AppCompatActivity {

    Button accept;
    private String TAG = AdminAddLocationActivity.class.getSimpleName();
    String sDate;
    CalendarView simpleCalendarView;
    long selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        accept = (Button) findViewById(R.id.button15);
        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView);

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int d = dayOfMonth;
                int m = month;
                int y = year;
                sDate = d+"/"+m+"/"+y;
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dat = f.parse(sDate);
                    selectedDate = dat.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long date = simpleCalendarView.getDate();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("calendarData", selectedDate);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

                //Intent intent = new Intent(Intent.ACTION_SEND);
                //intent.putExtra("calendarDate", date);
                //Log.e(TAG, "Data w long: " + date);
                //sendBroadcast(intent);
                //finish();
            }
        });
    }
}
