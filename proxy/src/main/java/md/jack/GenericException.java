package md.jack;

public class GenericException extends Exception
{
    private String message;

    public GenericException(final String message)
    {
        super(message);
    }
}
