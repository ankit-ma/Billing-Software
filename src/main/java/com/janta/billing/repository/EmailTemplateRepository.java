package com.janta.billing.repository;

import com.janta.billing.entity.EmailTemplates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplates,Long> {

    Optional<EmailTemplates> findByTemplateName(String templateName);
}
