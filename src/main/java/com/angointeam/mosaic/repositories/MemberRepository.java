package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Member;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member,Long> {

    Optional<Member> findMemberByUuidAndAuthKey(String uuid, String authKey);

    Optional<Member> findMemberByUuidAndEmailKey(String uuid, String emailKey);

    @Cacheable("byUuid")
    Optional<Member> findMemberByUuid(String uuid);

    Optional<Member> findMemberByNick(String nick);

    Optional<Member> findMemberByEmail(String email);

    Member findByUuid(String uuid);

    @Override
    @CacheEvict("byUuid")
    <S extends Member> S save(S var1);

}
