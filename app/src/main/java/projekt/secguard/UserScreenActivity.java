package projekt.secguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);


        final TextView userLogin = (TextView) findViewById(R.id.textLogin);
        final TextView userNames = (TextView) findViewById(R.id.textNames);

        Intent intent = getIntent();
        final UserData userData = (UserData) intent.getExtras().getSerializable("userData");
        userLogin.setText("@"+userData.getLogin());
        userNames.setText(userData.getImie()+" "+userData.getNazwisko());
    }
}
