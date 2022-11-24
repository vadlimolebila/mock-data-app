package com.nurvadli.vcfaker.service.impl;

import com.nurvadli.vcfaker.service.DocToPdfService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class DocToPdfServiceImpl implements DocToPdfService {
    @Override
    public void convertDocToPdf(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        file.transferTo( new File("C:\\upload\\" + fileName));
        System.out.println();
    }


}
