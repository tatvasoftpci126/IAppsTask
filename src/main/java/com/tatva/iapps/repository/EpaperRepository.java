package com.tatva.iapps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tatva.iapps.entity.Epaper;


/**
 * Repository class for Epaper.
 */

@Repository
public interface EpaperRepository extends JpaRepository<Epaper, Long>, JpaSpecificationExecutor<Epaper> {

}
