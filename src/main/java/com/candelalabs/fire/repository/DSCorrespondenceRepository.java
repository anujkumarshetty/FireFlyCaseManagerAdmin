package com.candelalabs.fire.repository;

import com.candelalabs.fire.domain.DSCorrespondence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DSCorrespondence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DSCorrespondenceRepository extends JpaRepository<DSCorrespondence, Long> {

}
