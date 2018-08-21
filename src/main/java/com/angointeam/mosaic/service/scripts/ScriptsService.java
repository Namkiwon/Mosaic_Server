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
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
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

    //스크립트 하나 가져오기
    public Script getScriptByUuid(String uuid){
        return scriptsRepository.findByUuid(uuid);
    }

    //컨텐트 검색으로 스크립트들 가져오기
    public List<Script> findAllByKeyword(String keyword,String memberUuid){
        List<Script> scriptList = scriptsRepository.findAllByKeyword(keyword);
        List<String>  scrapList = getScrapUuidListByUuid(memberUuid);
        for(int i = 0 ; i < scriptList.size(); i++){
            if(scrapList.contains(scriptList.get(i).getUuid())) scriptList.get(i).setScrap(true);
        }
        return scriptList;
    }

    //모든 스크립트 가져오기
    public List<Script> getAllScripts(String memberUuid,List<String> categories) {
        if (categories ==null) categories = new ArrayList<>();
        List<Script> scriptList = new ArrayList<>();
        if(categories.size() == 0) {scriptList =scriptsRepository.findAllWhereValidTrue();}
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

    //내 스크립트들만 가져오기
    public List<Script> getMyScripts(String memberUuid) {
        return scriptsRepository.findAllByMemberUuid(memberUuid);
    }

    //스크립트 작성하기
    public Script addScript(String content, String categoryUuid, String writerUuid, List<MultipartFile> multipartFiles)throws IOException {
        UUID uuid = UUID.randomUUID();
        List<String> imgUrls = new ArrayList<String>();
        List<String> thumbnailUrls = new ArrayList<String>();

        Category category = categoryRepository.findByUuid(categoryUuid).get();
        if (category == null) throw new CategoryNotFoundException();

        for (int i = 0; i < multipartFiles.size(); i++) {
            imgUrls.add(s3Uploader.upload(multipartFiles.get(i), "scripts/"+uuid));  //원본 이미지
            BufferedImage image = ImageIO.read(multipartFiles.get(i).getInputStream());
            BufferedImage resized = resize(image, 300, 300);
            File outputfile = new File(multipartFiles.get(i).getOriginalFilename());
            ImageIO.write(resized, "png", outputfile);
            thumbnailUrls.add(s3Uploader.upload(outputfile, "scripts/"+uuid+"/thumbnail"));//썸네일 이미지
        }

        Member writer = getWriter(writerUuid);

        return scriptsRepository.save(new Script(uuid.toString(), content,category,writer,imgUrls,thumbnailUrls));
    }

    //스크립트 업데이트하기
    public void updateScript(String scriptUuid, Member memberInfo,String content, Category category,List<String> imgUrls){
         scriptsRepository.updateScript(scriptUuid,memberInfo,content,category,imgUrls );
    }

    //스크립트 삭제 -> 사실상 발리드만 false
    public void deleteScript(String scriptUuid)  {
        scriptsRepository.updateScriptValidFalse(scriptUuid);
    }


    @Autowired
    private ScrapRepository scrapRepository;

    public List<String> getScrapUuidListByUuid(String memberUuid){
        return scrapRepository.findScriptUuidListByMemberUuid(memberUuid);
    }

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryByUuid(String uuid) {
        return categoryRepository.findByUuid(uuid).get();
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
