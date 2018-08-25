package com.angointeam.mosaic.service.univ;

import com.angointeam.mosaic.domain.Script;
import com.angointeam.mosaic.domain.University;
import com.angointeam.mosaic.etc.S3Uploader;
import com.angointeam.mosaic.repositories.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UnivService {
    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    S3Uploader s3Uploader;

    public University saveUniv(String name, String domain, MultipartFile multipartFile)throws IOException {
        String imgUrl = s3Uploader.upload(multipartFile, "univ/"+name);  //원본 이미지
        return universityRepository.save(new University(name,domain,imgUrl));
    }
}
