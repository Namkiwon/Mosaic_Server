package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Reply;
import com.angointeam.mosaic.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReplyRepository  extends JpaRepository<Reply,Long> {

    Collection<Reply> findReplyByScript(Script script);
}
