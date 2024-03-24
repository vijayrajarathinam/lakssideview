package com.lakesideview.hotel.exception;

public class PhotoRetrieverException extends RuntimeException {
    public PhotoRetrieverException(String error_retrieving_photo) {
        super(error_retrieving_photo);
    }
}
