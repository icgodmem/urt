package com.urt.urt.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.urt.urt.model.Usuario;
import com.urt.urt.repositorio.UsuarioRepository;
import com.urt.urt.shared.GenericResponse;


@RestController
@RequestMapping("/api")
@Transactional
public class UsuarioResource {

	
	private static final String ENTITY_NAME = "usuario";
	
	private final UsuarioRepository usuarioRepository;

	public UsuarioResource(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	@PostMapping("/usuarios")
    public GenericResponse createUsuario(@Valid @RequestBody Usuario usuario) throws URISyntaxException {
		
		
		//Check duplicates
		Optional<Usuario> inDB = usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario());
		if(inDB.isPresent()) {
			return new GenericResponse("Usuario ya existe");
		}
		inDB = usuarioRepository.findByNumeroDocumento(usuario.getNumeroDocumento());
		if(inDB.isPresent()) {
			return new GenericResponse("Usuario ya existe");
		}
		
		
		Usuario result = usuarioRepository.save(usuario);
		
		return new GenericResponse("Usario registrado");
		
	}
	
	@GetMapping("/usuarios")
	public List<Usuario> getAllUsuarios() {
		return usuarioRepository.findAll();
	}

	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}
		return ResponseEntity.ok(new Usuario());
	}
	@GetMapping("/usuarios/buscardoc/{documento}")
	public ResponseEntity<Usuario> getUsuarioByDocumento(@PathVariable String documento) {
		Optional<Usuario> usuario = usuarioRepository.findByNumeroDocumento(Long.parseLong(documento));
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}
		return ResponseEntity.ok(new Usuario());
	}
	@GetMapping("/usuarios/buscaruser/{nombreusuario}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable String nombreusuario) {
		Optional<Usuario> usuario = usuarioRepository.findByNombreUsuario(nombreusuario);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}
		return ResponseEntity.ok(new Usuario());
	}

	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isPresent()) {
			usuarioRepository.deleteById(id);
		}
		return ResponseEntity.noContent().build();
	}

}
