package com.angointeam.mosaic.service.login;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.config.security.TokenService;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.University;
import com.angointeam.mosaic.repositories.MemberRepository;
import com.angointeam.mosaic.repositories.UniversityRepository;
import com.angointeam.mosaic.utils.email.EmailSender;
import com.angointeam.mosaic.utils.email.EmailUtils;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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


    public String login(String uuid, String authKey) {
        return memberRepository.findMemberByUuidAndAuthKey(uuid,authKey)
                .map(member -> tokenService.expiring(ImmutableMap.of("uuid",uuid)))
                .orElseThrow(() -> new LoginErrorException());
    }

    public String authentication(String email) {

        Optional<University> universityOptional = universityRepository.findByDomain(EmailUtils.getDomain(email));

        if (universityOptional.isPresent())
            return universityOptional.map(university -> createMember(university,email))
                    .map(this::sendAuthEmail)
                    .map(member -> member.getUuid() + "/" +member.getAuthKey())
                    .orElseThrow(() -> new LoginErrorException());

        else throw new EmailNotValidException();

    }

    private Member createMember(University university, String email) {

        Optional<Member> already = memberRepository.findMemberByEmail(email);

        if (already.isPresent()) return already.get();

        Member member = new Member();
        member.setUniversity(university);
        member.setEmail(email);
        member.setAuthKey(encryptor.encode(email));

        return memberRepository.save(member);

    }

    private Member sendAuthEmail(Member member) {

        emailSender.send(EmailSender.messageBuilder()
                .setEmailKey(member.getAuthKey())
                .setSendTo(member.getEmail()).build());

        return member;
    }

}
