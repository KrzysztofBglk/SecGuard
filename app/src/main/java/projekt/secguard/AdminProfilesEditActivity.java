package projekt.secguard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
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

import com.google.android.gms.common.data.DataHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdminProfilesEditActivity extends AppCompatActivity {

    private Button buttonUsersList;
    private String login;
    private UserData userData;

    private TextView userLogin;
    private TextView userName;
    private TextView userRank;

    String url_pass = "&pass=";
    String param = "&param=";

    private EditText edFName, edSName, edPass, edPassCurrent;

    private Button buttonEdPass, buttonEdFname, buttonEdSname;

    private String TAG = AdminAddLocationActivity.class.getSimpleName();

    ProgressDialog pDialog;

    int context_id, selector;

    String url;

    ArrayList<UserData> users;
    UserData selectedUser = new UserData(0,null,null,null,null,null,null);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profiles_edit);

        final Intent intent = getIntent();
        userData = (UserData) intent.getExtras().getSerializable("userData");

        userLogin = (TextView) findViewById(R.id.textView23);
        userName = (TextView) findViewById(R.id.textView24);
        userRank = (TextView) findViewById(R.id.textView25);

        edPass = (EditText) findViewById(R.id.editText10);
        edFName = (EditText) findViewById(R.id.editText11);
        edSName = (EditText) findViewById(R.id.editText12);

        userLogin.setText(userData.getLogin());
        userName.setText(userData.getImie() + " " + userData.getNazwisko());
        userRank.setText(userData.getPozycja());


        new selectAllUserNames().execute();
        buttonUsersList = (Button) findViewById(R.id.button4);
        buttonUsersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new selectAllUserNames().execute();

                context_id = 1;
                registerForContextMenu(buttonUsersList);
                openContextMenu(buttonUsersList);
            }
        });

        buttonEdPass = (Button) findViewById(R.id.button7);
        buttonEdPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordConditions(edPassCurrent.getText().toString(), edPass.getText().toString())) {
                    //Wysłanie polecenia zmiany bieżącego hasła konta(nowe hasło to wartość pola edPass

                    url = "http://185.28.100.205/ch_pass.php?login=";

                    url = url + selectedUser.getLogin() + url_pass + selectedUser.getHaslo() + param + edPass.getText().toString();

                    new pushChange().execute();
                }
            }
        });


        buttonEdFname = (Button) findViewById(R.id.button8);
        buttonEdFname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (namesConditions(edFName.getText().toString())) {
                    //Wysłanie polecenia zmiany bieżącego imienia

                    url = "http://185.28.100.205/ch_fname.php?login=";

                    url = url + selectedUser.getLogin() + url_pass + selectedUser.getHaslo() + param + edFName.getText().toString();

                    new pushChange().execute();
                }
            }

        });

        buttonEdSname = (Button) findViewById(R.id.button9);
        buttonEdSname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namesConditions(edSName.getText().toString())) {
                    //Wysłanie polecenia zmiany bieżącego nazwiska

                    url = "http://185.28.100.205/ch_sname.php?login=";

                    url = url + selectedUser.getLogin() + url_pass + selectedUser.getHaslo() + param + edSName.getText().toString();

                    new pushChange().execute();
                }
            }
        });


        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {

                        userData = (UserData) data.getExtras().getSerializable("userData");
                        userLogin.setText(userData.getLogin());
                        userName.setText(userData.getImie()+" "+userData.getNazwisko());
                        userRank.setText(userData.getPozycja());
                }
                break;
            }

        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        selector = item.getItemId();
        TextView textType = (TextView) findViewById(R.id.textView43);
        TextView textCompany = (TextView) findViewById(R.id.textView45);

        if(context_id == 1) {
            login = users.get(selector).getLogin();
            selectedUser.setLogin(login);
            selectedUser.setId(users.get(selector).getId());

            new selectAllInfoAbAcc().execute();
            SystemClock.sleep(1000);

            userLogin.setText(selectedUser.getLogin());
            userName.setText(selectedUser.getImie() + " " + selectedUser.getNazwisko());
            userRank.setText(selectedUser.getPozycja());
        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (context_id == 1) {
            int iterator_id = 0;
            menu.setHeaderTitle("Wybierz użytkownika");
            for (UserData d : users) {
                menu.add(0, iterator_id, 0, d.getLogin());
                iterator_id++;
            }
        }
    }

    private class selectAllUserNames extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Dialog oczekiwania na polaczenie i sprawdzenie danych z serwerm
            pDialog = new ProgressDialog(AdminProfilesEditActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            Intent intent = getIntent();
            UserData userData = (UserData) intent.getExtras().getSerializable("userData");


            url = "http://185.28.100.205/getAllUserNames.php?hashcode=J1f2sa0sdi3Awj349";

            // Request na serwer
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Odebrano: " + jsonStr);

                    if (jsonStr != null) {
                        users = new ArrayList<UserData>();
                        try {
                            JSONArray mJsonArray = new JSONArray(jsonStr);
                            JSONObject mJsonObject = new JSONObject();


                            for (int i = 0; i < mJsonArray.length(); i++) {
                                mJsonObject = mJsonArray.getJSONObject(i);
                                String id, n;

                                id = mJsonObject.getString("id_user");
                                n = mJsonObject.getString("login");

                                UserData d = new UserData(Integer.parseInt(id), n, null, null, null, null, null);
                                users.add(d);
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
            pDialog = new ProgressDialog(AdminProfilesEditActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            url = "http://185.28.100.205/getAllUserData_admin.php?hashcode=J1f2sa0sdi3Awj349&id_user="+selectedUser.getId();

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
                    selectedUser = new UserData(Integer.parseInt(id), login, haslo, imie, nazwisko, pozycja, Boolean.parseBoolean(status));

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

    private class pushChange extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Dialog oczekiwania na polaczenie i sprawdzenie danych z serwerm
            pDialog = new ProgressDialog(AdminProfilesEditActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

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



                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Niepoprawne dane logowania 2",
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

    Boolean passwordConditions(String currentPass, String newPass) //warunki określające prawidłową formę hasła - jeżeli wszystko zostanie spełnione zostanie zwrócona wartość true i zostanie umożliwione wciśnięcie przycisku powodującego edycję i wysłanie polecenia SQL
    {
        if (currentPass.compareTo(newPass) == 0) {
            Toast.makeText(getApplicationContext(),
                    "Hasła nie różnią się.",
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        if (newPass.length() < 5) {
            Toast.makeText(getApplicationContext(),
                    "Hasło jest za krótkie.",
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }


        //Jeżeli hasło wpisane w polu aktualne hasło nie zgadza się z hasłem aktualnie zalogowanego użytkownika, zwróć false
        if (currentPass.compareTo(userData.getHaslo()) != 0){
            Toast.makeText(getApplicationContext(),
                    "Niepoprawne hasło bieżące.",
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }


        return true;
    }

    Boolean namesConditions(String name)  //warunki określające prawidłową formę imienia oraz nazwiska - jeżeli wszystko zostanie spełnione zostanie zwrócona wartość true i zostanie umożliwione wciśnięcie przycisku powodującego edycję i wysłanie polecenia SQL
    {
        if (name.length() < 2){
            Toast.makeText(getApplicationContext(),
                    "Imię za krótkie.",
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        if (name.length() > 55){
            Toast.makeText(getApplicationContext(),
                    "Imię za długie.",
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        //65-90 97-123
        //Warunek sprawdzajacy czy kazdy znak wpisanego imienia/nazwiska miesci sie w wartosci znakow alfabetycznych tablicy znakow ASCII
        /*for (int i = 0; i < name.length(); i++) {
            //Słownie - jeżeli znak nie mieści się pomiędzy wartością 65 a 90 - I - nie mieści się pomiędzy wartością 97 a 123 to ->
            if ( ((name.toCharArray()[i]>65) && (name.toCharArray()[i]<90)) || ((name.toCharArray()[i] > 97) && (name.toCharArray()[i] < 123)) ){
                return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "Niepoprawne znaki!",
                        Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        }*/


        return true;
    }
}
