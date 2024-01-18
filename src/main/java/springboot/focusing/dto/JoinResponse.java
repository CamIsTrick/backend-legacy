package springboot.focusing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import springboot.focusing.domain.Member;

@AllArgsConstructor
@Getter
public class JoinResponse {
    private final String jwt;
    private final Long memberId;
}