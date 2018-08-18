package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Reply;
import com.angointeam.mosaic.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository  extends JpaRepository<Reply,Long> {

    List<Reply> findReplyByScript(String scriptUuid);
    Optional<Reply> findReplyByUuid(String uuid);
}
