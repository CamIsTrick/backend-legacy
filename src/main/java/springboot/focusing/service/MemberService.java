package springboot.focusing.service;

import lombok.RequiredArgsConstructor;
import springboot.focusing.dto.JoinResponse;
import springboot.focusing.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springboot.focusing.config.auth.dto.MemberInfoForToken;
import springboot.focusing.config.auth.jwt.TokenProvider;
import springboot.focusing.domain.Member;
import springboot.focusing.dto.MemberLoginDto;
import springboot.focusing.exception.ErrorCode;
import springboot.focusing.exception.auth.DuplicateNicknameException;
import springboot.focusing.exception.auth.InvalidImageException;
import springboot.focusing.repository.MemberRepository;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public JoinResponse join(MemberLoginDto dto) {
        isValid(dto);
        Member member = dto.toEntity();
        member.initializePosition();
        memberRepository.save(member);

        MemberInfoForToken memberInfoForToken = new MemberInfoForToken(member.getNickName());
        String jwt = tokenProvider.generateAccessToken(memberInfoForToken);

        return new JoinResponse(jwt, member.getId());
    }

    public void isValid(MemberLoginDto dto) {
        validateDuplicate(dto.getMemberNickname());
        validatePngImage(dto.getCharacterImage());
    }

    private void validateDuplicate(String nickname) {
        memberRepository.findByNickname(nickname)
                .ifPresent(m -> {
                    throw new DuplicateNicknameException(ErrorCode.DUPLICATE_NICKNAME);
                });
    }

    public void validatePngImage(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                boolean isValid = FileUtils.validImgFile(file.getInputStream());
                if (!isValid) {
                    throw new InvalidImageException(ErrorCode.INVALID_IMAGE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidImageException(ErrorCode.INVALID_IMAGE);
        }
    }



    public void updateRefreshToken(String nickname, String refreshToken) {
        Member member = memberRepository.findByNickname(nickname).get();
        if (member == null)
            return;
        member.updateRefreshToken(refreshToken);
    }


}
