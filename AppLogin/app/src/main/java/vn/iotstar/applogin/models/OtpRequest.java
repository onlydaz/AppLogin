package vn.iotstar.applogin.models;

public class OtpRequest {
    private String email;

    public OtpRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

