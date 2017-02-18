package projekt.secguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


//TODO NAPISAC PHP ODEBRAC JSON

/**
 * Kasa dodajaca nowe firmy do bazy danych dla których można wykonywać zlecenia
 */
public class AdminAddDataActivity extends AppCompatActivity {
    Button addCompany;
    Button menageCompany;
    Button addObjects;
    Button menageObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_data);
         addCompany = (Button)findViewById(R.id.button_addCompany);
         menageCompany = (Button)findViewById(R.id.button_menageCompany);
         addObjects = (Button)findViewById(R.id.button_addObjects);
         menageObjects = (Button)findViewById(R.id.button_menageObjects);

        addCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddCompany = new Intent(getApplicationContext(), AdminAddCompanyActivity.class);
                startActivity(intentAddCompany);
            }
        });

        menageCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMenageCompany = new Intent(getApplicationContext(), AdminMenageCompanyActivity.class);
                startActivity(intentMenageCompany);
            }
        });
        addObjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentAddLocation = new Intent(getApplicationContext(), AdminAddLocationActivity.class);
                startActivity(intentAddLocation);
            }
        });
        menageObjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentManageLocation = new Intent(getApplicationContext(), AdminEditLocationActivity.class);
                startActivity(intentManageLocation);
            }
        });









    }
}
