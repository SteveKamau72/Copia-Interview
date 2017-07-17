package com.copia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.full_name)
    EditText edFullName;
    @BindView(R.id.email)
    EditText edEmail;
    @BindView(R.id.phone)
    EditText edPhone;
    @BindView(R.id.password)
    EditText edPassword;
    @BindView(R.id.username)
    EditText eduserName;
    UserDataBase userDataBase;

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        userDataBase = new UserDataBase(this);
    }

    @OnClick(R.id.log_in)
    void login() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @OnClick(R.id.register)
    void registerUser() {
        //check for empty inputs
        if (TextUtils.isEmpty(edFullName.getText())) {
            edFullName.setError(getString(R.string.error_full_name));
            edEmail.setError(null);
            edPhone.setError(null);
            eduserName.setError(null);
            edPassword.setError(null);
        } else if (TextUtils.isEmpty(eduserName.getText())) {
            eduserName.setError(getString(R.string.error_username));
            edFullName.setError(null);
            edEmail.setError(null);
            edPhone.setError(null);
            edPassword.setError(null);
        } else if (TextUtils.isEmpty(edEmail.getText())) {
            edEmail.setError(getString(R.string.error_email));
            edFullName.setError(null);
            edPhone.setError(null);
            eduserName.setError(null);
            edPassword.setError(null);
        } else if (!isEmailValid(edEmail.getText().toString())) {
            edEmail.setError(getString(R.string.invalid_email));
            edFullName.setError(null);
            edPhone.setError(null);
            eduserName.setError(null);
            edPassword.setError(null);
        } else if (TextUtils.isEmpty(edPhone.getText())) {
            edPhone.setError(getString(R.string.error_phone));
            edFullName.setError(null);
            edEmail.setError(null);
            eduserName.setError(null);
            edPassword.setError(null);

        } else if (TextUtils.isEmpty(edPassword.getText())) {
            edPassword.setError(getString(R.string.error_password));
            edFullName.setError(null);
            edEmail.setError(null);
            edPhone.setError(null);
            eduserName.setError(null);
        } else {
            //inputs not empty, register user
            edFullName.setError(null);
            edEmail.setError(null);
            edPhone.setError(null);
            eduserName.setError(null);
            edPassword.setError(null);
            registerRequest();
        }
    }

    private void registerRequest() {
        //add user to sqlite
        userDataBase.addUser(edFullName.getText().toString(), eduserName.getText().toString(),
                edPhone.getText().toString(), edEmail.getText().toString(), edPassword.getText().toString());
        Toast.makeText(this, "Successfully registered!", Toast.LENGTH_SHORT).show();
        startLoginUser();
    }

    private void startLoginUser() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.putExtra("username", eduserName.getText().toString());
        i.putExtra("password", edPassword.getText().toString());
        startActivity(i);
        finish();
    }
}
