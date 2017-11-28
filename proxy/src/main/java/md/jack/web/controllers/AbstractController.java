package md.jack.web.controllers;

import md.jack.GenericException;
import md.jack.dto.Dto;
import md.jack.model.api.ApiResponse;

public abstract class AbstractController
{
    protected Dto getResponse(final ApiResponse apiResponse) throws GenericException
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
