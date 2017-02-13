package projekt.secguard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminAddLocationActivity extends AppCompatActivity {

    Button button_choice1, button_choice2;
    ProgressDialog pDialog;
    ArrayList <DataHolder> companyData;
    ArrayList <String> types;
    private String TAG = AdminAddLocationActivity.class.getSimpleName();
    int context_id = 0;

    EditText edName, edStreet, edStreetNumber, edCity;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_location);

        edName = (EditText) findViewById(R.id.editText5);
        edStreet = (EditText) findViewById(R.id.editText6);
        edStreetNumber = (EditText) findViewById(R.id.editText7);
        edCity = (EditText) findViewById(R.id.editText8);




        button_choice1 = (Button)findViewById(R.id.button5);
        //new getType().execute();

        button_choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getType().execute();
                context_id = 1;
                registerForContextMenu(button_choice1);
                openContextMenu(button_choice1);
            }
        });

        button_choice2 = (Button)findViewById(R.id.button6);
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


        
        
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if(context_id == 1) {
            menu.setHeaderTitle("Wybierz typ");
            int iterator_id = 0;
            for (int i = 0;i>types.size();i++) {
                menu.add(types.get(i));
                iterator_id++;
            }
        }

        if(context_id == 2) {
            menu.setHeaderTitle("Wybierz firme");
            int iterator_id = 0;
            for (DataHolder d : companyData) {
                menu.add(0, iterator_id, 0, d.getHold_name());
                iterator_id++;
            }
        }
    }

    private class getType extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Dialog oczekiwania na polaczenie i sprawdzenie danych z serwerm
            pDialog = new ProgressDialog(AdminAddLocationActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler getAll = new HttpHandler();

            // hash code zabezpiecza przed wyciekiem danych z serwera
            String urlCompanyData = "http://185.28.100.205/getAllTypes.php?hashcode=J1f2sa0sdi3Awj349";

            // Request na serwer
            String jsonStr = getAll.makeServiceCall(urlCompanyData);

            Log.e(TAG, "Odebrano: " + jsonStr);

            if (jsonStr != null) {
                companyData = new ArrayList<DataHolder>();
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStr);
                    JSONObject mJsonObject = new JSONObject();


                    for(int i = 0 ; i < mJsonArray.length() ; i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);


                        type = mJsonObject.getString("nazwa");

                        types.add(type);

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
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler getAll = new HttpHandler();

            // hash code zabezpiecza przed wyciekiem danych z serwera
            String urlCompanyData = "http://185.28.100.205/getAllCompany.php?hashcode=J1f2sa0sdi3Awj349";

            // Request na serwer
            String jsonStr = getAll.makeServiceCall(urlCompanyData);

            Log.e(TAG, "Odebrano: " + jsonStr);

            if (jsonStr != null) {
                companyData = new ArrayList<DataHolder>();
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStr);
                    JSONObject mJsonObject = new JSONObject();


                    for(int i = 0 ; i < mJsonArray.length() ; i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        String id,n,p,k;

                        id = mJsonObject.getString("id_zleceniodawcy");
                        n = mJsonObject.getString("nazwa_firmy");
                        p = mJsonObject.getString("telefon");
                        k = mJsonObject.getString("osoba_kontakt");
                        DataHolder d = new DataHolder(id,n,p,k);
                        companyData.add(d) ;
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



    }

    class DataHolder
    {
        String hold_id;
        String hold_name;
        String hold_phone;
        String hold_kontakt;

        DataHolder(String i, String n,String p, String k){
            hold_id = i;
            hold_name = n;
            hold_phone = p;
            hold_kontakt = k;
        }
        public String getHold_id(){
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
        public void setHold_id(String hold_id){
            this.hold_id = hold_id;
        }
    }

