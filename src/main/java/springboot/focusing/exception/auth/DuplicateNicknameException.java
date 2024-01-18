package springboot.focusing.exception.auth;

import lombok.Getter;
import springboot.focusing.exception.CustomExceptionIfs;
import springboot.focusing.exception.ErrorCode;

@Getter
public class DuplicateNicknameException  extends RuntimeException implements CustomExceptionIfs {
    private final ErrorCode errorCode;

    public DuplicateNicknameException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}