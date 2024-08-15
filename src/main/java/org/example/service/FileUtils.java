package org.example.service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;

@Slf4j
@Service
public class FileUtils {

    public static final String filePath = "/FilePath/";

    @Autowired
    private BlobContainerClient azureStorageClient;

    public String uploadFileInAzureStorage( MultipartFile fileContent)  {

        if (fileContent.isEmpty())
            log.error("File content is NULL");

        BlobClient blobClient = null;
        String fullFilePath = filePath + File.separator + fileContent.getOriginalFilename();
        try {
            blobClient = azureStorageClient.getBlobClient(fullFilePath);
            blobClient.getBlockBlobClient().upload(new BufferedInputStream(fileContent.getInputStream()), fileContent.getSize(), true);
            log.info(fileContent.getOriginalFilename() + " uploaded successfully into Azure File Storage");
        } catch (IOException e) {
            log.error("Exception occurred during file upload : " + e.getMessage() + " for " + fileContent.getOriginalFilename());
        }
        return blobClient.getBlobUrl() + "?"
                + blobClient.generateSas(new BlobServiceSasSignatureValues(OffsetDateTime.now().plusDays(30),
                        new BlobSasPermission().setReadPermission(true)).setStartTime(OffsetDateTime.now()));
    }

    public InputStreamResource downloadFile(String fileName) {
        InputStream inputStream = null;
        try {
            // Construct the full file path in Azure Blob Storage
            String fullFilePath = filePath + "/" + fileName;
            log.info("filePath : " + filePath + "/" + fileName);

            BlobClient blobClient = azureStorageClient.getBlobClient(fullFilePath);

            if (!blobClient.exists())
                log.error("File does not exist in Azure Blob Storage.");

            BinaryData binaryData = blobClient.downloadContent();
            inputStream = binaryData.toStream();
        } catch (Exception e) {
            log.error("Exception occurred during file download : " + e.getMessage() + " for " + fileName);
        }
        return new InputStreamResource(inputStream);
    }
}
