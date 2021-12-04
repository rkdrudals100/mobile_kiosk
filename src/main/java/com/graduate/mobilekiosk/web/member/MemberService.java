package com.graduate.mobilekiosk.web.member;

import com.graduate.mobilekiosk.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username);
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }

        return User.builder()
                .username(member.getUserId())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();
    }

    @Transactional
    public void join(Member member) {
        member.encodePassword(passwordEncoder);
        memberRepository.save(member);

    }

    public Member findMember(String userId) {
        return memberRepository.findByUserId(userId);
    }

    public String findStoreNameByUserId(String url) {
        return memberRepository.findByUserId(url).getStoreName();
    }
}
