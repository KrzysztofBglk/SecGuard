package projekt.secguard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import org.w3c.dom.Text;

public class ProfileEditActivity extends AppCompatActivity {

    private TextView userLogin;
    private TextView userName;
    private TextView userRank;
    private UserData userData;

    private EditText edFName, edSName, edPass, edPassCurrent;

    private Button buttonEdPass, buttonEdFname, buttonEdSname;

    String url = "http://185.28.100.205/login.php?login=";
    String url_pass = "&pass=";
    String condition = "&cond=";
    String param = "&param=";
    String login, pass;
    private ProgressDialog pDialog;
    private String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        userLogin = (TextView) findViewById(R.id.textView5);
        userName = (TextView) findViewById(R.id.textView6);
        userRank = (TextView) findViewById(R.id.textView7);

        edPassCurrent = (EditText) findViewById(R.id.editText);
        edPass = (EditText) findViewById(R.id.editText2);
        edFName = (EditText) findViewById(R.id.editText3);
        edSName = (EditText) findViewById(R.id.editText4);

        Intent intent = getIntent();
        userData = (UserData) intent.getExtras().getSerializable("userData");
        userLogin.setText(userData.getLogin());
        userName.setText(userData.getImie() + " " + userData.getNazwisko());
        userRank.setText(userData.getPozycja());
        final HttpHandler sh = new HttpHandler();


        login = userData.getLogin();
        pass = userData.getHaslo();



        buttonEdPass = (Button) findViewById(R.id.button);
        buttonEdPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordConditions(edPassCurrent.getText().toString(), edPass.getText().toString())) {
                    //Wysłanie polecenia zmiany bieżącego hasła konta(nowe hasło to wartość pola edPass
                    url = url + login + url_pass + pass + condition + "changePass" + param + edPass.getText().toString();


                    String jsonStr = sh.makeServiceCall(url);

                    if(serverRequest(jsonStr) == true)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Poprawnie zmieniono hasło.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Niepoprawne imie",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        buttonEdFname = (Button) findViewById(R.id.button2);
        buttonEdFname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namesConditions(edFName.getText().toString())) {
                    //Wysłanie polecenia zmiany bieżącego imienia
                    url = url + login + url_pass + pass + condition + "changeFName" + param + edFName.getText().toString();

                    String jsonStr = sh.makeServiceCall(url);

                    if(serverRequest(jsonStr) == true)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Poprawnie zmieniono imię.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Niepoprawne imie",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        buttonEdSname = (Button) findViewById(R.id.button3);
        buttonEdSname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namesConditions(edSName.getText().toString())) {
                    //Wysłanie polecenia zmiany bieżącego nazwiska
                    url = url + login + url_pass + pass + condition + "changeSName" + param + edSName.getText().toString();

                    String jsonStr = sh.makeServiceCall(url);

                    if(serverRequest(jsonStr) == true)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Poprawnie zmieniono nazwisko.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Niepoprawne imie",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    Boolean passwordConditions(String currentPass, String newPass) //warunki określające prawidłową formę hasła - jeżeli wszystko zostanie spełnione zostanie zwrócona wartość true i zostanie umożliwione wciśnięcie przycisku powodującego edycję i wysłanie polecenia SQL
    {
        if (currentPass.compareTo(newPass) == 0)
            return false;

        if (newPass.length() < 5)
            return false;

        if (newPass.length() > 25)
            return false;

        //Jeżeli hasło wpisane w polu aktualne hasło nie zgadza się z hasłem aktualnie zalogowanego użytkownika, zwróć false
        if (currentPass.compareTo(userData.getHaslo()) != 0)
            return false;


        return true;
    }

    Boolean namesConditions(String name)  //warunki określające prawidłową formę imienia oraz nazwiska - jeżeli wszystko zostanie spełnione zostanie zwrócona wartość true i zostanie umożliwione wciśnięcie przycisku powodującego edycję i wysłanie polecenia SQL
    {
        if (name.length() < 2)
            return false;

        if (name.length() > 55)
            return false;

        //Warunek sprawdzajacy czy kazdy znak wpisanego imienia/nazwiska miesci sie w wartosci znakow alfabetycznych tablicy znakow ASCII
        for (int i = 0; i < name.length(); i++) {
            //Słownie - jeżeli znak nie mieści się pomiędzy wartością 65 a 90 - I - nie mieści się pomiędzy wartością 97 a 123 to ->
            if ((!((name.toCharArray()[i] > 65) && (name.toCharArray()[i] < 90))) && (!((name.toCharArray()[i] > 97) && (name.toCharArray()[i] < 123))))
                return false;
        }


        return true;
    }

    Boolean serverRequest(String jsonStr)
    {
        if (jsonStr != null) {
            try {
                // Przetwarzanie JSON
                JSONArray msg = new JSONArray(jsonStr);
                JSONObject o = msg.getJSONObject(0); // 0 or 1

                String data = o.getString("msg");

                if(data.compareTo("SUCCESS") == 0)
                {
                    return true;
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
                return false;
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
            return false;
        }
        return false;
    }
}
