package springboot.focusing.exception.auth;

import springboot.focusing.exception.CustomExceptionIfs;
import springboot.focusing.exception.ErrorCode;

public class InvalidNicknameException extends RuntimeException implements CustomExceptionIfs {
    private final ErrorCode errorCode;

    public InvalidNicknameException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}