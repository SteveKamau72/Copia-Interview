package com.copia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.create_account)
    TextView tvCreateAccount;
    @BindView(R.id.password)
    EditText edPassword;
    @BindView(R.id.username)
    EditText eduserName;
    UserDataBase userDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        userDataBase = new UserDataBase(this);
        getIntentValues();
    }

    private void checkIfUserIsLoggedIn() {
        if (userDataBase.getUserCount() > 0) {
            //user exists
            startMainActivity();
        }
    }


    private void getIntentValues() {
        Intent i = getIntent();
        String username = i.getStringExtra("username");
        String password = i.getStringExtra("password");
        if (username != null && password != null) {
            eduserName.setText(username);
            edPassword.setText(password);
        } else {
            checkIfUserIsLoggedIn();
        }
    }

    @OnClick(R.id.create_account)
    void signUp() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.login)
    void loginUser() {
        //check for empty inputs
        if (TextUtils.isEmpty(eduserName.getText())) {
            eduserName.setError(getString(R.string.error_username));
        } else if (TextUtils.isEmpty(edPassword.getText())) {
            edPassword.setError(getString(R.string.error_password));
        } else {
            //inputs not empty, log user
            eduserName.setError(null);
            edPassword.setError(null);
            loginRequest();
        }
    }

    private void loginRequest() {
        //check if user exists in database
        if (userDataBase.isUserRegistered(eduserName.getText().toString(),
                edPassword.getText().toString())) {
            //user exists
            startMainActivity();

        } else {
            Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
