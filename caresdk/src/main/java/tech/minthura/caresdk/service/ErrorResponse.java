package tech.minthura.caresdk.service;

public class ErrorResponse {

    ErrorResponse(Error error) {
        this.error = error;
    }

    private Error error;

    public Error getError() {
        return error;
    }

}
