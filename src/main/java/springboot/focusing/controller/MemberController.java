package springboot.focusing.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/user/join")
    public ResponseEntity join(@RequestParam("memberNickname") String memberNickname, @RequestParam("characterImage") MultipartFile characterImage) {
        MemberLoginDto dto = new MemberLoginDto(memberNickname, characterImage);
        JoinResponse joinResponse = memberService.join(dto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + joinResponse.getJwt());
        return new ResponseEntity<>(joinResponse.getMemberId(), headers, HttpStatus.OK);
    }

//    @GetMapping("/user")
//    public ResponseEntity test(HttpServletRequest request) {
//        String token = request.getHeader(JwtProperties.HEADER_STRING);
//        String username = tokenProvider.getMemberInfoFromToken(token).getName();
//
//        return ResponseEntity.ok().body("Profile of " + username);
//    }
}
