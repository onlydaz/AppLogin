package vn.iotstar.applogin.models;

public class OtpVerifyRequest {
    private String email;
    private int otp;

    public OtpVerifyRequest(String email, int otp) {
        this.email = email;
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public int getOtp() {
        return otp;
    }
}

