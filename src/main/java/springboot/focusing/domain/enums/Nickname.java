package springboot.focusing.domain.enums;

import springboot.focusing.exception.ErrorCode;
import springboot.focusing.exception.auth.DuplicateNicknameException;
import springboot.focusing.exception.auth.InvalidNicknameException;
import springboot.focusing.repository.MemberRepository;

public class Nickname {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 30;
    private String value;

    public Nickname(String value, MemberRepository memberRepository) {
        validateDuplicate(value, memberRepository);
        validateLength(value);
        validateSpace(value);
        this.value = value;
    }

    private void validateDuplicate(String value, MemberRepository memberRepository) {
        memberRepository.findByNickname(value)
                .ifPresent(m -> {
                    throw new DuplicateNicknameException(ErrorCode.DUPLICATE_NICKNAME);
                });
    }

    private void validateLength(String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidNicknameException(ErrorCode.INVALID_NICKNAME);
        }
    }

    private void validateSpace(String value) {
        if (value.contains(" ")) {
            throw new InvalidNicknameException(ErrorCode.INVALID_NICKNAME);
        }
    }

    public String getValue() {
        return value;
    }
}
