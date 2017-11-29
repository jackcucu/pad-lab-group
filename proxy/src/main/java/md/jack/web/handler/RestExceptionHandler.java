package md.jack.web.handler;

import md.jack.GenericException;
import md.jack.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> error(final GenericException exception)
    {
        final ErrorResponse error = new ErrorResponse();

        error.setErrorCode(HttpStatus.BAD_REQUEST.value());

        error.setMessage(exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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
}
