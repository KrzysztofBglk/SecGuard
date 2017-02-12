package projekt.secguard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminAddCompanyActivity extends AppCompatActivity {
    EditText nazwaFirmy;
    EditText telefon;
    EditText osobaKontaktowa;
    Button dodaj;
    Button sprawdzCzyWstepuje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_company);
        nazwaFirmy = (EditText)findViewById(R.id.et_add_name);
        telefon = (EditText)findViewById(R.id.et_add_phone);
        osobaKontaktowa = (EditText)findViewById(R.id.et_add_osobaKontaktowa);
        dodaj = (Button)findViewById(R.id.button_add);
        sprawdzCzyWstepuje = (Button)findViewById(R.id.button_check);


        sprawdzCzyWstepuje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //json
            }
        });


        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //json
            }
        });

    }
}
