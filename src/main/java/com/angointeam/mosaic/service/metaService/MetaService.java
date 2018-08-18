package com.angointeam.mosaic.service.metaService;

import com.angointeam.mosaic.domain.University;
import com.angointeam.mosaic.repositories.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaService {

    @Autowired
    private UniversityRepository universityRepository;

    public List<University> findUniversities() {
        return universityRepository.findAll();
    }

}
