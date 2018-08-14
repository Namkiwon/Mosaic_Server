package com.angointeam.mosaic.service.mem;

import com.angointeam.mosaic.domain.Category;
import com.angointeam.mosaic.domain.Mem;
import com.angointeam.mosaic.repositories.MemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemService {
    @Autowired
    private MemRepository memRepository;

    public Mem addMem(){
        return memRepository.save(new Mem(UUID.randomUUID().toString()));
    }

    public List<Mem> getAllMem() {
        return memRepository.findAll();
    }
}
