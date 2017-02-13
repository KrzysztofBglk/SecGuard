package projekt.secguard;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserScreenActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    private TextView userLogin;
    private TextView userStanowisko;
    private TextView userImie;
    private TextView userNazwisko;
    private UserData userData;
    private ImageView avatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);
        userLogin = (TextView) findViewById(R.id.textLogin);
        userStanowisko= (TextView) findViewById(R.id.txt_dane_stanowisko);
        userImie = (TextView) findViewById(R.id.txt_dane_imie);
        userNazwisko = (TextView) findViewById(R.id.txt_dane_nazwisko);
        avatar = (ImageView)  findViewById(R.id.imageView);

        // Uzyskiwanie danych o logowaniu z klasy UserData
        Intent intent = getIntent();
        userData = (UserData) intent.getExtras().getSerializable("userData");

        userLogin.setText(userData.getLogin());

        userStanowisko.setText(userData.getPozycja());
        if(userData.getPozycja().equals("menager")){
            userStanowisko.setTextColor(getResources().getColor(R.color.red));
        }
        if(userData.getPozycja().equals("ochroniarz")){
            userStanowisko.setTextColor(getResources().getColor(R.color.green));
        }

        userImie.setText(userData.getImie());
        userNazwisko.setText(userData.getNazwisko());


        if(userData.getLogin().equals("bozena")){
            avatar.setImageResource(R.drawable.avatar_bozena);
        }
        if(userData.getLogin().equals("mar")){
            avatar.setImageResource(R.drawable.avatar_grzegorz);
        }


        // Boczne menu
        setMenuOptions();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// back button
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.menu_editUsers:
                        Intent editUsers = new Intent(getApplicationContext(), AdminProfilesEditActivity.class);
                        editUsers.putExtra("userData", userData);
                        startActivity(editUsers);
                        return true;
                    case R.id.menu_menageObjects:
                        Intent menageObjects = new Intent(getApplicationContext(), AdminAddDataActivity.class);
                        startActivity(menageObjects);
                        return true;
                    case R.id.menu_orders:
                        Intent orders = new Intent(getApplicationContext(), AdminOrdersActivity.class);
                        startActivity(orders);
                        return true;
                    case R.id.menu_finishedOrders:
                        Intent finishedOrders = new Intent(getApplicationContext(), AdminFinishedOrdersActivity.class);
                        startActivity(finishedOrders);
                        return true;
                    case R.id.menu_myOrders:
                        Intent myOrders = new Intent(getApplicationContext(), UserOrdersTableActivity.class);
                        startActivity(myOrders);
                        return true;
                    case R.id.menu_tracking:
                        Intent tracking = new Intent(getApplicationContext(), UserTrackerActivity.class);
                        startActivity(tracking);
                        return true;
                    case R.id.menu_editProfile:
                        Intent editProfile = new Intent(getApplicationContext(), ProfileEditActivity.class);
                        editProfile.putExtra("userData", userData);
                        startActivity(editProfile);
                        return true;
                    case R.id.menu_exit:
                        Intent exit = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(exit);
                        return true;
                    default:
                        return true;
                }
            }
        } );

    }


    // Dostosowanie menu pod uprawnienia usera
    private void setMenuOptions()
    {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
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
