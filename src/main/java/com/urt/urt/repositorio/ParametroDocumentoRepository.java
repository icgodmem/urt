package com.urt.urt.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urt.urt.model.ParametroDocumento;



@Repository
public interface ParametroDocumentoRepository extends JpaRepository<ParametroDocumento, Long> {}
