package projekt.secguard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 *  Activity dla zarzadzania firmą dla menagera
 *
 */
public class AdminOrdersActivity extends AppCompatActivity {

    //TODO ORDER BY LIST VIEW
    private String TAG = AdminOrdersActivity.class.getSimpleName();
    private HashMap<String, String> hMap;
    private ProgressDialog pDialog;
    private ListView lista;

    // URL to get contacts JSON
    private static String url = "http://185.28.100.205/getAllLocationData.php?hashcode=J1f2sa0sdi3Awj349";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);
        contactList = new ArrayList<>();
        lista = (ListView)findViewById(R.id.lista_lokacji);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {
                 //  int intId = (int)id;
                Toast.makeText(getApplicationContext(), "ON CLICK: POS" + position + " id " + id, Toast.LENGTH_LONG).show();
                Intent intentAdminOrdersForLocationActivity = new Intent(getApplicationContext(), AdminOrdersForLocationActivity.class);
                intentAdminOrdersForLocationActivity.putExtra("locationData", contactList.get((int)id));
                startActivity(intentAdminOrdersForLocationActivity);
            }
        });




        new GetObjects().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetObjects extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(AdminOrdersActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Odebrano: " + jsonStr);


            // parsing data

            if (jsonStr != null) {
                          //  companyData = new ArrayList<DataHolder>();
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStr);

                   // JSONObject mJsonObject = new JSONObject();


                    for(int i = 0 ; i < mJsonArray.length() ; i++) {
                      //  mJsonObject =
                        JSONObject o = mJsonArray.getJSONObject(i);

                        String id_lokacji= o.getString("id_lokacje");
                        String nazwa = o.getString("nazwa");
                        String ulica_numer= o.getString("ulica_numer");
                        String ulica= o.getString("ulica");
                        String miasto= o.getString("miasto");
                        String typy_nazwa= o.getString("typy_nazwa");
                        String nazwa_firmy= o.getString("nazwa_firmy");
                        String osoba_kontakt= o.getString("osoba_kontakt");
                        String telefon= o.getString("telefon");
                        String data_start= o.getString("data_start");
                        String data_stop= o.getString("data_stop");
                        String liczba_ochroniarzy= o.getString("liczba_ochroniarzy");
                        String czas_start= o.getString("czas_start");
                        String czas_stop= o.getString("czas_stop");



                        // tmp hash map for single contact
                        hMap = new HashMap<>();

                        // adding each child node to HashMap key => value
                        hMap.put("id_lokacji", id_lokacji);
                        hMap.put("nazwa", nazwa);
                        hMap.put("ulica_numer", ulica_numer);
                        hMap.put("ulica", ulica);
                        hMap.put("miasto", miasto);
                        hMap.put("typy_nazwa", typy_nazwa);
                        hMap.put("nazwa_firmy", nazwa_firmy);
                        hMap.put("osoba_kontakt", osoba_kontakt);
                        hMap.put("telefon", telefon);
                        hMap.put("data_start", data_start);
                        hMap.put("data_stop", data_stop);
                        hMap.put("liczba_ochroniarzy", liczba_ochroniarzy);
                        hMap.put("czas_start", czas_start);
                        hMap.put("czas_stop", czas_stop);

                        // adding contact to contact list
                        contactList.add(hMap);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
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
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            ListAdapter adapter = new SimpleAdapter(
                    AdminOrdersActivity.this, contactList,
                    R.layout.menu_one_object, new String[]{"id_lokacji", "nazwa",
                    "ulica_numer","ulica","miasto","typy_nazwa","nazwa_firmy",
                    "data_start","data_stop","liczba_ochroniarzy","czas_start","czas_stop"}, new int[]{
                    //"osoba_kontakt","telefon",
                    R.id.tv_id_lokacji,
                    R.id.tv_nazwa,
                    R.id.tv_ulica_numer,
                    R.id.tv_ulica,
                    R.id.tv_miasto,
                    R.id.tv_typy_nazwa,
                    R.id.tv_nazwa_firmy,
                   // R.id.tv_osoba_kontakt,
                  //  R.id.tv_telefon,
                    R.id.tv_data_start,
                    R.id.tv_data_stop,
                    R.id.tv_liczba_ochroniarzy,
                    R.id.tv_czas_start,
                    R.id.tv_czas_stop
            });
            lista.setAdapter(adapter);
        }

    }
}
