package projekt.secguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class AdminOrdersForLocationActivity extends AppCompatActivity {

    HashMap<String, String> hMap = new HashMap<>();

    TextView a;
    TextView kontakt_imie;
    TextView kontakt_telefon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders_for_location);

        a = (TextView)findViewById(R.id.textView33);
        kontakt_imie = (TextView)findViewById(R.id.tv_osoba_konakt);
        kontakt_telefon = (TextView)findViewById(R.id.tv_telefon_kontakt);
        Intent intent = getIntent();
        hMap =(HashMap) intent.getExtras().getSerializable("locationData");
        kontakt_imie.setText("Kontakt: "+ hMap.get("osoba_kontakt").toString());
        kontakt_telefon.setText("Telefon: " + hMap.get("telefon").toString());
        a.setText(hMap.values().toString());

    }
}
