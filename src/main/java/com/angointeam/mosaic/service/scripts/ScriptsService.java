package com.angointeam.mosaic.service.scripts;


import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.etc.S3Uploader;
import com.angointeam.mosaic.repositories.CategoryRepository;
import com.angointeam.mosaic.repositories.MemberRepository;
import com.angointeam.mosaic.repositories.ScrapRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class ScriptsService {

    @Autowired
    S3Uploader s3Uploader;


    @Autowired
    private ScriptsRepository scriptsRepository;

    public Script getScriptByUuid(String uuid){
        return scriptsRepository.findByUuid(uuid);
    }

    public List<Script> getAllScripts(String memberUuid,List<String> categories) {
        List<Script> scriptList = new ArrayList<>();
        if(categories.size() == 0) {scriptList =scriptsRepository.findAll();}
        else{
            for (int i= 0; i < categories.size();i++){
                scriptList.addAll(scriptsRepository.findAllByCategoryUuid(categories.get(i)));
            }
        }
        List<String>  scrapList = getScrapUuidListByUuid(memberUuid);
        for(int i = 0 ; i < scriptList.size(); i++){
            if(scrapList.contains(scriptList.get(i).getUuid())) scriptList.get(i).setScrap(true);
        }
        return scriptList;
    }

    public Script addScript(String content, String categoryUuid, String writerUuid, List<MultipartFile> multipartFiles)throws IOException {
        UUID uuid = UUID.randomUUID();
        List<String> imgUrls = new ArrayList<String>();
        List<String> thumbnailUrls = new ArrayList<String>();
        for (int i = 0; i < multipartFiles.size(); i++) {
            imgUrls.add(s3Uploader.upload(multipartFiles.get(i), "scripts/"+uuid));  //원본 이미지
            BufferedImage image = ImageIO.read(multipartFiles.get(i).getInputStream());
            BufferedImage resized = resize(image, 300, 300);
            File outputfile = new File(multipartFiles.get(i).getOriginalFilename());
            ImageIO.write(resized, "png", outputfile);
            thumbnailUrls.add(s3Uploader.upload(outputfile, "scripts/"+uuid+"/thumbnail"));//썸네일 이미지
        }
        Category category = getCategoryByUuid(categoryUuid);
        Member writer = getWriter(writerUuid);

        return scriptsRepository.save(new Script(uuid.toString(), content,category,writer,imgUrls,thumbnailUrls));
    }

    public void updateScript(String scriptUuid, Member memberInfo,String content, Category category,List<String> imgUrls){
         scriptsRepository.updateScript(scriptUuid,memberInfo,content,category,imgUrls );
    }

    public void deleteScript(Script script){
        scriptsRepository.delete(script);
    }

    @Autowired
    private ScrapRepository scrapRepository;

    public List<String> getScrapUuidListByUuid(String memberUuid){
        return scrapRepository.findScriptUuidListByMemberUuid(memberUuid);
    }

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryByUuid(String uuid) {
        return categoryRepository.findByUuid(uuid);
    }

    @Autowired
    private MemberRepository memberRepository;

    public Member getWriter(String writerUuid) {
        return memberRepository.findByUuid(writerUuid);
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
