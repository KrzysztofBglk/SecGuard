package projekt.secguard;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                Intent intent = new Intent(getApplicationContext(), ProfileListViewActivity.class);
                intent.putExtra("userData", userData);
                startActivityForResult(intent, 1);
            }
        });


        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {

                        userData = (UserData) data.getExtras().getSerializable("userData");
                        userLogin.setText(userData.getLogin());
                        userName.setText(userData.getImie()+" "+userData.getNazwisko());
                        userRank.setText(userData.getPozycja());

                }
                break;
            }

        }
    }
}
