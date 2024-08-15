package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.service.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/file/")
public class FileController {

    @Autowired
    FileUtils fileUtils;

    @PostMapping(value = "/upload")
    public String uploadFile(@RequestPart MultipartFile file) {
        return fileUtils.uploadFileInAzureStorage(file);
    }

    @GetMapping(value = "/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(
            @PathVariable("fileName") String fileName) {
        InputStreamResource file = fileUtils.downloadFile(fileName);

        // Set the appropriate content type and header for the file download
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");

        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }
}
