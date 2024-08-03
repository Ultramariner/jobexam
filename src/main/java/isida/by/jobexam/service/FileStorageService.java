package isida.by.jobexam.service;

import java.io.IOException;

public interface FileStorageService {
    void saveToStorage(String imgLink, String storageLocation) throws IOException;
}
