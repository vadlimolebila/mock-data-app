package com.nurvadli.vcfaker.service.impl;

import com.nurvadli.vcfaker.dto.UploadDto;
import com.nurvadli.vcfaker.entity.MerchantDocument;
import com.nurvadli.vcfaker.exception.DocumentStorageException;
import com.nurvadli.vcfaker.repository.MerchantDocumentRepository;
import com.nurvadli.vcfaker.service.MerchantDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@RequiredArgsConstructor
@Service
public class MerchantDocumentServiceImpl implements MerchantDocumentService {

    private final Path fileStorageLocation;

    private final MerchantDocumentRepository merchantDocumentRepository;

    @Override
    public String storeFile(UploadDto uploadDto) {
        String originalFileName = StringUtils.cleanPath(uploadDto.getFile().getOriginalFilename());
        String fileName = "";

        try {
            //-- check if the file's name contains invalid characters
            if (originalFileName.contains("..")) {
                throw new DocumentStorageException("Sorry filename contains invalid path sequence : " + originalFileName);
            }

            String fileExtension = "";
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch (Exception e) {
                log.error("Error to get file upload extension");
            }

            fileName = uploadDto.getUserId() + "_" + uploadDto.getDocumentType() + fileExtension;
            //-- copy file to the target location (Replacing existing with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(uploadDto.getFile().getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Optional<MerchantDocument> existingMerchantDocument = merchantDocumentRepository.findByUserIdAndDocumentType(
                    uploadDto.getUserId(),
                    uploadDto.getDocumentType());

            if (existingMerchantDocument.isPresent()) {
                MerchantDocument merchantDocument = existingMerchantDocument.get();
                merchantDocument.setDocumentFormat(uploadDto.getFile().getContentType());
                merchantDocument.setFileName(fileName);
                merchantDocumentRepository.save(merchantDocument);
            } else {
                MerchantDocument merchantDocument = new MerchantDocument();
                merchantDocument.setUserId(uploadDto.getUserId());
                merchantDocument.setDocumentFormat(uploadDto.getFile().getContentType());
                merchantDocument.setFileName(fileName);
                merchantDocument.setDocumentType(uploadDto.getDocumentType());
                merchantDocumentRepository.save(merchantDocument);
            }

            return fileName;
        } catch (IOException ex) {

            throw new DocumentStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

    }

    @Override
    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    @Override
    public String getDocumentName(String userId, String documentType) {
        return merchantDocumentRepository.getMerchantDocumentFilePath(userId, documentType);
    }
}
