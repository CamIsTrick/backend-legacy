package springboot.focusing.config.auth.dto;

/*
토큰을 생성할 때 사용할 사용자 정보
 */
public record MemberInfoForToken(String name) {

    public String getName() {
        return this.name;
    }
}
