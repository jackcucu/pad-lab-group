package md.jack.model.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import md.jack.dto.Dto;

@Data
@NoArgsConstructor
public class ApiResponse
{
    private String message;

    private boolean status;

    private Dto result;

    public ApiResponse(final Dto result)
    {
        this.status = true;
        this.result = result;
    }

    public ApiResponse(final String message, final Dto result)
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
