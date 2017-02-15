package projekt.secguard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//todo try i catche na typy inputów

public class AdminAddLocationActivity extends AppCompatActivity {

    Button button_choice1, button_choice2;
    ProgressDialog pDialog;
    ArrayList<DataHolder> companyData;
    ArrayList<DataTypesHolder> types;
    private String TAG = AdminAddLocationActivity.class.getSimpleName();
    int context_id = 0;
    int selector;
    int guards = 0;
    long minDate, maxDate, minTime, maxTime;
    int flagDate;

    EditText edName, edStreet, edStreetNumber, edCity, edDateStart, edDateStop;
    Button buttonOplus, buttonOminus, buttonSetDate, buttonSetDate2, buttonSetStartHour, buttonSetStopHour, buttonAdd;
    TextView textHowManyGuards, textTimeStart, textTimeStop;
    String type;
    LocationData location = new LocationData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_location);



        edName = (EditText) findViewById(R.id.editText5);
        edStreet = (EditText) findViewById(R.id.editText6);
        edStreetNumber = (EditText) findViewById(R.id.editText7);
        edCity = (EditText) findViewById(R.id.editText8);
        textHowManyGuards = (TextView) findViewById(R.id.textView49);
        edDateStart = (EditText) findViewById(R.id.editText9);
        edDateStop = (EditText) findViewById(R.id.editText13);
        textTimeStart = (TextView) findViewById(R.id.textView31);
        textTimeStop = (TextView) findViewById(R.id.textView32);

        button_choice1 = (Button) findViewById(R.id.button5);
        new getType().execute();
        button_choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getType().execute();
                context_id = 1;
                registerForContextMenu(button_choice1);
                openContextMenu(button_choice1);
            }
        });

        button_choice2 = (Button) findViewById(R.id.button6);
        new getCompany().execute();
        button_choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getCompany().execute();
                context_id = 2;
                registerForContextMenu(button_choice2);
                openContextMenu(button_choice2);
            }
        });

        buttonOplus = (Button) findViewById(R.id.button10);
        new getType().execute();
        buttonOplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(guards<10)
                guards++;
                textHowManyGuards.setText("" + guards);
                location.setIlOchroniarzy(guards);
            }
        });

        buttonOminus = (Button) findViewById(R.id.button11);
        new getType().execute();
        buttonOminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(guards>0)
                guards--;
                textHowManyGuards.setText("" + guards);
                location.setIlOchroniarzy(guards);
            }
        });

        buttonOminus = (Button) findViewById(R.id.button11);
        new getType().execute();
        buttonOminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(guards>0)
                    guards--;
                textHowManyGuards.setText("" + guards);
            }
        });

        buttonSetDate = (Button) findViewById(R.id.button16);
        new getType().execute();
        buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagDate = 1;
                Intent intent = new Intent(getApplicationContext(), CalendarViewActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        buttonSetDate2 = (Button) findViewById(R.id.button18);
        new getType().execute();
        buttonSetDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagDate = 2;
                Intent intent = new Intent(getApplicationContext(), CalendarViewActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        buttonSetStartHour = (Button) findViewById(R.id.button14);
        new getType().execute();
        buttonSetStartHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagDate = 3;
                Intent intent = new Intent(getApplicationContext(), TimeActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        buttonSetStopHour = (Button) findViewById(R.id.button13);
        new getType().execute();
        buttonSetStopHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagDate = 4;
                Intent intent = new Intent(getApplicationContext(), TimeActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        buttonAdd = (Button) findViewById(R.id.button19);
        new getType().execute();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(false)
                //{


                //}else{

                    location.setNazwa(edName.getText().toString());
                    location.setNr_ulicy(Integer.parseInt(edStreetNumber.getText().toString()));
                    location.setUlica(edStreet.getText().toString());
                    location.setGps_x(0);
                    location.setGps_x(0);
                    location.setGps_x(100);

                    new insertCompany().execute();
                    Toast.makeText(getApplicationContext(),
                        "Wprowadzono do bazy",
                        Toast.LENGTH_LONG)
                        .show();
            //}
            }
        });





    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (context_id == 1) {
            int iterator_id = 0;
            menu.setHeaderTitle("Wybierz typ");
            for (DataTypesHolder d : types) {
                menu.add(0, iterator_id, 0, d.getHold_name());
                iterator_id++;
            }
        }

        if (context_id == 2) {
            menu.setHeaderTitle("Wybierz firme");
            int iterator_id = 0;
            for (DataHolder d : companyData) {
                menu.add(0, iterator_id, 0, d.getHold_name());
                iterator_id++;
            }
        }
    }

    /*@Override
    protected void onResume()
    {
        super.onResume();
        Intent intent = getIntent();
        if(flagDate == 1) {
            minDate = intent.getLongExtra("calendarDate", 0L);
            Log.e(TAG, "Data w intent long: " + minDate);
            String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(minDate));
            edDateStart.setText(dateString);

        }

        if(flagDate == 2) {
            maxDate = getIntent().getLongExtra("calendarDate", 0L);
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    if(flagDate == 1) {
                        Long newData = data.getLongExtra("calendarData", 0L);
                        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(newData));
                        edDateStart.setText(dateString);
                        minDate = newData;
                        location.setStartData(newData);
                    }
                    if(flagDate == 2) {
                        Long newData = data.getLongExtra("calendarData", 0L);
                        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(newData));
                        edDateStop.setText(dateString);
                        maxDate = newData;
                        location.setStopData(newData);
                    }
                }
                break;
            }

            case (2) : {
                if (resultCode == Activity.RESULT_OK) {
                    if(flagDate == 3) {
                        Long newData = data.getLongExtra("selectedTime", 0L);
                        String timeString = new SimpleDateFormat("hh:mm").format(new Date(newData));
                        textTimeStart.setText(timeString);
                        minTime = newData;
                        location.setStartGodziny(newData);
                    }
                    if(flagDate == 4) {
                        Long newData = data.getLongExtra("selectedTime", 0L);
                        String timeString = new SimpleDateFormat("hh:mm").format(new Date(newData));
                        textTimeStop.setText(timeString);
                        maxTime = newData;
                        location.setStopGodziny(newData);
                    }
                }
                break;
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        selector = item.getItemId();
        TextView textType = (TextView) findViewById(R.id.textView43);
        TextView textCompany = (TextView) findViewById(R.id.textView45);

        if(context_id == 1) {
            textType.setText("Typ obiektu: " + types.get(selector).getHold_name());
            for(DataTypesHolder d : types)
            {
                if(d.getHold_name().compareTo(types.get(selector).toString())==0)
                {
                    location.setIdTyp(Integer.parseInt(d.getHold_id()));
                    break;
                }
            }



        }

        if(context_id == 2) {
            textCompany.setText("Zleceniodawca: " + companyData.get(selector).getHold_name());

            for(DataHolder d : companyData)
            {
                if(d.getHold_name().compareTo(companyData.get(selector).toString())==0)
                {
                    location.setIdZlec(Integer.parseInt(d.getHold_id()));
                    break;
                }
            }
        }

        return true;
    }

    private class getType extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Dialog oczekiwania na polaczenie i sprawdzenie danych z serwerm
            pDialog = new ProgressDialog(AdminAddLocationActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            //pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler getAll = new HttpHandler();

            // hash code zabezpiecza przed wyciekiem danych z serwera
            String urlTypesData = "http://185.28.100.205/getAllTypes.php?hashcode=J1f2sa0sdi3Awj349";

            // Request na serwer
            String jsonStr = getAll.makeServiceCall(urlTypesData);

            Log.e(TAG, "Odebrano typy: " + jsonStr);

            if (jsonStr != null) {
                types = new ArrayList<DataTypesHolder>();
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStr);
                    JSONObject mJsonObject = new JSONObject();


                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        type = mJsonObject.getString("typy_nazwa");

                        mJsonObject = mJsonArray.getJSONObject(i);
                        String id, n;

                        id = mJsonObject.getString("id_typ");
                        n = mJsonObject.getString("typy_nazwa");

                        DataTypesHolder d = new DataTypesHolder(id, n);
                        types.add(d);
                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Niepoprawne dane logowania",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Problem z serwerem.");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Server error, Zobacz LogCat po wiecej informacji",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    private class getCompany extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Dialog oczekiwania na polaczenie i sprawdzenie danych z serwerm
            pDialog = new ProgressDialog(AdminAddLocationActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            //pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler getAll = new HttpHandler();

            // hash code zabezpiecza przed wyciekiem danych z serwera
            String urlCompanyData = "http://185.28.100.205/getAllCompany.php?hashcode=J1f2sa0sdi3Awj349";

            // Request na serwer
            String jsonStr = getAll.makeServiceCall(urlCompanyData);

            Log.e(TAG, "Odebrano firmy: " + jsonStr);

            if (jsonStr != null) {
                companyData = new ArrayList<DataHolder>();
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStr);
                    JSONObject mJsonObject = new JSONObject();


                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        String id, n, p, k;

                        id = mJsonObject.getString("id_zleceniodawcy");
                        n = mJsonObject.getString("nazwa_firmy");
                        p = mJsonObject.getString("telefon");
                        k = mJsonObject.getString("osoba_kontakt");
                        DataHolder d = new DataHolder(id, n, p, k);
                        companyData.add(d);
                    }




                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Niepoprawne dane logowania",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Problem z serwerem.");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Server error, Zobacz LogCat po wiecej informacji",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }


    private class DataHolder {
        String hold_id;
        String hold_name;
        String hold_phone;
        String hold_kontakt;

        DataHolder(String i, String n, String p, String k) {
            hold_id = i;
            hold_name = n;
            hold_phone = p;
            hold_kontakt = k;
        }

        public String getHold_id() {
            return hold_id;
        }

        public String getHold_kontakt() {
            return hold_kontakt;
        }

        public void setHold_kontakt(String hold_kontakt) {
            this.hold_kontakt = hold_kontakt;
        }

        public String getHold_name() {
            return hold_name;
        }

        public void setHold_name(String hold_name) {
            this.hold_name = hold_name;
        }

        public String getHold_phone() {
            return hold_phone;
        }

        public void setHold_phone(String hold_phone) {
            this.hold_phone = hold_phone;
        }

        public void setHold_id(String hold_id) {
            this.hold_id = hold_id;
        }
    }

    private class DataTypesHolder {
        String hold_id;
        String hold_name;

        DataTypesHolder(String i, String n) {
            hold_id = i;
            hold_name = n;
        }

        public String getHold_id() {
            return hold_id;
        }

        public String getHold_name() {
            return hold_name;
        }

        public void setHold_name(String hold_name) {
            this.hold_name = hold_name;
        }

        public void setHold_id(String hold_id) {
            this.hold_id = hold_id;
        }
    }

    private class insertCompany extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler insert = new HttpHandler();
            String urlInsert = "http://185.28.100.205/insertCompany.php?nazwa=" + location.getNazwa() + "&ulica_numer=" + location.getNr_ulicy() + "&ulica=" + location.getUlica() + "&miasto=" + location.getMiasto() + "&data_start=" + location.getStartData() + "&data_stop=" + location.getStopData() + "&liczba_ochroniarzy=" + location.getIlOchroniarzy() + "&czas_start=" + location.getStartGodziny() + "&czas_stop=" + location.getStopGodziny() +  "&gps_x=" + location.getGps_x() +  "&gps_y=" + location.getGps_y() + "&gps_r=" + location.getGps_r();
            String geting = insert.makeServiceCall(urlInsert);
            Log.e(TAG, "Odebrano: " + geting);
            return null;
        }
    }
}
