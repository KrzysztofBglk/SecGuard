package projekt.secguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AdminProfilesEditActivity extends AppCompatActivity {

    private Button buttonUsersList;
    private String login;
    private UserData userData;

    private TextView userLogin;
    private TextView userName;
    private TextView userRank;


    private EditText edFName, edSName, edPass, edPassCurrent;

    private Button buttonEdPass, buttonEdFname, buttonEdSname;



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



        buttonUsersList = (Button) findViewById(R.id.button4);
        buttonUsersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProfileListViewActivity.class);
                i.putExtra("userData", userData);
                startActivity(i);
            }
        });


        }

    protected void onResume()
    {
        super.onResume();
        Intent intent = getIntent();
        userData = (UserData) intent.getExtras().getSerializable("userData");

        userLogin.setText(userData.getLogin());
        userName.setText(userData.getImie()+" "+userData.getNazwisko());
        userRank.setText(userData.getPozycja());


    }
}
