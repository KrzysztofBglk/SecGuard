package projekt.secguard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileListViewActivity extends AppCompatActivity {

    private ListView list ;
    private ArrayAdapter<String> adapter ;

    String url;
    String url_pass = "&pass=";
    String param = "&param=";
    String login, pass;
    private ProgressDialog pDialog;
    private String TAG = MainActivity.class.getSimpleName();
    String usersList[];
    String selectedLogin;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list_view);

        final Intent intent = getIntent();
        userData = (UserData) intent.getExtras().getSerializable("userData");

        list = (ListView) findViewById(R.id.listView);
        new selectAllUserNames().execute();


        SystemClock.sleep(1000);
        ArrayList<String> carL = new ArrayList<String>();
        carL.addAll( Arrays.asList(usersList) );
        adapter = new ArrayAdapter<String>(this, R.layout.one_user_data_textview, carL);
        list.setAdapter(adapter);

        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = list.getItemAtPosition(position);
                selectedLogin=(String)o;//As you are using Default String Adapter
                new selectAllInfoAbAcc().execute();


                finish();
            }
        });

    }

    private class selectAllUserNames extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Dialog oczekiwania na polaczenie i sprawdzenie danych z serwerm
            pDialog = new ProgressDialog(ProfileListViewActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            Intent intent = getIntent();
            UserData userData = (UserData) intent.getExtras().getSerializable("userData");
            login = userData.getLogin();
            pass = userData.getHaslo();

            url = "http://185.28.100.205/getAllUserNames.php?login="+login+"&pass="+pass;

            // Request na serwer
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Odebrano: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStr);
                    JSONObject mJsonObject = new JSONObject();

                    usersList = new String[mJsonArray.length()];
                    for(int i = 0 ; i < mJsonArray.length() ; i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        usersList[i] = mJsonObject.getString("login");

                        System.out.print("HALO HALOOOOOOOOOO: " + usersList.length);

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

    private class selectAllInfoAbAcc extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Dialog oczekiwania na polaczenie i sprawdzenie danych z serwerm
            pDialog = new ProgressDialog(ProfileListViewActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            url = "http://185.28.100.205/getAllUserData_admin.php?login="+userData.getLogin()+"&pass="+userData.getHaslo()+"&param="+selectedLogin;

            // Request na serwer
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Odebrano: " + jsonStr);

            if (jsonStr != null) {
                try {

                    // Przetwarzanie JSON => UserData.class
                    JSONArray daneUsera = new JSONArray(jsonStr);
                    JSONObject o = daneUsera.getJSONObject(0); // 0 or 1

                    String id = o.getString("id_user");
                    String login = o.getString("login");
                    String haslo = o.getString("haslo");
                    String imie = o.getString("imie");
                    String nazwisko = o.getString("nazwisko");
                    String pozycja = o.getString("pozycja");
                    String status = o.getString("status");
                    UserData data = new UserData(Integer.parseInt(id), login, haslo, imie, nazwisko, pozycja, Boolean.parseBoolean(status));

                    //ADRESY GDZIE WYSLAC GETEXTRA
                    //
                    //
                    //
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("userData", data);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();




                    //
                    //
                    ////////////////////////////////

                    finish();

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

