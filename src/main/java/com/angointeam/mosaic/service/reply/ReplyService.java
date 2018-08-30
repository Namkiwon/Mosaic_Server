package com.angointeam.mosaic.service.reply;

import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Reply;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.etc.S3Uploader;
import com.angointeam.mosaic.repositories.ReplyRepository;
import com.angointeam.mosaic.repositories.ScriptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReplyService {

    @Autowired
    S3Uploader s3Uploader;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ScriptsRepository scriptsRepository;

    public Reply addReply(Member member, String scriptUuid, String upperReplyUuid, String content, MultipartFile file) throws IOException{

        Reply reply = new Reply();

        reply.setUuid(UUID.randomUUID().toString());
        reply.setContent(content);
        reply.setWriter(member);
        reply.setValid(true);

        System.out.println(scriptUuid);

        Script script = scriptsRepository.findByUuid(scriptUuid);

        if (script == null) throw new ScrpitNotFoundException();

        reply.setScript(script);

        Reply upperReply = null;

        if (upperReplyUuid != null && !upperReplyUuid.isEmpty()) {
            upperReply = replyRepository.findReplyByUuid(upperReplyUuid)
                    .orElseThrow(ReplyNotFoundException::new);

            reply.setUpperReply(upperReply);
            reply.setDepth(1);
        }

        reply.setContent(content);

        if(file != null) {
            reply.setImgUrl(s3Uploader.upload(file, "replies/"+reply.getUuid()));
            BufferedImage image = ImageIO.read(file.getInputStream());
            BufferedImage resized = resize(image, 300, 300);
            File outputfile = new File(file.getOriginalFilename());
            ImageIO.write(resized, "png", outputfile);
            reply.setThumbnailUrl(s3Uploader.upload(outputfile, "replies/"+reply.getUuid()+"/thumbnail"));//썸네일 이미지

        }

        Reply r = replyRepository.save(reply);

        if (r != null && upperReply != null) {
           upperReply.getChildReplies().add(r);
           replyRepository.save(upperReply);
        }

        return r;

    }

    public Reply deleteReply(String uuid) {
        return replyRepository.findReplyByUuid(uuid)
                            .map(reply -> {
                                reply.setValid(false);
                                return reply;
                            }).map(replyRepository::save)
                            .orElseThrow(ReplyNotFoundException::new);
    }

    public List<Reply> getReplies(String scriptUuid) {
        Script script = scriptsRepository.findByUuid(scriptUuid);

        if (script == null) throw new ScrpitNotFoundException();

        List<Reply> replies = replyRepository.findRepliesByScript(script);
        List<Reply> result = new ArrayList<>();

        for (Reply r : replies) {
            if ( !result.contains(r) ) {
                result.add(r);
                result.addAll(r.getChildReplies());
            }

        }

        return result;
    }


    private static BufferedImage resize(BufferedImage img, int height, int width) throws IOException {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
