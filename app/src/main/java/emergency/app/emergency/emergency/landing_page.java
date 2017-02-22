package emergency.app.emergency.emergency;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.service;



public class landing_page extends ActionBarActivity {
    service webServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.screen, options);

        options.inSampleSize = calculateInSampleSiza(options,720, 1200);
        options.inJustDecodeBounds = false;

        ImageView imageBack = (ImageView) findViewById(R.id.backGroundId);

        imageBack.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bbb, options));






        webServer = new service();
        ImageButton login =(ImageButton)findViewById(R.id.Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText password = (EditText) findViewById(R.id.password);
                EditText userName = (EditText) findViewById(R.id.username);
                String pass = password.getText().toString();
                String user = userName.getText().toString();
                boolean errorNotifcationUserName = false, errorNotifcationPassword = false;

                if (pass.trim().isEmpty()){
                    password.setHint("Enter Password");
                    password.setBackgroundResource(R.drawable.wrong);
                    password.setHintTextColor(Color.WHITE);
                    password.setTextColor(Color.WHITE);
                    errorNotifcationPassword = true;
                }else{
                    password.setHint("Enter Password");
                    password.setBackgroundResource(R.drawable.edit);
                    password.setTextColor(Color.BLACK);
                    errorNotifcationPassword = false;
                }

                if (user.trim().isEmpty()){
                    userName.setHint("Enter Username");
                    userName.setBackgroundResource(R.drawable.wrong);
                    userName.setHintTextColor(Color.WHITE);
                    userName.setTextColor(Color.WHITE);
                    errorNotifcationUserName = true;
                }else{
                    userName.setHint("Enter Username");
                    userName.setBackgroundResource(R.drawable.edit);
                    userName.setTextColor(Color.BLACK);
                    errorNotifcationUserName = false;
                }


                if (!errorNotifcationPassword && !errorNotifcationUserName)
                    login(user, pass);
            }
        });
    }

    public static int calculateInSampleSiza(BitmapFactory.Options options, int regWidth, int regHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height >  regHeight || width  > regWidth){
            final int halfHeight = height /2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > regHeight && (halfWidth / inSampleSize) > regWidth){
                inSampleSize *=2;
            }
        }

        return inSampleSize;
    }


    private void login(String userName, String password){
        RequestParams officer = new RequestParams();
        officer.put("user", userName);
        officer.put("pass", password);

        webServer.post("http://172.30.153.71/emergency/android/android_officers", officer, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    if (response.getBoolean("response")) {
                        Intent add_pateint = new Intent(getApplicationContext(),Add_pateint.class);
                        startActivity(add_pateint);
                    } else {
                        Toast error = Toast.makeText(getApplicationContext(), "oops! Wrong Password Or username", Toast.LENGTH_LONG);
                        error.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                Toast error = Toast.makeText(getApplicationContext(), "erooooooor", Toast.LENGTH_LONG);
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
        getMenuInflater().inflate(R.menu.menu_landing_page, menu);
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
}
