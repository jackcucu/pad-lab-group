package md.pad.web.handler;

import md.pad.exceptions.SerialException;
import md.pad.model.api.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(SerialException.class)
    public ResponseEntity<ApiResponse> exceptionToDoHandler(final Exception exception)
    {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> dataViolation(final DataIntegrityViolationException exception)
    {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> exceptionHandler(final Exception exception)
    {
        exception.printStackTrace();

        return new ResponseEntity<>(new ApiResponse("Bad request"), HttpStatus.OK);
    }
}
