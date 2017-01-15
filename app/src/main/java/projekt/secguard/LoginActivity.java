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
import java.util.ArrayList;
import android.util.Log;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    /** Main menu intent*/
    Intent intentMainMenu = new Intent(this, MainActivity.class);
    /** Remote data*/
    String url = "http://185.28.100.205/login.php?login=";
    String url_pass = "&pass=";
    private ProgressDialog pDialog;
    private String TAG = MainActivity.class.getSimpleName();
    /** -- */

    Button btLogin; // button do logowania
    EditText etLogin,etPass; // pola do logowania
    String login,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLogin = (EditText)findViewById(R.id.et_login);
        etPass =(EditText)findViewById(R.id.et_pass);
        btLogin = (Button) findViewById(R.id.button_login);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = etLogin.getText().toString();
                pass = etPass.getText().toString();
                url = url + login + url_pass + pass;
                new LogIn().execute();
            }
        });


    }
    private class LogIn extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Request
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr); // debug response

            if (jsonStr != null) {
                try {
                    JSONArray json = new JSONArray(jsonStr);

                    /*
                    TODO ODPAKOWANIE JSONA !

                     */
                    //int id = json.getString("id"); // error


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


/*       error?

                startActivity(intentMainMenu); // if login != null(dane poprawne) => main menu
                finish();

*/

            } else {
                Log.e(TAG, "Blad pobierania danych z serwera!");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Blad, LogCat zawiera crash report",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        }

}


