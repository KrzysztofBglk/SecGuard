package projekt.secguard;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeActivity extends AppCompatActivity {

    Button accept;
    TimePicker timePicker;
    int hh, mm;
    long selectedHour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        accept = (Button) findViewById(R.id.button17);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hh = timePicker.getHour();
                mm = timePicker.getMinute();

                String sHours = hh+":"+mm;
                SimpleDateFormat f = new SimpleDateFormat("hh:mm");
                try {
                    Date dat = f.parse(sHours);
                    selectedHour = dat.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedTime", selectedHour);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
