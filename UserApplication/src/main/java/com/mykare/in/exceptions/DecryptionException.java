package com.mykare.in.exceptions;


public class DecryptionException extends RuntimeException  {

    public DecryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecryptionException(){
        super();
    }
}
