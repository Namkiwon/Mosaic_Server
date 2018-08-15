package com.angointeam.mosaic.api;

import com.angointeam.mosaic.api.response.BaseResponse;
import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Example;
import com.angointeam.mosaic.domain.Scrap;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.etc.S3Uploader;
import com.angointeam.mosaic.service.scripts.ScriptsService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ScriptsController {
    public final S3Uploader s3Uploader;

    @Autowired
    ScriptsService scriptsService;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/scripts")
    public BaseResponse<List<Script>> getAll() {
        String memberUuid = "ba7602dd-3f37-45d3-ba83-dfcb259b1d64";
        List<Script> scriptList =scriptsService.getAllScripts();
        List<String>  scrapList =scriptsService.getScrapUuidListByUuid(memberUuid);
        for(int i = 0 ; i < scriptList.size(); i++){
            if(scrapList.contains(scriptList.get(i).getUuid())) scriptList.get(i).setScrap(true);
        }
        return responseScriptListReturnSuccess(scriptList);
    }

    @PostMapping("/script")
    @ResponseBody
    public BaseResponse<Script> addScript(String content , String categoryUuid, String writerUuid, @RequestParam("imgUrls") List<MultipartFile> multipartFiles) throws IOException {
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
        Script script = scriptsService.addScript(uuid.toString(),content, scriptsService.getCategoryByUuid(categoryUuid), scriptsService.getWriter(writerUuid), imgUrls,thumbnailUrls);
        return responseAddScriptReturnSuccess(script);
    }

//    @PutMapping("/script")
//    @ResponseBody
//    public BaseResponse<String> updateScript(HttpSession session, String content , String scriptUuid, String categoryUuid, @RequestParam("imgUrls") List<MultipartFile> multipartFiles) throws IOException {
//        List<List<String>> urls = uploadImage(multipartFiles,scriptUuid);
//        System.out.println(scriptsService.getWriter(session.getAttribute("memberUuid").toString()));
//            scriptsService.updateScript(scriptUuid,scriptsService.getWriter(session.getAttribute("memberUuid").toString()),content,scriptsService.getCategoryByUuid(categoryUuid), urls);
//        return responseUDReturnSuccess("update success");
//    }

    @DeleteMapping("/script")
    @ResponseBody
    public BaseResponse<String> deleteScript(HttpSession session, String scriptUuid) throws IOException {
        Script script = scriptsService.getScriptByUuid(scriptUuid);
        if(script == null){
            return responseUDReturnSuccess("not found script");
        }else{
            scriptsService.deleteScript(script);
            return responseUDReturnSuccess("not found script");
        }
    }

    private BaseResponse<String> responseUDReturnSuccess(String resultMessage) {

        BaseResponse<String> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(resultMessage);

        return result;
    }

    private BaseResponse<Script> responseAddScriptReturnSuccess(Script script) {

        BaseResponse<Script> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(script);

        return result;
    }

    private BaseResponse<List<Script>> responseScriptListReturnSuccess(List<Script> scripts) {

        BaseResponse<List<Script>> result = new BaseResponse<>();
        result.setResponseCode(0);
        result.setHttpStatus(200);
        result.setResult(scripts);

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

//    public  List<List<String>> uploadImage(List<MultipartFile> multipartFiles,String uuid) throws IOException{
//        List<List<String>> img = new ArrayList<List<String>>();
//        List<String> imgUrls = new ArrayList<String>();
//        List<String> thumbnailUrls = new ArrayList<String>();
//
//        for (int i = 0; i < multipartFiles.size(); i++) {
//            imgUrls.add(s3Uploader.upload(multipartFiles.get(i), "scripts/"+uuid));  //원본 이미지
//            BufferedImage image = ImageIO.read(multipartFiles.get(i).getInputStream());
//            BufferedImage resized = resize(image, 300, 300);
//            File outputfile = new File(multipartFiles.get(i).getOriginalFilename());
//            ImageIO.write(resized, "png", outputfile);
//            thumbnailUrls.add(s3Uploader.upload(outputfile, "scripts/"+uuid+"/thumbnail"));//썸네일 이미지
//        }
//        img.add(imgUrls);
//        img.add(thumbnailUrls);
//
//        return img;
//    }
}
