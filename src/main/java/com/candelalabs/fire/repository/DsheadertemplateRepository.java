package com.candelalabs.fire.repository;

import com.candelalabs.fire.domain.Dsheadertemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dsheadertemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DsheadertemplateRepository extends JpaRepository<Dsheadertemplate, Long> {

}
