package com.copia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditFormActivity extends AppCompatActivity {
    @BindView(R.id.app_bar)
    Toolbar toolBar;
    @BindView(R.id.message)
    TextView tvMessage;
    @BindView(R.id.submit)
    Button btnSubmit;
    @BindView(R.id.email)
    EditText edUserName;
    @BindView(R.id.password)
    EditText edPassword;
    ProgressDialog PD;
    UserDataBase userDB;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);
        ButterKnife.bind(this);
        userDB = new UserDataBase(this);
        username = userDB.getUserInformation().get(0).getUserName();
        password = userDB.getUserInformation().get(0).getPassword();
        views();
    }

    private void views() {
        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(true);
        setUpToolBar("");
        edUserName.setText(username);
        edPassword.setText(password);
    }

    private void setUpToolBar(String title) {
        setSupportActionBar(toolBar);
        //add back icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @OnClick(R.id.submit)
    void submitBtnClicked() {
        if (TextUtils.isEmpty(edUserName.getText().toString())) {
            edUserName.setError(getString(R.string.error_username));
            edPassword.setError(null);
        } else if (TextUtils.isEmpty(edPassword.getText().toString())) {
            edPassword.setError(getString(R.string.error_password));
            edUserName.setError(null);
        } else {
            edUserName.setError(null);
            edPassword.setError(null);
            username = edUserName.getText().toString();
            password = edPassword.getText().toString();
            startRequest();
        }
    }

    private void startRequest() {
        String api_endpoint = "http://174.138.54.223:91/api_v1/promoter_login/" + username + "/" + password;
        PD.show();
        tvMessage.setVisibility(View.INVISIBLE);
        StringRequest getRequest = new StringRequest(Request.Method.GET, api_endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        Log.d("response_submit", response);
                        //parse response json
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String statusCode = jsonObject.getString("status_code");
                            String message = jsonObject.getString("message");
                            tvMessage.setText("Status: " + statusCode + "\n" + "Message: " + message);
                            tvMessage.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response_submit_error", error.toString());
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Failed to send, Please try again", Toast.LENGTH_LONG).show();
                tvMessage.setText(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
