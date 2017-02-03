package projekt.secguard;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class UserScreenActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

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



        // Boczne menu
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// back button
    }

    // Podpięcie akcji rozwijania i zwijania menu z poziomu ikony górnego menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
