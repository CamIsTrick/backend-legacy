package springboot.focusing.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import springboot.focusing.domain.Member;

import java.io.File;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberLoginDto {

    private String memberNickname;
    private MultipartFile characterImage;

    public MemberLoginDto(String memberNickname, MultipartFile characterImage) {
        this.memberNickname = memberNickname;
        this.characterImage = characterImage;
    }

    public Member toEntity() {
        return Member.builder()
                .nickName(memberNickname)
                .build();
    }
}
