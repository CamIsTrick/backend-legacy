package springboot.focusing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinResponse {
    private final String jwt;
    private final Long memberId;
}