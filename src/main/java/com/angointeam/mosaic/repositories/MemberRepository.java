package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member,Long> {

    Optional<Member> findMemberByAuthKey(String authKey);
    Optional<Member> findMemberByUuid(String uuid);
    Optional<Member> findMemberByNick(String nick);
}
