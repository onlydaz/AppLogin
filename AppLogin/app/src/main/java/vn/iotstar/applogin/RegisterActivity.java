package vn.iotstar.applogin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.applogin.api.ApiClient;
import vn.iotstar.applogin.api.ApiService;
import vn.iotstar.applogin.models.ApiResponse;
import vn.iotstar.applogin.models.OtpRequest;
import vn.iotstar.applogin.models.OtpVerifyRequest;
import vn.iotstar.applogin.models.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtEmail, edtUsername, edtPassword, edtRePassword, edtOtp;
    private Button btnSendOtp, btnConfirm;
    private String email, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtRePassword = findViewById(R.id.edtRePassword);
        edtOtp = findViewById(R.id.edtOtp);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnSendOtp.setOnClickListener(v -> sendOtp());
        btnConfirm.setOnClickListener(v -> verifyOtp());
    }

    private void sendOtp() {
        email = edtEmail.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Email is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        apiService.sendOtp(new OtpRequest(email)).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "OTP sent!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyOtp() {
        String otp = edtOtp.getText().toString();
        if (otp.isEmpty()) {
            Toast.makeText(this, "OTP is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        apiService.verifyOtp(new OtpVerifyRequest(email, Integer.parseInt(otp))).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "OTP verified!.", Toast.LENGTH_SHORT).show();
                    registerUser();
                } else {
                    Toast.makeText(RegisterActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser() {
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();
        String rePassword = edtRePassword.getText().toString();

        if (username.isEmpty() || password.isEmpty() || !password.equals(rePassword)) {
            Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        apiService.registerUser(new RegisterRequest(username, password, email)).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Email or username already exists!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

