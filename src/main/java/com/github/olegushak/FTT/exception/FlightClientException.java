package com.github.olegushak.FTT.exception;

public class FlightClientException extends RuntimeException{

    public FlightClientException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
