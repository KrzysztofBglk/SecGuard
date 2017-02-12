package projekt.secguard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminMenageCompanyActivity extends AppCompatActivity {
    EditText edit_name;
    EditText edit_phone;
    EditText edit_kontakt;
    Button button_choice;
    Button button_modify;
    ProgressDialog pDialog;
    int selector;
    String dbase_id;
    String edited_name;
    String edited_phone;
    String edited_kontakt;
    private String TAG = AdminMenageCompanyActivity.class.getSimpleName();
    ArrayList <DataHolder> companyData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menage_company);
        edit_name = (EditText)findViewById(R.id.editCompany_name);
        edit_phone = (EditText)findViewById(R.id.editCompany_phone);
        edit_kontakt = (EditText)findViewById(R.id.editCompany_kontakt);
        button_choice = (Button)findViewById(R.id.button_choiceCompany);
        button_modify = (Button)findViewById(R.id.button_modify);
        new getAll().execute();
        button_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getAll().execute();
                registerForContextMenu(button_choice);
                openContextMenu(button_choice);
            }
        });

        button_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edited_name = edit_name.getText().toString();
                edited_phone = edit_phone.getText().toString();
                edited_kontakt = edit_kontakt.getText().toString();
                if( (valid_name(edited_name) == true) && (valid_phone(edited_phone) == true) && (valid_kontakt(edited_kontakt) == true) ){
                    new updateCompany().execute();
                }
            }
        });
    }

    Boolean valid_name(String name) {
        if(!name.equals(companyData.get(selector).getHold_name())){
            if(name.length() !=0 && name.length() <25){
                if(name.matches("[a-zA-Z]+")){
                    return true;
                }
            }
        }
        return false;
    }

    Boolean valid_phone(String phone) {
        if((phone.length() != 9 || phone.length() != 7)){
            try {
                double d = Double.parseDouble(phone);
            }
            catch(NumberFormatException nfe) {
                return false;
            }
            return true;
        }
        return false;
    }


    Boolean valid_kontakt(String name) {
        if(!name.equals(companyData.get(selector).getHold_name())){
            if(name.length() !=0 && name.length() <25){
                if(name.matches("[a-zA-Z_\\s]+")){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Wybierz firme");
        int iterator_id = 0;
        for(DataHolder d: companyData){
            menu.add(0, iterator_id, 0,d.getHold_name());
            iterator_id++;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

       // AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
   //     selector = info.position;
        selector = item.getItemId();

/*        Toast.makeText(getApplicationContext(),
                selector,
                Toast.LENGTH_LONG)
                .show();
*/
        edit_name.setText(companyData.get(selector).getHold_name());
        edit_phone.setText(companyData.get(selector).getHold_phone());
        edit_kontakt.setText(companyData.get(selector).getHold_kontakt());
        dbase_id = companyData.get(selector).getHold_id();
        return true;
    }



    private class getAll extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Dialog oczekiwania na polaczenie i sprawdzenie danych z serwerm
            pDialog = new ProgressDialog(AdminMenageCompanyActivity.this);
            pDialog.setMessage("Przetwarzanie...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler getAll = new HttpHandler();
            // hash code zabezpiecza przed wyciekiem danych z serwera
            String urlCompanyData = "http://185.28.100.205/getAllCompany.php?hashcode=J1f2sa0sdi3Awj349";

            // Request na serwer
            String jsonStr = getAll.makeServiceCall(urlCompanyData);

            Log.e(TAG, "Odebrano: " + jsonStr);

            if (jsonStr != null) {
                companyData = new ArrayList<DataHolder>();
                try {
                    JSONArray mJsonArray = new JSONArray(jsonStr);
                    JSONObject mJsonObject = new JSONObject();


                    for(int i = 0 ; i < mJsonArray.length() ; i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        String id,n,p,k;

                        id = mJsonObject.getString("id_zleceniodawcy");
                        n = mJsonObject.getString("nazwa_firmy");
                        p = mJsonObject.getString("telefon");
                        k = mJsonObject.getString("osoba_kontakt");
                        DataHolder d = new DataHolder(id,n,p,k);
                        companyData.add(d) ;
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


    private class updateCompany extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler insert = new HttpHandler();
             String edited_name_modded = edited_name.replaceAll(" ", "%20");
             String edited_kontakt_modded = edited_kontakt.replaceAll(" ", "%20");

            String urlInsert = "http://185.28.100.205/updateCompany.php?hashcode=J1f2sa0sdi3Awj349&nazwa=" + edited_name_modded + "&telefon=" + edited_phone + "&kontakt=" + edited_kontakt_modded + "&id=" + dbase_id;
            String geting = insert.makeServiceCall(urlInsert);
            Log.e(TAG, "Odebrano: " + geting);
            return null;
        }
    }




    private class DataHolder
    {
        String hold_id;
        String hold_name;
        String hold_phone;
        String hold_kontakt;

        DataHolder(String i, String n,String p, String k){
             hold_id = i;
             hold_name = n;
             hold_phone = p;
             hold_kontakt = k;
        }
        public String getHold_id(){
            return hold_id;
        }
        public String getHold_kontakt() {
            return hold_kontakt;
        }

        public void setHold_kontakt(String hold_kontakt) {
            this.hold_kontakt = hold_kontakt;
        }

        public String getHold_name() {
            return hold_name;
        }

        public void setHold_name(String hold_name) {
            this.hold_name = hold_name;
        }

        public String getHold_phone() {
            return hold_phone;
        }

        public void setHold_phone(String hold_phone) {
            this.hold_phone = hold_phone;
        }
        public void setHold_id(String hold_id){
            this.hold_id = hold_id;
        }
    }
}
