
package com.sunrisehms.exception;

public class WrongPassword extends Exception{

    public WrongPassword() {
    }

    public WrongPassword(String msg) {
        super(msg);
    }
}
