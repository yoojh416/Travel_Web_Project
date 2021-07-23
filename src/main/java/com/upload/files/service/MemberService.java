package com.upload.files.service;

import com.upload.files.entity.Member;
import com.upload.files.repository.MemberDto;
import com.upload.files.repository.MemberRepository;

import com.upload.files.repository.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;
    

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long joinUser(MemberDto memberDto) {

        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    /**
     * 로그인 확인 로직
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String admin = "admin@1234";

        Member userEntityWrapper = memberRepository.findByUsername(username);
        Member userEntity = userEntityWrapper;
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (admin.equals(username)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }

    /**
     * username 중복 확인 로직
     */
    public boolean checkUsernameDuplicate(String username) {
        return memberRepository.existsByUsername(username);
    }

    /**
     * 회원정보 수정시 password 확인
     */
    public boolean validatePassword(String password) {
        return memberRepository.existsByPassword(password);
    }

    /** email 유효성 검사  */
    public boolean userEmailCheck(String username, String name) {

        Member member = memberRepository.findByUsername(username);

        if(member!=null && member.getUsername().equals(username)) {
            return true;
        }
        else {
            return false;
        }
    }

}
