package com.nurvadli.vcfaker.dto;

import lombok.Data;

@Data
public class UploadFileResponseDto {

    private String fileName;
    private String fileDownloadUrl;
    private String contentType;
    private long fileSize;

    public UploadFileResponseDto(String fileName, String fileDownloadUrl, String contentType, long fileSize) {
        this.fileName = fileName;
        this.fileDownloadUrl = fileDownloadUrl;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }
}
