package springboot.focusing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springboot.focusing.config.auth.jwt.JwtProperties;
import springboot.focusing.config.auth.jwt.TokenProviderImpl;
import springboot.focusing.dto.JoinResponse;
import springboot.focusing.dto.MemberLoginDto;
import springboot.focusing.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TokenProviderImpl tokenProvider;

    /*
    사용자 닉네임, 파일이 유효한 지 확인하고,
    유효하다면 사용자를 등록하고 토큰 발급
     */
    @PostMapping("/user/join")
    public ResponseEntity join(@RequestParam("memberNickname") String memberNickname, @RequestParam("characterImage") MultipartFile characterImage) {
        MemberLoginDto dto = new MemberLoginDto(memberNickname, characterImage);
        JoinResponse joinResponse = memberService.join(dto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + joinResponse.getJwt());
        return new ResponseEntity<>(joinResponse.getMemberId(), headers, HttpStatus.OK);
    }

    /*
    인증(토큰 검증) test
     */
//    @GetMapping("/user")
//    public ResponseEntity test(HttpServletRequest request) {
//        String token = request.getHeader(JwtProperties.HEADER_STRING);
//        String username = tokenProvider.getMemberInfoFromToken(token).getName();
//
//        return ResponseEntity.ok().body("Profile of " + username);
//    }
}
