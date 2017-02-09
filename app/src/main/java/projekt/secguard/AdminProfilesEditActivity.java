package projekt.secguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminProfilesEditActivity extends AppCompatActivity {

    private Button buttonUsersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profiles_edit);

        buttonUsersList = (Button) findViewById(R.id.button4);
        buttonUsersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ProfileListViewActivity.class);
                startActivity(intent);
            }
        });


        }
}
