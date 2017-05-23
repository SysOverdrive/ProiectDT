package kamaradu.tudor.smartkineto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.DeviceRegistration;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.property.UserProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    public static String token;

    @Bind(R.id.contactListButton)
    Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Backendless.setUrl( BackendlessDefaults.SERVER_URL );
        Backendless.initApp(getApplicationContext(), BackendlessDefaults.APPLICATION_ID, BackendlessDefaults.API_KEY );

        Toast.makeText(MainActivity.this, "This is the MainActivity", Toast.LENGTH_SHORT).show();

        ButterKnife.bind(this);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        if(Backendless.UserService.CurrentUser() == null) {
            startActivity(intent);
        }

        Backendless.UserService.describeUserClass( new AsyncCallback<List<UserProperty>>()
        {
            public void handleResponse( List<UserProperty> properties )
            {
                for( UserProperty userProp : properties )
                {
                    Log.i( "LaboratorDT", "Property name - " + userProp.getName());
                    Log.i( "LaboratorDT", "\trequired - " + userProp.isRequired());
                    Log.i( "LaboratorDT", "\tidentity - " + userProp.isIdentity());
                    Log.i( "LaboratorDT", "\tdata type - " + userProp.getType());
                }
            }

            public void handleFault( BackendlessFault fault )
            {
            }
        });

        Backendless.Messaging.getDeviceRegistration(new AsyncCallback<DeviceRegistration>() {
            @Override
            public void handleResponse(DeviceRegistration response) {
                token = response.getDeviceToken();
                Log.d("TOKEN",token);
            }
            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
                    startActivity(intent);



            }
        });


//        myButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent buttonIntent = new Intent(MainActivity.this, MainMenuActivity.class);
//                startActivity(buttonIntent);
//            }
//        });
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


  _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

            //This should send us to main menu after the login
   //    Intent intentMainMenu = new Intent(this, MainMenuActivity.class);
    //    startActivity(intentMainMenu);
    */


}
