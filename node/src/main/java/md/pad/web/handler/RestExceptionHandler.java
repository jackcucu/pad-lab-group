package md.pad.web.handler;

import md.pad.exceptions.SerialException;
import md.pad.model.api.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(SerialException.class)
    public ResponseEntity<ErrorResponse> exceptionToDoHandler(final Exception exception)
    {
        final ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> notValidRequest(final MethodArgumentNotValidException exception)
    {
        final ErrorResponse error = new ErrorResponse();

        error.setErrorCode(HttpStatus.BAD_REQUEST.value());

        final String fields = exception.getBindingResult().getFieldErrors().stream()
                .map(it -> it.getField() + " " + it.getDefaultMessage())
                .collect(Collectors.joining(", "));

        error.setMessage(fields);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> dataViolation(final DataIntegrityViolationException exception)
    {
        final ErrorResponse error = new ErrorResponse();

        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getLocalizedMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(final Exception exception)
    {
        exception.printStackTrace();

        final ErrorResponse error = new ErrorResponse();

        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Bad request");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
