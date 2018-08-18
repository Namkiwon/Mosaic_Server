package com.angointeam.mosaic.service.member;

import com.angointeam.mosaic.config.security.InValidTokenException;
import com.angointeam.mosaic.config.security.TokenService;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.University;
import com.angointeam.mosaic.repositories.MemberRepository;
import com.angointeam.mosaic.repositories.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MemberRepository memberRepository;

    public Member findByToken(String token) {
        return Optional.of(tokenService.verify(token))
                .map(map -> {
                    System.out.println(map.get("uuid"));
                    return map.get("uuid");
                })
                .flatMap(memberRepository::findMemberByUuid)
                .orElseThrow(() -> new InValidTokenException());
    }


}
