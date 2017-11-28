package md.pad.exceptions;

import lombok.Getter;

@Getter
public class SerialException extends Exception
{
    private String errorMessage;

    public SerialException(final String errorMessage)
    {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
