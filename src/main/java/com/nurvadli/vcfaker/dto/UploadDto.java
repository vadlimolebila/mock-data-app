package com.nurvadli.vcfaker.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UploadDto {
    private MultipartFile file;
    private String userId;
    private String documentType;
}
