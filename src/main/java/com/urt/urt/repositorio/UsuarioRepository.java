package com.urt.urt.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urt.urt.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByNumeroDocumento(Long numeroDocumento);
	Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
