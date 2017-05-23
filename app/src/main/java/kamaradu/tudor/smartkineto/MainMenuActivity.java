package kamaradu.tudor.smartkineto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static kamaradu.tudor.smartkineto.R.id.doctorButton;
import static kamaradu.tudor.smartkineto.R.id.parent;

// Nu mai folosesc asta momentan pentru ca nu mai fac cu main menu
public class MainMenuActivity extends AppCompatActivity {
    final BackendlessUser currentUser = Backendless.UserService.CurrentUser();
    private List<Users> mList;

    @Bind(R.id.contactsList)
    ListView contacts;
    @Bind(R.id.buttonAdd)
    Button buttonAdd;
    @Bind(R.id.doctorButton)
    Button doctorButton;


    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);

        //Todo aratat prof DT asta face lista

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String whereClause = "NOT email = '" + currentUser.getEmail() + "'";
                DataQueryBuilder dataQuery = DataQueryBuilder.create();
                dataQuery.setWhereClause(whereClause);

                Backendless.Persistence.of(Users.class).find(dataQuery, new AsyncCallback<List<Users>>() {
                    @Override
                    public void handleResponse(List<Users> response) {

                        contacts.setAdapter(new PacientAdaptor(MainMenuActivity.this, R.layout.contact_row, response));
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
            }
        });


        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String whereClause = "email = '" + currentUser.getEmail() + "'";
                DataQueryBuilder dataQuery = DataQueryBuilder.create();
                dataQuery.setWhereClause(whereClause);
                //ToDo Update property in db
                Backendless.Persistence.of(Users.class).find(dataQuery, new AsyncCallback<List<Users>>() {
                    @Override
                    public void handleResponse(List<Users> findResponse) {
                        Backendless.Persistence.save( findResponse.get(0), new AsyncCallback<Users>() {
                            @Override
                            // Todo This returns Handle Fault
                            public void handleResponse(Users response) {
                                response.setDoctor(true);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                flag = true;
                            }
                        });
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });


            }
        });
    }

//        contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String whereClause = "email = '" + mList.get(position).getName() + "'";
//                DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
//                dataQueryBuilder.setWhereClause(whereClause);
//                Backendless.Persistence.of(BackendlessUser.class).find(dataQueryBuilder, new AsyncCallback<List<BackendlessUser>>() {
//                    @Override
//                    public void handleResponse(List<BackendlessUser> response) {
//                        DeliveryOptions deliveryOptions = new DeliveryOptions();
//                        ArrayList<String> listaMea = new ArrayList<String>();
//                        listaMea.add(currentUser.getProperty("gcmToken").toString());
//
//                        deliveryOptions.setPushSinglecast(listaMea);
//
//                        PublishOptions publishOptions = new PublishOptions();
//
//                        publishOptions.putHeader("android-ticker-text", "You just got a push notification!");
//                        publishOptions.putHeader("android-content-title", "This is a notification title");
//
//                        MessageStatus status = Backendless.Messaging.publish("Hi Devices!", publishOptions, deliveryOptions);
//                    }
//                    @Override
//                    public void handleFault(BackendlessFault fault) {
//
//                    }
//                });
//            }
//        });


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }


}
/*
     _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
 */