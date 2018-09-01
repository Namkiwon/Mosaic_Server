package com.angointeam.mosaic.repositories;

import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Reply;
import com.angointeam.mosaic.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository  extends JpaRepository<Reply,Long> {

    //@Query("select r from Reply r where r.script_uuid = scriptUuid")
    List<Reply> findRepliesByScriptAndUpperReplyAndValid(Script script,Reply upperReply, boolean valid);

    List<Reply> findRepliesByScriptAndValid(Script script,boolean valid);

    Optional<Reply> findReplyByUuid(String uuid);
}
