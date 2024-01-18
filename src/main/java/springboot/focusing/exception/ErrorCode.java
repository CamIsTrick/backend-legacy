package springboot.focusing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode  {

    OK(200 , 200 , "성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버에러"),

    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null point"),

    //login 에러 코드
    DUPLICATE_NICKNAME(400,410 ,"중복된 닉네임" ),
    INVALID_NICKNAME(400,411 ,"올바르지 않은 형식의 닉네임" ),
    INVALID_IMAGE(400,412 ,"잘못된 형식의 이미지" ),

    //auth 에러 코드
    UNAUTHORIZED(401 , 401 , "권한 없음"),
    EXPIRED_TOKEN(400 , 2001 , "만료된 토큰"),
    INVALID_TOKEN(403,403 , "올바르지 않은 토큰"),
    NOT_ACCESS_TOKEN_FOR_REISSUE(400, 2003,"재발급하기에는 유효기간이 남은 엑세스토큰"),
    EXPIRED_REFRESH_TOKEN(400, 2004, "리프레시토큰의 유효기간이 지남"),
    NULL_MEMBER(400,2008,"해당 Member 엔티티 조회 불가" ),
    NULL_REFRESH_TOKEN(400, 2009,"해당 RefreshToken 엔티티 조회불가" );

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;

}