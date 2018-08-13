package com.angointeam.mosaic.service.login;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.University;
import com.angointeam.mosaic.repositories.MemberRepository;
import com.angointeam.mosaic.repositories.UniversityRepository;
import com.angointeam.mosaic.utils.email.EmailSender;
import com.angointeam.mosaic.utils.email.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private EmailSender emailSender;

    public BaseResponse<Member> login(String authKey) {
        return null;
    }

    public BaseResponse<String> authetication(String email) {

        BaseResponse<String> result = new BaseResponse<>();

        universityRepository.findByDomain(EmailUtils.getDomain(email))
                .ifPresent(university -> {});
        return null;
    }


}
