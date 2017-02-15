package projekt.secguard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class AdminAddCompanyActivity extends AppCompatActivity {
    String get_nazwa,get_telefon,get_osoba;
    EditText nazwaFirmy;
    EditText telefon;
    EditText osobaKontaktowa;
    Button dodaj;
    String tempName;
    Button sprawdzCzyWstepuje;
    ProgressDialog pDialog;
    ArrayList<String> companyNames;
    TextView txt_status;
    private String TAG = AdminAddCompanyActivity.class.getSimpleName();

    public static int numeric(String number) {
        try {
            double d = Double.parseDouble(number);
        }
        catch(NumberFormatException nfe) {
            return 0;
        }
        return 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_company);
        nazwaFirmy = (EditText)findViewById(R.id.et_add_name);
        telefon = (EditText)findViewById(R.id.et_add_phone);
        osobaKontaktowa = (EditText)findViewById(R.id.et_add_osobaKontaktowa);
        dodaj = (Button)findViewById(R.id.button_add);
        sprawdzCzyWstepuje = (Button)findViewById(R.id.button_check);
        txt_status = (TextView)findViewById(R.id.txt_status);

        sprawdzCzyWstepuje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempName = nazwaFirmy.getText().toString();
                new getAllNames().execute();
                SystemClock.sleep(1000);
                if(tempName.length() == 0)
                {
                    txt_status.setText("Wpisz nawze");
                    txt_status.setTextColor(getResources().getColor(R.color.red));
                }else {
                    if (companyNames.contains(tempName)) {
                        txt_status.setText("Duplikacja");
                        txt_status.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        txt_status.setText("OK");
                        txt_status.setTextColor(getResources().getColor(R.color.green));
                    }
                }
            }
        });

        // INSERT do bazy
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get_nazwa = nazwaFirmy.getText().toString();
                get_telefon = telefon.getText().toString();
                get_osoba = osobaKontaktowa.getText().toString();


                // phone validator
                if((get_telefon.length() != 9 || get_telefon.length() != 7) && numeric(get_telefon) != 1){

                        telefon.setText("");
                        Toast.makeText(getApplicationContext(),
                                "Blad: " + get_telefon + " nie jest poprawnym numerem telefonu!",
                                Toast.LENGTH_LONG)
                                .show();
                }else{
                    new getAllNames().execute();
                    SystemClock.sleep(1000);

                    // czy nazwa jest w bazie
                    if(companyNames.contains(get_nazwa)) {
                        Toast.makeText(getApplicationContext(),
                                "W bazie wystepuje firma pod nazwa: " + get_nazwa,
                                Toast.LENGTH_LONG)
                                .show();

                        //czy nazwa is null
                    }else if(get_nazwa.length() == 0){
                        Toast.makeText(getApplicationContext(),
                                "Wprowadz nazwe firmy " + get_nazwa,
                                Toast.LENGTH_LONG)
                                .show();

                        // kontakt is null
                    }else if(get_osoba.length() == 0){
                    Toast.makeText(getApplicationContext(),
                            "Nie wprowadzono osoby kontaktowej " + get_osoba,
                            Toast.LENGTH_LONG)
                            .show();
                }else{
                        new insertCompany().execute();
                        Toast.makeText(getApplicationContext(),
                                "Wprowadzono do bazy",
                                Toast.LENGTH_LONG)
                                .show();
                        nazwaFirmy.setText("");
                        telefon.setText("");
                        osobaKontaktowa.setText("");
                    }
                }
            }
        });
    }


    private class getAllNames extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Dialog oczekiwania na polaczenie i sprawdzenie danych z serwerm
            pDialog = new ProgressDialog(AdminAddCompanyActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler getAll = new HttpHandler();
            String urlComanyNames = "http://185.28.100.205/getAllCompanyNames.php?hashcode=J1f2sa0sdi3Awj349";

            // Request na serwer
            String jsonStr = getAll.makeServiceCall(urlComanyNames);

            Log.e(TAG, "Odebrano: " + jsonStr);

            if (jsonStr != null) {
                 companyNames = new ArrayList<String>(); // nowa array lista dla każdego zapytania żeby się odświeżyło
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStr);
                    JSONObject mJsonObject = new JSONObject();


                    for(int i = 0 ; i < mJsonArray.length() ; i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        companyNames.add(mJsonObject.getString("nazwa_firmy")) ;

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


    private class insertCompany extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler insert = new HttpHandler();
            String get_osoba_modded = get_osoba.replaceAll(" ", "%20");
            String get_nazwa_modded = get_nazwa.replaceAll(" ", "%20");
            String urlInsert = "http://185.28.100.205/insertCompany.php?nazwa=" + get_nazwa_modded + "&telefon=" + get_telefon + "&kontakt=" + get_osoba_modded;
            String geting = insert.makeServiceCall(urlInsert);
            Log.e(TAG, "Odebrano: " + geting);
            return null;
        }
    }
}