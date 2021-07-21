package com.upload.files.repository;

import com.upload.files.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

/*    @Query("select m from Member m where username = :username and password = :password")
    Member findMember(String username, String password);*/

    boolean existsByUsername(String username);

}
