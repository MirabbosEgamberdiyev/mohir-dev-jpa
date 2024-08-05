package fido.uz.mohir_dev_jpa.controller;

import fido.uz.mohir_dev_jpa.entity.FileStorage;
import fido.uz.mohir_dev_jpa.enums.BankFileUploadRequest;
import fido.uz.mohir_dev_jpa.exception.ResponseMessage;
import fido.uz.mohir_dev_jpa.service.FileStorageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController

@RequestMapping("/api/files")
@Tag(name = "FileStorage", description = "The File Storage API")
public class FileStorageController {
    private final FileStorageService fileStorageService;


    @Value("${upload.server.folder}")
    private String serverFolderPath;

    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Operation(
            summary = "Upload file",
            description = "Upload file",
            tags = {"FileStorage"}
    )
    @ApiResponse(responseCode = "201",
            description = "Student upload file",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @PostMapping("/upload-file")
    public ResponseEntity<?> upload(@Valid @RequestParam("file")MultipartFile multipartFile) {
        try {
            FileStorage fileStorage = fileStorageService.save(multipartFile);
            return ResponseEntity.ok(fileStorage);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error creating student: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Upload file",
            description = "Upload a file to the server",
            tags = {"FileStorage"}
    )
    @ApiResponse(responseCode = "201",
            description = "File uploaded successfully",
            content = @Content(
                    mediaType = "multipart/form-data",
                    schema = @Schema(implementation = FileStorage.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Invalid file")
    @PostMapping(path = "/upload-file-use-swagger", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@ModelAttribute("request") @Validated BankFileUploadRequest request) {
        try {
            FileStorage fileStorage = fileStorageService.save(request.getFile());
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file", e);
        }
    }

    @Operation(
            summary = "Preview file",
            description = "Preview a file based on its hashId",
            tags = {"FileStorage"}
    )

    @GetMapping("/file-preview/{hashId}")
    public ResponseEntity<Resource> previewFile(@PathVariable String hashId) {
        try {
            Resource resource = fileStorageService.preview(hashId);

            Path filePath = Path.of(resource.getURI());
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
            summary = "Delete file",
            description = "Delete a file based on its hashId",
            tags = {"FileStorage"}
    )
    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity<String> deleteFile(@PathVariable String hashId) {
        try {
            fileStorageService.deleteFile(hashId);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting file: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Download file",
            description = "Download a file based on its hashId",
            tags = {"FileStorage"}
    )
    @GetMapping("/download/{hashId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String hashId) {
        try {
            Resource resource = fileStorageService.downloadFile(hashId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}