package com.angointeam.mosaic.service.login;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.config.security.EncryptionManager;
import com.angointeam.mosaic.config.security.TokenService;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.University;
import com.angointeam.mosaic.repositories.MemberRepository;
import com.angointeam.mosaic.repositories.UniversityRepository;
import com.angointeam.mosaic.utils.email.EmailSender;
import com.angointeam.mosaic.utils.email.EmailUtils;
import com.angointeam.mosaic.utils.member.NickGenerator;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private EmailSender emailSender;

    private BCryptPasswordEncoder encryptor = new BCryptPasswordEncoder();


    public Map<String, String> login(String uuid, String authKey) {
        return memberRepository.findMemberByUuidAndAuthKey(uuid,authKey)
                .map(member -> {
                    if (!member.isAuthenticated()) throw new EmailNotYetException();
                    return tokenService.expiring(ImmutableMap.of("uuid",uuid));
                })
                .map(this::createTokenMap)
                .orElseThrow(LoginErrorException::new);
    }

    public Map<String, String> authentication(String email) {

        Optional<University> universityOptional = universityRepository.findByDomain(EmailUtils.getDomain(email));

        if (universityOptional.isPresent())
            return universityOptional.map(university -> createMember(university,email))
                    .map(this::sendAuthEmail)
                    .map(member -> createUuidAndKeyMap(member.getUuid(),member.getAuthKey()))
                    .orElseThrow(LoginErrorException::new);

        else throw new EmailNotValidException();

    }

    public Member emailAuthentication(String uuid, String emailKey) {
        try {

            Member member = memberRepository.findMemberByUuidAndEmailKey(uuid,emailKey)
                                            .orElseThrow(EmailAuthErrorException::new);
            member.setAuthenticated(true);
            return memberRepository.save(member);

        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailAuthErrorException();
        }
    }

    private Member createMember(University university, String email) {

        Optional<Member> already = memberRepository.findMemberByEmail(email);

        if (already.isPresent()) {
            Member alreadyMember = already.get();
            alreadyMember.setAuthenticated(false);
            return memberRepository.save(alreadyMember);
        }

        Member member = new Member();
        member.setUniversity(university);
        member.setEmail(email);
        member.setNick(NickGenerator.gen());
        member.setEmailKey(RandomStringUtils.random(10,true,true));
        member.setAuthKey(encryptor.encode(email));

        return memberRepository.save(member);

    }

    private Member sendAuthEmail(Member member) {
        try {

            emailSender.send(member.getEmail(),member.getUuid(),member.getEmailKey());
            return member;

        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailSendErrorException();
        }


    }

    private Map<String, String> createUuidAndKeyMap(String uuid, String authKey) {
        Map<String, String> map = new HashMap<>();
        map.put("uuid",uuid);
        map.put("authKey",authKey);
        return map;
    }

    private Map<String, String> createTokenMap(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("token",token);
        return map;
    }

}
