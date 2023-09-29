package com.marvin.domain.backup.google;

public class GoogleDriveException extends Exception {

    public GoogleDriveException(String message) {
        super(message);
    }

    public GoogleDriveException(Exception e) {
        super(e);
    }
}
