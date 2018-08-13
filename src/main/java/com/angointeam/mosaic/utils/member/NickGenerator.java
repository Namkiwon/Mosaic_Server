package com.angointeam.mosaic.utils.member;


import com.angointeam.mosaic.repositories.MemberRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class NickGenerator implements IdentifierGenerator {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {


        return null;
    }
}
