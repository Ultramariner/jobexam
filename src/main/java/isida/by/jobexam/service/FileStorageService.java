package isida.by.jobexam.service;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

@Service
public class FileStorageService {

    public void saveToStorage(String imgLink, String storageLocation) throws IOException {
        URL url = new URL(imgLink);
        InputStream inputStream = url.openStream();
        OutputStream fileOutputStream = new FileOutputStream(storageLocation);
        int ch;
        while ((ch = inputStream.read()) != -1) {
            fileOutputStream.write(ch);
        }
        inputStream.close();
        fileOutputStream.close();
    }

}
