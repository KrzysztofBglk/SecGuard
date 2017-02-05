package projekt.secguard;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class UserScreenActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView userLogin;
    private TextView userName;
    private TextView userRank;
    private UserData userData;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);
        userLogin = (TextView) findViewById(R.id.textLogin);
        userName = (TextView) findViewById(R.id.textNames);
        userRank = (TextView) findViewById(R.id.textRank);
        // Uzyskiwanie danych o logowaniu z klasy UserData
        Intent intent = getIntent();
        userData = (UserData) intent.getExtras().getSerializable("userData");
        userLogin.setText(userData.getLogin());
        userName.setText(userData.getImie()+" "+userData.getNazwisko());
        userRank.setText(userData.getPozycja());

        // Boczne menu
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// back button
        setMenuOptions();

    }

    // Podpięcie akcji rozwijania i zwijania menu z poziomu ikony górnego menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Dostosowanie menu pod uprawnienia usera
    private void setMenuOptions()
    {
        navigationView = (NavigationView) findViewById(R.id.navigation);
        Menu nav_Menu = navigationView.getMenu();
        if(userData.getPozycja().equals("menager")){
            nav_Menu.findItem(R.id.menu_myOrders).setVisible(false);
            nav_Menu.findItem(R.id.menu_tracking).setVisible(false);
        }
        if(userData.getPozycja().equals("ochroniarz")){
            nav_Menu.findItem(R.id.menu_editUsers).setVisible(false);
            nav_Menu.findItem(R.id.menu_menageObjects).setVisible(false);
            nav_Menu.findItem(R.id.menu_orders).setVisible(false);
            nav_Menu.findItem(R.id.menu_finishedOrders).setVisible(false);
        }
    }
}
