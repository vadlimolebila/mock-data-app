package com.nurvadli.vcfaker.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocToPdfService {

    void convertDocToPdf(MultipartFile file) throws Exception ;
}
