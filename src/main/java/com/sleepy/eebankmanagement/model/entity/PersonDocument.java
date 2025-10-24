package com.sleepy.eebankmanagement.model.entity;


import com.sleepy.eebankmanagement.Model.entity.enums.DocumentType;
import com.sleepy.eebankmanagement.Model.entity.enums.VerificationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "person_documents")
public class PersonDocument extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Column(name = "document_number", length = 50)
    private String documentNumber;

    @Column(name = "file_name", nullable = false, length = 255)
    @NotBlank(message = "File name is required")
    private String fileName;

    @Column(name = "file_path", nullable = false, length = 500)
    @NotBlank(message = "File path is required")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @Column(name = "verified_by", length = 100)
    private String verifiedBy;

    @Column(name = "verification_notes", length = 500)
    private String verificationNotes;



}