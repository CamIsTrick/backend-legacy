package springboot.focusing.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Member {

    private Long id;
    private String nickName;
    private int x;
    private int y;
    private String refreshToken;

    public void initializePosition() {
        this.x = 0;
        this.y = 0;
    }

    public void movePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
