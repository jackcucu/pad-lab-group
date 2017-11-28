package md.pad.model.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse
{
    private String message;
    private boolean status;
    private Object result;

    public ApiResponse(final Object result)
    {
        this.status = true;
        this.result = result;
    }

    public ApiResponse(final String message, final Object result)
    {
        this.status = true;
        this.message = message;
        this.result = result;
    }

    public ApiResponse(final String message)
    {
        this.status = false;
        this.message = message;
    }

    public ApiResponse(final String message, final boolean status)
    {
        this.status = status;
        this.message = message;
    }
}