package com.nurvadli.vcfaker.service;

import com.nurvadli.vcfaker.dto.UploadDto;
import org.springframework.core.io.Resource;

public interface MerchantDocumentService {

    String storeFile(UploadDto uploadDto);

    Resource loadFileAsResource(String fileName) throws Exception;

    String getDocumentName(String userId, String documentType);
}
