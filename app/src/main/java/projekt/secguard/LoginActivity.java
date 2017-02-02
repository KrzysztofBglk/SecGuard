package projekt.secguard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

/**
 * Logowanie do aplikacji + pobranie danych o userze
 */
public class LoginActivity extends AppCompatActivity {

    String url = "http://185.28.100.205/login.php?login=";
    String url_pass = "&pass=";
    private ProgressDialog pDialog;
    private String TAG = MainActivity.class.getSimpleName();

    Button btLogin; // button do logowania
    EditText etLogin, etPass; // pola do logowania
    String login, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLogin = (EditText) findViewById(R.id.et_login);
        etPass = (EditText) findViewById(R.id.et_pass);
        btLogin = (Button) findViewById(R.id.button_login);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = etLogin.getText().toString();
                pass = etPass.getText().toString();
                url = url + login + url_pass + pass;
                new LogIn().execute();
                etLogin.setText("");
                etPass.setText("");
            }
        });
    }


    @Override // zapobiega cofaniu do main menu z poziomu logowania
    public void onBackPressed() {
        this.finishAffinity();
    }

    /**
     * Odpakowanie JSONa
     */
    private class LogIn extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Dialog masg
            pDialog = new ProgressDialog(LoginActivity.this);
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

                        Intent intent = new Intent(getApplicationContext(), UserScreenActivity.class);
                        intent.putExtra("userData", data);

                        startActivity(intent);
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
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }
}


