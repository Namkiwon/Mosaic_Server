package com.angointeam.mosaic.api;

import com.angointeam.mosaic.domain.Example;
import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.etc.S3Uploader;
import com.angointeam.mosaic.service.scripts.ScriptsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
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

    @GetMapping("/scripts")
    public List<Script> getAll() {
        return scriptsService.getAllScripts();
    }

    @PostMapping("/script")
    @ResponseBody
    public Script addScript(String content , String category_uuid, String writerUuid, @RequestParam("imgUrls") List<MultipartFile> multipartFiles) throws IOException {
        UUID uuid = UUID.randomUUID();
        List<String> urls = new ArrayList<String>();
        for (int i = 0; i < multipartFiles.size(); i++) {
            s3Uploader.upload(multipartFiles.get(i), "scripts/"+uuid);  //원본 이미지

            BufferedImage image = ImageIO.read(multipartFiles.get(i).getInputStream());
            BufferedImage resized = resize(image, 300, 300);
            File outputfile = new File(multipartFiles.get(i).getOriginalFilename());
            ImageIO.write(resized, "png", outputfile);

            urls.add(s3Uploader.upload(outputfile, "scripts/"+uuid+"/thumbnail"));//썸네일 이미지
        }

        System.out.println(category_uuid);
        System.out.println(scriptsService.getCategoryByUuid(UUID.fromString(category_uuid)));

        return scriptsService.addScript(uuid, content, scriptsService.getCategoryByUuid(UUID.fromString(category_uuid)), writerUuid, urls);
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
