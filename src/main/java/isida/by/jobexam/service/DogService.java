package isida.by.jobexam.service;

import isida.by.jobexam.model.Dog;
import isida.by.jobexam.repository.DogRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

@Service
@Transactional
public class DogService {

    private final DogRepository repository;
    @Value("${server.storage}")
    private String storage;

    public DogService(DogRepository repository) {
        this.repository = repository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Dog dog) throws IOException {
        String imgLink = dog.getLink();
        URL url = new URL(imgLink);
        String storageLocation = storage + imgLink.substring(imgLink.lastIndexOf("/"));
        dog.setPath(storageLocation);
        InputStream inputStream = url.openStream();
        OutputStream fileOutputStream = new FileOutputStream(storageLocation);
        int ch;
        while ((ch = inputStream.read()) != -1) {
            fileOutputStream.write(ch);
        }
        inputStream.close();
        fileOutputStream.close();
        this.entityManager.persist(dog);
    }
}
