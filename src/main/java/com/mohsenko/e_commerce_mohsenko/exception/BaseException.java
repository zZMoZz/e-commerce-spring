package com.mohsenko.e_commerce_mohsenko.exception;

/*
If each custom exception extends RuntimeException directly,
we'd need a separate exception handler for each one, because if we
create a shared handler that receives the exception as a RuntimeException,
it will omit the status attribute.

By creating a BaseException abstract class, all custom exceptions
share a common structure. So, we can handle all of them using
one global handler by receiving the exception as a BaseException object.

We make it abstract because it is just a blueprint
for other exceptions, not a concrete error type.

BaseException says: "All exceptions need a status and message".
Subclasses say: "here is my specific status and message format".
*/

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {
    // NOTE: final keyword here doesn't add anything as there is no setters.
    // It only indicates that once an exception is thrown, its properties should never change.
    private final String message;
    private final HttpStatus status;
    
    protected BaseException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    protected BaseException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.status = status;
    }
}
