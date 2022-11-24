package com.nurvadli.vcfaker.entity;

import lombok.ToString;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.OffsetDateTime;

@MappedSuperclass
@ToString
public class BaseEntity implements Serializable {

    private String createdBy;
    private OffsetDateTime createdOn;
    private String modifiedBy;
    private OffsetDateTime modifiedOn;
}
