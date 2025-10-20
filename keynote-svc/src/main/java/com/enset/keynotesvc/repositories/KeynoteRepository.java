package com.enset.keynotesvc.repositories;

import com.enset.keynotesvc.models.Keynote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface KeynoteRepository extends JpaRepository<Keynote, Long> {
}
