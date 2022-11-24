package com.nurvadli.vcfaker.repository;

import com.nurvadli.vcfaker.entity.MerchantDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantDocumentRepository extends JpaRepository<MerchantDocument, String> {
    Optional<MerchantDocument> findByUserIdAndDocumentType(String userId, String documentType);

    @Query("SELECT m.fileName from MerchantDocument m where m.userId=?1 and m.documentType=?2")
    String getMerchantDocumentFilePath(String userId, String documentType);

}
