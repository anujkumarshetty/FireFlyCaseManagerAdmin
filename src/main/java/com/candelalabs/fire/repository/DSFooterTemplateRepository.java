package com.candelalabs.fire.repository;

import com.candelalabs.fire.domain.DSFooterTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DSFooterTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DSFooterTemplateRepository extends JpaRepository<DSFooterTemplate, Long> {

}
