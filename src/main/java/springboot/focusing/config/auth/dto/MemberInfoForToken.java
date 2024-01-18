package springboot.focusing.config.auth.dto;

public record MemberInfoForToken(String name) {

    public String getName(){
        return this.name;
    }
}
