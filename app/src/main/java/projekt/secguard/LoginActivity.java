package projekt.secguard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    Button btLogin; // button do logowania
    EditText etLogin,etPass; // pola do logowania



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
                //DoIt(v);
            }
        });


    }
}
