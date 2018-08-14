package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member,Long> {

    Optional<Member> findMemberByUuidAndAuthKey(String uuid, String authKey);
    Optional<Member> findMemberByUuid(String uuid);
    Optional<Member> findMemberByNick(String nick);
    Optional<Member> findMemberByEmail(String email);

}
