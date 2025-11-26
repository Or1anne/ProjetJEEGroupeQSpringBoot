package org.example.projetjeegroupeqspringboot.repository;

import org.example.projetjeegroupeqspringboot.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepertory extends JpaRepository<Project,Long> {
}
