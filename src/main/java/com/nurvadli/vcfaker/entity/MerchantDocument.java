package com.nurvadli.vcfaker.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "merchant_documents")
public class MerchantDocument implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(strategy = "system-uuid", name = "uuid2")
    private String id;

    private String userId;

    private String fileName;
    private String documentType;
    private String documentFormat;
    private String uploadDir;

}
