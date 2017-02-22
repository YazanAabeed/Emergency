package emergency.app.emergency.emergency;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import server.service;



public class Add_pateint extends ActionBarActivity {

    service webServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pateint);

        webServer = new service();

        final Spinner address = (Spinner) findViewById(R.id.address);

        final Spinner situation = (Spinner) findViewById(R.id.sit);

        Button logOut = (Button) findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logOutToLandingPage = new Intent(getApplicationContext(), landing_page.class);
                startActivity(logOutToLandingPage);
            }
        });

        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("Ramallah");
        list.add("Albierh");
        list.add("abu des");

        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        address.setAdapter(adapter);

        ArrayAdapter<String> adapter1;
        List<String> list1;

        list1 = new ArrayList<String>();
        list1.add("Critical");
        list1.add("Normal");
        list1.add("Minor");
        adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        situation.setAdapter(adapter1);

        ImageButton add_pateint = (ImageButton) findViewById(R.id.add_pateint);
        add_pateint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText idnumber = (EditText) findViewById(R.id.patientId);
                EditText first_name = (EditText) findViewById(R.id.firstName);
                EditText last_name = (EditText) findViewById(R.id.lastName);
                EditText mother_name = (EditText) findViewById(R.id.motherName);


                String addressTxt = address.getSelectedItem().toString();


                String situationtxt = situation.getSelectedItem().toString();

                RadioGroup gender = (RadioGroup) findViewById(R.id.gender);

                int selected = gender.getCheckedRadioButtonId();

                RadioButton genderType = (RadioButton) findViewById(selected);

                RadioButton selectedButton = (RadioButton) findViewById(R.id.male);
                selectedButton.setSelected(true);

                String genderTXT = genderType.getText().toString();


                String idNumberTxt = idnumber.getText().toString();
                String firstNameTxt = first_name.getText().toString();
                String lastNameTxt =  last_name.getText().toString();
                String motherNameTxt = mother_name.getText().toString();
                ArrayList errors = new ArrayList<Boolean>();
                errors.clear();


                if (idNumberTxt.trim().isEmpty()){
                    idnumber.setHint("Id Number");
                    idnumber.setHintTextColor(Color.rgb(0, 69,94));
                    idnumber.setTextColor(Color.rgb(0, 69,94));
                    idnumber.setBackgroundResource(R.drawable.wrong);
                    errors.add(true);
                }else{
                    idnumber.setHint("");
                    idnumber.setBackgroundResource(R.drawable.posttext);
                    idnumber.setTextColor(Color.WHITE);
                    idnumber.setTextColor(Color.rgb(0, 69,94));
                    errors.add(false);
                }

                if (firstNameTxt.trim().isEmpty()){
                    first_name.setHint("First Name");
                    first_name.setHintTextColor(Color.rgb(0, 69,94));
                    first_name.setTextColor(Color.rgb(0, 69,94));
                    first_name.setBackgroundResource(R.drawable.wrong);
                    errors.add(true);
                }else{
                    first_name.setHint("");
                    first_name.setBackgroundResource(R.drawable.posttext);
                    first_name.setTextColor(Color.rgb(0, 69,94));
                    errors.add(false);
                }

                if (lastNameTxt.trim().isEmpty()){
                    last_name.setHint("Last Name");
                    last_name.setHintTextColor(Color.rgb(0, 69,94));
                    last_name.setTextColor(Color.rgb(0, 69,94));
                    last_name.setBackgroundResource(R.drawable.wrong);
                    errors.add(true);
                }else{
                    last_name.setHint("");
                    last_name.setBackgroundResource(R.drawable.posttext);
                    last_name.setTextColor(Color.rgb(0, 69,94));
                    errors.add(false);
                }

                if (motherNameTxt.trim().isEmpty()){
                    mother_name.setHint("Mother Name");
                    mother_name.setHintTextColor(Color.rgb(0, 69,94));
                    mother_name.setTextColor(Color.rgb(0, 69,94));
                    mother_name.setBackgroundResource(R.drawable.wrong);
                    errors.add(true);
                }else{
                    mother_name.setHint("");
                    mother_name.setBackgroundResource(R.drawable.posttext);
                    mother_name.setTextColor(Color.rgb(0, 69,94));
                    errors.add(false);
                }

                if (!errors.contains(true))
                    add_pateint( idNumberTxt, situationtxt,  firstNameTxt, lastNameTxt,  motherNameTxt,  addressTxt,  genderTXT);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_pateint, menu);
        return true;
    }

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

    private void add_pateint(String idnumber,String situation, String first_name, String last_name, String mother_name, String address, String gender){

        RequestParams patient = new RequestParams();

        patient.put("natid", idnumber);
        patient.put("pname", first_name+"   "+ last_name);
        patient.put("last_name", last_name);
        patient.put("mother_name", mother_name);
        patient.put("address", address);
        patient.put("gender", gender);
        patient.put("pstat", situation);

        webServer.post("http://172.30.153.71/emergphp/addmove.php", patient, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    if (response.getBoolean("response")) {
                        Intent add_patient = new Intent(getApplicationContext(),Add_pateint.class);
                        startActivity(add_patient);
                        Toast done = Toast.makeText(getApplicationContext(), "insert successfully", Toast.LENGTH_LONG);
                        done.show();
                    } else {
                        Toast error = Toast.makeText(getApplicationContext(), "oops! Wrong Password Or username", Toast.LENGTH_LONG);
                        error.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            protected void setOrientation() {
                int current = getRequestedOrientation();
                // only switch the orientation if not in portrait
                if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
                    setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast error = Toast.makeText(getApplicationContext(), "oops! Some Error", Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast error = Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG);
            }


        });

    }
}
