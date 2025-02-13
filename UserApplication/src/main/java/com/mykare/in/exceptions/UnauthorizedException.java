package com.mykare.in.exceptions;

public class UnauthorizedException  extends Exception{

    private static final long serialVersionUID = -9079454849611061074L;

    public UnauthorizedException() {

        super();
    }

    public UnauthorizedException( String message) {

        super(message);
    }
}