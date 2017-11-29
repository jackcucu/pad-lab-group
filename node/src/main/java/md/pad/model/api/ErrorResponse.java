package md.pad.model.api;

import lombok.Data;

@Data
public class ErrorResponse
{
    private int errorCode;

    private String message;
}
