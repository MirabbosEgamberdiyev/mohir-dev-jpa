package fido.uz.mohir_dev_jpa.service;

import fido.uz.mohir_dev_jpa.entity.FileStorage;
import fido.uz.mohir_dev_jpa.enums.FileStorageStatus;
import fido.uz.mohir_dev_jpa.repository.FileStorageRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FileStorageService {
    private final FileStorageRepository fileStorageRepository;

    @Value("${upload.server.folder}")
    private String serverFolderPath;

    private final Hashids hashids;

    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
        this.hashids = new Hashids(getClass().getName(), 6);
    }

    public FileStorage save(MultipartFile multipartFile) {
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setExtension(getExtension(multipartFile.getOriginalFilename()));
        fileStorage.setFileStorageStatus(FileStorageStatus.DRAFT);

        fileStorage = fileStorageRepository.save(fileStorage);

        Date now = new Date();
        String uploadPathStr = String.format("%s\\upload_folder\\%d\\%02d\\%02d",
                this.serverFolderPath,
                now.getYear() + 1900,
                now.getMonth() + 1,
                now.getDate());

        Path uploadPath = Paths.get(uploadPathStr);
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory: " + uploadPath, e);
        }

        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        fileStorage.setUploadFolder(uploadPath.resolve(fileStorage.getHashId() + "." + fileStorage.getExtension()).toString());

        fileStorageRepository.save(fileStorage);
        Path filePath = uploadPath.resolve(String.format("%s.%s", fileStorage.getHashId(), fileStorage.getExtension()));

        try {
            multipartFile.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Could not save file to path: " + filePath, e);
        }

        return fileStorage;
    }

    private String getExtension(String fileName) {

        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot < fileName.length() - 1) {
                return fileName.substring(dot + 1);
            }
        }
        return null;
    }

}
