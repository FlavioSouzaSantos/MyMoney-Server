package br.com.mymoney.authserver.exceptions;

public class UseruuuidNotFoundException extends RuntimeException {
    public UseruuuidNotFoundException(String message) {
        super(message);
    }
}
