package projekt.secguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import projekt.secguard.LoginActivity;
/** Glowne okno aplikacji */
public class MainActivity extends AppCompatActivity {
    Intent intentLogin; // login intent
    public UserData userData; // klasa z danymi usera


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }
}
