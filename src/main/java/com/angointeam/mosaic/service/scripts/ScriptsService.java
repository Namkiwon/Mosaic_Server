package com.angointeam.mosaic.service.scripts;


import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Member;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.etc.S3Uploader;
import com.angointeam.mosaic.repositories.CategoryRepository;
import com.angointeam.mosaic.repositories.MemberRepository;
import com.angointeam.mosaic.repositories.ScrapRepository;
import com.angointeam.mosaic.repositories.ScriptsRepository;
import com.angointeam.mosaic.service.category.CategoryNotFoundException;
import com.angointeam.mosaic.utils.comparator.ScriptComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
public class ScriptsService {

    @Autowired
    S3Uploader s3Uploader;

    @Autowired
    private ScriptsRepository scriptsRepository;

    @Autowired
    private ScrapRepository scrapRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    //스크립트 하나 가져오기
    public Script getScriptByUuid(String memberUuid, String scriptUuid){
        return scriptsRepository.findScriptByUuid(scriptUuid).map(script -> {
            if(scrapRepository.findScrapByScriptAndMemberUuid(script,memberUuid).isPresent())
                script.setScrap(true);
            return script;
        }).orElseThrow(ScriptNotFoundException::new);
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

            if(categories.get(0).split(",").length>1){ // ios 호출
                categories = Arrays.asList(categories.get(0).split(","));
                for (String categoryUuid :categories){
                    scriptList.addAll(scriptsRepository.findAllByCategoryUuid(categoryUuid));
                }
            }else{
                for (String categoryUuid :categories){
                    scriptList.addAll(scriptsRepository.findAllByCategoryUuid(categoryUuid));
                }
            }

        }
        List<String>  scrapList = getScrapUuidListByUuid(memberUuid);
        for(Script script : scriptList){
            if(scrapList.contains(script.getUuid())) script.setScrap(true);
        }

        Collections.sort(scriptList, new Comparator<Script>() {
            @Override
            public int compare(Script o1, Script o2) {
                if(o1.getIdx() > o2.getIdx()) return -1;
                else if (o1.getIdx() < o2.getIdx()) return 1;
                return 0;
            }
        });
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

        if(multipartFiles != null){
            for (MultipartFile multipartFile : multipartFiles) {

                BufferedImage image = ImageIO.read(multipartFile.getInputStream());
                if(image.getWidth() > 1000){
                    BufferedImage resized = resize(image, 1000, 1000);
                    File outputfile = new File(multipartFile.getOriginalFilename());
                    ImageIO.write(resized, "png", outputfile);
                    imgUrls.add(s3Uploader.upload(outputfile, "scripts/"+uuid));  //원본 이미지
                }else{
                    imgUrls.add(s3Uploader.upload(multipartFile, "scripts/"+uuid));  //원본 이미지
                }

                BufferedImage resizedTumbnail = resize(image, 300, 300);
                File outputfileThumbnail = new File(multipartFile.getOriginalFilename());
                ImageIO.write(resizedTumbnail, "png", outputfileThumbnail);
                thumbnailUrls.add(s3Uploader.upload(outputfileThumbnail, "scripts/"+uuid+"/thumbnail"));//썸네일 이미지
            }
        }


        Member writer = getWriter(writerUuid);

        return scriptsRepository.save(new Script(uuid.toString(), content,category,writer,imgUrls,thumbnailUrls));
    }


    //스크립트 삭제 -> 사실상 발리드만 false
    public Script deleteScript(String scriptUuid)  {
        return scriptsRepository.findScriptByUuid(scriptUuid).map(script -> {
            script.setValid(false);
            return scriptsRepository.save(script);
        }).orElseThrow(ScriptNotFoundException::new);
//        scriptsRepository.updateScriptValidFalse(scriptUuid);
    }


    public List<String> getScrapUuidListByUuid(String memberUuid){
        return scrapRepository.findScriptUuidListByMemberUuid(memberUuid);
    }


    public Category getCategoryByUuid(String uuid) {
        return categoryRepository.findByUuid(uuid).get();
    }


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
