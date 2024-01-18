package springboot.focusing.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import springboot.focusing.exception.auth.DuplicateNicknameException;
import springboot.focusing.exception.auth.ExpiredTokenException;
import springboot.focusing.exception.auth.InvalidImageException;
import springboot.focusing.exception.auth.UnauthorizedException;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
public class customizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .errorCode(ErrorCode.SERVER_ERROR.getErrorCode())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DuplicateNicknameException.class, ExpiredTokenException.class, InvalidImageException.class, NullPointerException.class, UnauthorizedException.class})
    public final ResponseEntity<Object> handleCustomExceptions(CustomExceptionIfs ex, WebRequest request) {
        logger.error("An error occurred", (Throwable) ex);
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .errorCode(ex.getErrorCode().getErrorCode())
                .message(ex.getErrorCode().getMessage())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(ex.getErrorCode().getHttpStatusCode()));
    }
}
