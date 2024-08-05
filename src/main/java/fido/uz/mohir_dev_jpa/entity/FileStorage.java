package fido.uz.mohir_dev_jpa.entity;

import fido.uz.mohir_dev_jpa.enums.FileStorageStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Entity
@Data
@Getter
@Setter
public class FileStorage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String extension;
    private Long fileSize;
    private String contentType;
    private String hashId;
    private String uploadFolder;
    private FileStorageStatus fileStorageStatus;

}
