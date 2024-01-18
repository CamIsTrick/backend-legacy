package springboot.focusing.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberLoginDto {

    private String memberNickname;
    private MultipartFile characterImage;

    public MemberLoginDto(String memberNickname, MultipartFile characterImage) {
        this.memberNickname = memberNickname;
        this.characterImage = characterImage;
    }
}
