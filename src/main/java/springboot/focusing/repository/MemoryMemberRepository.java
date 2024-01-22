package springboot.focusing.repository;

import org.springframework.stereotype.Repository;
import springboot.focusing.domain.Member;
import springboot.focusing.exception.ErrorCode;
import springboot.focusing.exception.auth.DuplicateNicknameException;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    /*
    Map 형식으로 사용자 저장
     */
    private static Map<String, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        if (store.containsKey(member.getNickName())) {
            throw new DuplicateNicknameException(ErrorCode.DUPLICATE_NICKNAME);
        }
        member.setId(++sequence);
        store.put(member.getNickName().getValue(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByNickname(String nickname) {
        return Optional.ofNullable(store.get(nickname));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
