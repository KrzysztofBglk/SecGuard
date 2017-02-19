package projekt.secguard;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AdminOrdersForLocationActivity extends AppCompatActivity {


    private String TAG = AdminOrdersForLocationActivity.class.getSimpleName();
    HashMap<String, String> dataMap = new HashMap<>();
    private HashMap<String, String> hMap;
    TextView kontakt_imie;
    Button o1,o2,o3,o4,o5,o6;
    TextView kontakt_telefon;
    TextView editedData;
    String data_zmiany;
    String id_lokacji;
    private ProgressDialog pDialog,pDialog2;
    int liczbaOchrony;
    ListView lv;
    ArrayList<Integer>  workers;
    ArrayList<Ochroniarz> listaDostepnych;
    ArrayList<HashMap<String, String>> dayArray;




















    @Override
    protected void onCreate(Bundle savedInstanceState) {






        dayArray = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders_for_location);
        lv = (ListView)findViewById(R.id.lista_dat);
        Intent intent = getIntent();

        dataMap = (HashMap) intent.getExtras().getSerializable("locationData");
        id_lokacji = dataMap.get("id_lokacji");
        kontakt_imie = (TextView) findViewById(R.id.tv_osoba_konakt);
        kontakt_telefon = (TextView) findViewById(R.id.tv_telefon_kontakt);
        editedData = (TextView)findViewById(R.id.tvl_data);
        o1 = (Button) findViewById(R.id.button_o1);
        o2 = (Button) findViewById(R.id.button_o2);
        o3 = (Button) findViewById(R.id.button_o3);
        o4 = (Button) findViewById(R.id.button_o4);
        o5 = (Button) findViewById(R.id.button_o5);
        o6 = (Button) findViewById(R.id.button_o6);

        o1.setVisibility(View.INVISIBLE);
        o2.setVisibility(View.INVISIBLE);
        o3.setVisibility(View.INVISIBLE);
        o4.setVisibility(View.INVISIBLE);
        o5.setVisibility(View.INVISIBLE);
        o6.setVisibility(View.INVISIBLE);

        new GetGuardData().execute();

        kontakt_imie.setText("Kontakt: " + dataMap.get("osoba_kontakt").toString());
        kontakt_telefon.setText("Telefon: " + dataMap.get("telefon").toString());
        liczbaOchrony =  Integer.parseInt(dataMap.get("liczba_ochroniarzy"));



        o1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerForContextMenu(o1);
                openContextMenu(o1);












            }
        });
        o2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        o3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        o4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        o5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        o6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        new GetData().execute();




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {
                data_zmiany = dayArray.get(position).get("data_zmiany");
                editedData.setText(data_zmiany);

                if(!dayArray.get(position).get("o1").equals("null"))
                {
                    for(Ochroniarz o : listaDostepnych)
                    {
                        if(Integer.parseInt(dayArray.get(position).get("o1")) == o.getId()){
                            String imie = o.getImie();
                            String nazwisko = o.getNazwisko();
                            o1.setText(imie + " " + nazwisko);
                            break;
                        }
                    }
                }

                if(!dayArray.get(position).get("o2").equals("null"))
                {
                    for(Ochroniarz o : listaDostepnych)
                    {
                        if(Integer.parseInt(dayArray.get(position).get("o2")) == o.getId()){
                            String imie = o.getImie();
                            String nazwisko = o.getNazwisko();
                            o2.setText(imie + " " + nazwisko);
                            break;
                        }
                    }
                }

                if(!dayArray.get(position).get("o3").equals("null"))
                {
                    for(Ochroniarz o : listaDostepnych)
                    {
                        if(Integer.parseInt(dayArray.get(position).get("o3")) == o.getId()){
                            String imie = o.getImie();
                            String nazwisko = o.getNazwisko();
                            o3.setText(imie + " " + nazwisko);
                            break;
                        }
                    }
                }

                if(!dayArray.get(position).get("o4").equals("null"))
                {
                    for(Ochroniarz o : listaDostepnych)
                    {
                        if(Integer.parseInt(dayArray.get(position).get("o4")) == o.getId()){
                            String imie = o.getImie();
                            String nazwisko = o.getNazwisko();
                            o4.setText(imie + " " + nazwisko);
                            break;
                        }
                    }
                }

                if(!dayArray.get(position).get("o5").equals("null"))
                {
                    for(Ochroniarz o : listaDostepnych)
                    {
                        if(Integer.parseInt(dayArray.get(position).get("o5")) == o.getId()){
                            String imie = o.getImie();
                            String nazwisko = o.getNazwisko();
                            o5.setText(imie + " " + nazwisko);
                            break;
                        }
                    }
                }

                if(!dayArray.get(position).get("o6").equals("null"))
                {
                    for(Ochroniarz o : listaDostepnych)
                    {
                        if(Integer.parseInt(dayArray.get(position).get("o6")) == o.getId()){
                            String imie = o.getImie();
                            String nazwisko = o.getNazwisko();
                            o6.setText(imie + " " + nazwisko);
                            break;
                        }
                    }
                }


                new GetWorker().execute();


                switch(liczbaOchrony){

                    case 1:
                        o1.setVisibility(View.VISIBLE);
                        o2.setVisibility(View.INVISIBLE);
                        o3.setVisibility(View.INVISIBLE);
                        o4.setVisibility(View.INVISIBLE);
                        o5.setVisibility(View.INVISIBLE);
                        o6.setVisibility(View.INVISIBLE);
                        break;

                    case 2:
                        o1.setVisibility(View.VISIBLE);
                        o2.setVisibility(View.VISIBLE);
                        o3.setVisibility(View.INVISIBLE);
                        o4.setVisibility(View.INVISIBLE);
                        o5.setVisibility(View.INVISIBLE);
                        o6.setVisibility(View.INVISIBLE);
                        break;

                    case 3:
                        o1.setVisibility(View.VISIBLE);
                        o2.setVisibility(View.VISIBLE);
                        o3.setVisibility(View.VISIBLE);
                        o4.setVisibility(View.INVISIBLE);
                        o5.setVisibility(View.INVISIBLE);
                        o6.setVisibility(View.INVISIBLE);
                        break;

                    case 4:
                        o1.setVisibility(View.VISIBLE);
                        o2.setVisibility(View.VISIBLE);
                        o3.setVisibility(View.VISIBLE);
                        o4.setVisibility(View.VISIBLE);
                        o5.setVisibility(View.INVISIBLE);
                        o6.setVisibility(View.INVISIBLE);
                        break;

                    case 5:
                        o1.setVisibility(View.VISIBLE);
                        o2.setVisibility(View.VISIBLE);
                        o3.setVisibility(View.VISIBLE);
                        o4.setVisibility(View.VISIBLE);
                        o5.setVisibility(View.VISIBLE);
                        o6.setVisibility(View.INVISIBLE);
                        break;

                    case 6:
                        o1.setVisibility(View.VISIBLE);
                        o2.setVisibility(View.VISIBLE);
                        o3.setVisibility(View.VISIBLE);
                        o4.setVisibility(View.VISIBLE);
                        o5.setVisibility(View.VISIBLE);
                        o6.setVisibility(View.VISIBLE);
                        break;

                    default:
                        o1.setVisibility(View.INVISIBLE);
                        o2.setVisibility(View.INVISIBLE);
                        o3.setVisibility(View.INVISIBLE);
                        o4.setVisibility(View.INVISIBLE);
                        o5.setVisibility(View.INVISIBLE);
                        o6.setVisibility(View.INVISIBLE);
                        break;
                }

                Toast.makeText(getApplicationContext(), "ON CLICK: POS" + position + " id " + id, Toast.LENGTH_LONG).show();
            }
        });
    }



    /**
     *  POBIERZ DANE O GRAFIKU DANEJ LOKACJI
     */
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(AdminOrdersForLocationActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // hash code zabezpiecza przed wyciekiem danych z serwera
            String url = "http://185.28.100.205/getOrdersForLocation.php?hashcode=J1f2sa0sdi3Awj349&idLokacji=";
            url = url + id_lokacji;
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Odebrano: " + jsonStr);


            if (jsonStr != null) {
                //  companyData = new ArrayList<DataHolder>();
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStr);

                    // JSONObject mJsonObject = new JSONObject();


                    for(int i = 0 ; i < mJsonArray.length() ; i++) {
                        //  mJsonObject =
                        JSONObject o = mJsonArray.getJSONObject(i);

                        String id_grafik_lokacji= o.getString("id_grafik_lokacji");
                        String data_zmiany = o.getString("data_zmiany");
                        String is_full= o.getString("is_full");
                        String o1 = o.getString("guard1");
                        String o2 = o.getString("guard2");
                        String o3 = o.getString("guard3");
                        String o4 = o.getString("guard4");
                        String o5 = o.getString("guard5");
                        String o6 = o.getString("guard6");


                        if(is_full.equals("f"))
                        {
                            is_full = "Dodaj...";
                        }else{
                            is_full = "";
                        }
                        // tmp hash map for single contact
                        hMap = new HashMap<>();

                        // adding each child node to HashMap key => value
                        hMap.put("id_grafik_lokacji", id_grafik_lokacji);
                        hMap.put("data_zmiany", data_zmiany);
                        hMap.put("is_full", is_full);
                        hMap.put("o1", o1);
                        hMap.put("o2", o2);
                        hMap.put("o3", o3);
                        hMap.put("o4", o4);
                        hMap.put("o5", o5);
                        hMap.put("o6", o6);

                        // adding contact to contact list
                        dayArray.add(hMap);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "INFO O LOKACJI error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "INFO O LOKACJI  error: " + e.getMessage(),
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


            ListAdapter adapter = new SimpleAdapter(
                    AdminOrdersForLocationActivity.this, dayArray,
                    R.layout.one_day_data, new String[]{"data_zmiany", "is_full",}, new int[]{
                    R.id.order_data,
                    R.id.order_full
            });
            lv.setAdapter(adapter);
        }

    }


    /**
     *  POBIERZ WSZYSTKICH OCHRONIARZY PRACUJACYCH (zajetych) W TYM DNIU
     */
    private class GetWorker extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(AdminOrdersForLocationActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // hash code zabezpiecza przed wyciekiem danych z serwera
            String get_data_guards = "http://185.28.100.205/getAvailableGuards.php?hashcode=J1f2sa0sdi3Awj349&data=";
            get_data_guards = get_data_guards + data_zmiany;
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(get_data_guards);

            Log.e(TAG, "Odebrano: " + jsonStr);
            workers = new ArrayList<Integer>();
            workers.add(0);

            if (jsonStr != null) {
                //  companyData = new ArrayList<DataHolder>();
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStr);

                    // JSONObject mJsonObject = new JSONObject();


                    for(int i = 0 ; i < mJsonArray.length() ; i++) {
                        //  mJsonObject =
                        JSONObject o = mJsonArray.getJSONObject(i);

                        if(!o.getString("ochrona").equals("null"))
                        {
                            int ochrona_id= Integer.parseInt(o.getString("ochrona"));
                            workers.add(ochrona_id);

                        }

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Pracownicy zajeci error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Pracownicy zajeci error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

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









    /**
     *  POBIERZ AKTYWNYCH PRACOWNIKÓW
     */
    private class GetGuardData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog2 = new ProgressDialog(AdminOrdersForLocationActivity.this);
            pDialog2.setMessage("Przetwarzanie...");
            pDialog2.setCancelable(false);
            pDialog2.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // hash code zabezpiecza przed wyciekiem danych z serwera
            String get_guards = "http://185.28.100.205/getAllGuards.php?hashcode=J1f2sa0sdi3Awj349";

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(get_guards);

            Log.e(TAG, "Odebrano: " + jsonStr);

            listaDostepnych = new  ArrayList<Ochroniarz>();


            if (jsonStr != null) {
                //  companyData = new ArrayList<DataHolder>();
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStr);

                    // JSONObject mJsonObject = new JSONObject();



                    for(int i = 0 ; i < mJsonArray.length() ; i++) {

                        JSONObject o = mJsonArray.getJSONObject(i);
                        int id_user = Integer.parseInt(o.getString("id_user"));
                        String imie = o.getString("imie");
                        String nazwisko = o.getString("nazwisko");
                        Ochroniarz temp = new Ochroniarz(nazwisko,imie,id_user);
                        listaDostepnych.add(temp);
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
            if (pDialog2.isShowing())
                pDialog2.dismiss();
        }
    }


    /**
     *  Data holder dla ochroniarza
     */

    private class Ochroniarz
    {
        public Ochroniarz(String nazwisko, String imie, int id) {
            this.nazwisko = nazwisko;
            this.imie = imie;
            this.id = id;
        }

        int id;
        String imie;
        String nazwisko;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNazwisko() {
            return nazwisko;
        }

        public void setNazwisko(String nazwisko) {
            this.nazwisko = nazwisko;
        }

        public String getImie() {
            return imie;
        }

        public void setImie(String imie) {
            this.imie = imie;
        }
    }
}
