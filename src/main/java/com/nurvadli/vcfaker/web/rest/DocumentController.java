package com.nurvadli.vcfaker.web.rest;

import com.nurvadli.vcfaker.dto.UploadDto;
import com.nurvadli.vcfaker.dto.UploadFileResponseDto;
import com.nurvadli.vcfaker.service.MerchantDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/merchant-document")
public class DocumentController {

    private final MerchantDocumentService merchantDocumentService;

    @PostMapping(value = "/upload")
    public UploadFileResponseDto uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId, @RequestParam("documentType") String documentType) {
        String fileName = merchantDocumentService.storeFile(UploadDto.builder()
                .file(file)
                .userId(userId)
                .documentType(documentType)
                .build());

        String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponseDto(
                fileName,
                fileDownloadUrl,
                file.getContentType(),
                file.getSize());
    }

    @GetMapping(value = "/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("userId") String userId, @RequestParam("documentType") String documentType, HttpServletRequest httpServletRequest) {
        String fileName = merchantDocumentService.getDocumentName(userId, documentType);
        Resource resource = null;
        if (null != fileName && !fileName.isEmpty()) {
            try {
                resource = merchantDocumentService.loadFileAsResource(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //-- try to determine file's content type
            String contentType = null;
            try {
                contentType = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //-- fallback to default contentType if type could not be determined
            if (null == contentType) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }
}
