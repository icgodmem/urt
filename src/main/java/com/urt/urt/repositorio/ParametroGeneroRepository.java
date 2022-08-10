package com.urt.urt.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urt.urt.model.ParametroGenero;


@Repository
public interface ParametroGeneroRepository extends JpaRepository<ParametroGenero, Long> {}
