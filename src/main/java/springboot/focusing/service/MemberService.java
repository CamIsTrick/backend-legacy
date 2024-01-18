package springboot.focusing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.focusing.config.auth.dto.MemberInfoForToken;
import springboot.focusing.config.auth.jwt.TokenProvider;
import springboot.focusing.domain.Member;
import springboot.focusing.domain.enums.ImageFile;
import springboot.focusing.domain.enums.Nickname;
import springboot.focusing.dto.JoinResponse;
import springboot.focusing.dto.MemberLoginDto;
import springboot.focusing.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public JoinResponse join(MemberLoginDto dto) {
        Nickname nickname = new Nickname(dto.getMemberNickname(), memberRepository);
        ImageFile imageFile = new ImageFile(dto.getCharacterImage());

        Member member = Member.builder()
                .nickName(new Nickname(dto.getMemberNickname(), memberRepository))
                .build();
        member.initializePosition();
        memberRepository.save(member);

        MemberInfoForToken memberInfoForToken = new MemberInfoForToken(member.getNickName().getValue());
        String jwt = tokenProvider.generateAccessToken(memberInfoForToken);

        return new JoinResponse(jwt, member.getId());
    }

    public void updateRefreshToken(String nickname, String refreshToken) {
        Member member = memberRepository.findByNickname(nickname).get();
        if (member == null)
            return;
        member.updateRefreshToken(refreshToken);
    }
}
