package com.angointeam.mosaic.service.example;

        import com.angointeam.mosaic.domain.Example;
        import com.angointeam.mosaic.repositories.ExampleRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;

@Service
public class ExampleService {

    @Autowired
    private ExampleRepository exampleRepository;

    public List<Example> getAllExample() {
        return exampleRepository.findAll();
    }

    public Example addExample(String content) {
        return exampleRepository.save(new Example());
    }

}
