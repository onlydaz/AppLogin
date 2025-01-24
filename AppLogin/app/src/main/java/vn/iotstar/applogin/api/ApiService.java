package vn.iotstar.applogin.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.iotstar.applogin.models.ApiResponse;
import vn.iotstar.applogin.models.LoginRequest;
import vn.iotstar.applogin.models.OtpRequest;
import vn.iotstar.applogin.models.OtpVerifyRequest;
import vn.iotstar.applogin.models.RegisterRequest;
import vn.iotstar.applogin.models.ResetPasswordRequest;

public interface ApiService {

    // API gửi OTP
    @POST("/api/auth/send-otp")
    Call<ApiResponse> sendOtp(@Body OtpRequest otpRequest);

    // API đăng ký
    @POST("/api/auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest registerRequest);

    // API xác thực OTP
    @POST("/api/auth/verify-otp")
    Call<ApiResponse> verifyOtp(@Body OtpVerifyRequest otpVerifyRequest);

    // API login
    @POST("/api/auth/login")
    Call<ApiResponse> loginUser(@Body LoginRequest loginRequest);

    // API reset mật khẩu
    @POST("/api/auth/reset-password")
    Call<ApiResponse> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);
}
