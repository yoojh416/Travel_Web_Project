package com.upload.files.repository;

import com.upload.files.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /** 기본 제공 아이디로 유저찾기 */
    Member findByUsername(String username);

/*    @Query("select m from Member m where username = :username and password = :password")
    Member findMember(String username, String password);*/

    /*아이디 중복확인용*/
    boolean existsByUsername(String username);

    /*같은 로직으로 회원수정시 비밀번호 확인*/
    boolean existsByPassword(String password);

}
