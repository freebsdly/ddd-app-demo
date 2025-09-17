package org.example.exception;

import lombok.Getter;

@Getter
public class BusinessException extends Exception
{
    private final String code;

    public BusinessException(String code, String message)
    {
        super(message);
        this.code = code;
    }

}
