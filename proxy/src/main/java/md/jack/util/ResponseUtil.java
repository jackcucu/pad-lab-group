package md.jack.util;

import md.jack.GenericException;
import md.jack.dto.Dto;
import md.jack.model.api.ApiResponse;

public final class ResponseUtil
{
    private ResponseUtil()
    {
    }

    public static Dto getResponse(final ApiResponse apiResponse) throws GenericException
    {
        if (apiResponse.isStatus())
        {
            return apiResponse.getResult();
        }
        else
        {
            throw new GenericException(apiResponse.getMessage());
        }
    }
}
