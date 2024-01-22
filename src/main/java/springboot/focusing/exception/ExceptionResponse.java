package springboot.focusing.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {    //에러 발생시 리턴할 형식
    private int errorCode;  // 지정한 에러 코드
    private String message; // 오류 메시지
    private String details; // 오류 상세정보
}