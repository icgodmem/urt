package com.urt.urt.controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.urt.urt.model.ConsultadoVM;
import com.urt.urt.model.ConsultarVM;
import com.urt.urt.model.ParametroDocumento;
import com.urt.urt.model.ParametroGenero;
import com.urt.urt.model.Usuario;
import com.urt.urt.model.UsuarioVM;
import com.urt.urt.repositorio.ParametroDocumentoRepository;
import com.urt.urt.repositorio.ParametroGeneroRepository;
import com.urt.urt.repositorio.UsuarioRepository;

@Controller
public class MainController {
	

	@Autowired
	private final UsuarioRepository usuarioRepository;
	
	@Autowired
	private final ParametroDocumentoRepository parametroDocumentoRepository;
	
	@Autowired
	private final ParametroGeneroRepository parametroGeneroRepository;

	public MainController(ParametroDocumentoRepository parametroDocumentoRepository,
			ParametroGeneroRepository parametroGeneroRepository, UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.parametroDocumentoRepository = parametroDocumentoRepository;
		this.parametroGeneroRepository = parametroGeneroRepository;
	}


	
	@RequestMapping("/crear")
	public String crear(Model model) {
		String mensajeError = "";
		model.addAttribute("usuario", new UsuarioVM());
		//Get parametros
		List<ParametroDocumento> parametrodoc= parametroDocumentoRepository.findAll();
		List<ParametroGenero> parametrogen = parametroGeneroRepository.findAll();
		
		model.addAttribute("parametrogen",parametrogen);
		model.addAttribute("parametrodoc",parametrodoc);
		model.addAttribute("mensajeError", mensajeError);
		
		return "crear";
	}
	
	@PostMapping("/crear")
	public String crearSubmit(@ModelAttribute UsuarioVM usuario, Model model) {
		String mensajeError = "";
		model.addAttribute("usuario", new UsuarioVM());
		List<ParametroDocumento> parametrodoc= parametroDocumentoRepository.findAll();
		List<ParametroGenero> parametrogen = parametroGeneroRepository.findAll();
		
		model.addAttribute("parametrogen",parametrogen);
		model.addAttribute("parametrodoc",parametrodoc);
		
		
		//Get tipo doc object
		Optional<ParametroDocumento> tipoDoc= parametroDocumentoRepository.findById(usuario.getIDParametroDocumento());
		Optional<ParametroGenero> tipoGen = parametroGeneroRepository.findById(usuario.getIDParametroGenero());
		
		if(usuario.getPrimerNombre().equals("") || usuario.getSegundoNombre().equals("") 
			||usuario.getPrimerApellido().equals("") || usuario.getSegundoApellido().equals("")
			|| usuario.getNombreUsuario().equals("")|| usuario.getCorreoElectronico().equals("")
				|| usuario.getNumeroDocumento() ==null)
		{
			mensajeError += "Llenar por favor todos los campos";
			model.addAttribute("mensajeError", mensajeError);
			return "crear";
		}
		
		if(!tipoDoc.isPresent() || !tipoGen.isPresent()) {
			mensajeError += "Seleccione documento y/o genero de la lista";
			model.addAttribute("mensajeError", mensajeError);
			return "crear";
		}
		//Check doc number duplicates
		Optional<Usuario> usuarioInDB = usuarioRepository.findByNumeroDocumento(usuario.getNumeroDocumento());
		if(usuarioInDB.isPresent()) {
			mensajeError += "Numero de documento ya existe";
			model.addAttribute("mensajeError", mensajeError);
			return "crear";
		}
		//Check user name duplicates
		usuarioInDB = usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario());
		if(usuarioInDB.isPresent()) {
			mensajeError += "Nombre de usuario ya existe";
			model.addAttribute("mensajeError", mensajeError);
			return "crear";
		}
		
		Usuario prepararUsuario = new Usuario();
		prepararUsuario.setPrimerNombre(usuario.getPrimerNombre());
		prepararUsuario.setPrimerApellido(usuario.getPrimerApellido());
		prepararUsuario.setSegundoNombre(usuario.getSegundoNombre());
		prepararUsuario.setSegundoApellido(usuario.getSegundoApellido());
		prepararUsuario.setNumeroDocumento(usuario.getNumeroDocumento());
		prepararUsuario.setNombreUsuario(usuario.getNombreUsuario());
		prepararUsuario.setCorreoElectronico(usuario.getCorreoElectronico());
		prepararUsuario.setParametroDocumento(tipoDoc.get());
		prepararUsuario.setParametroGenero(tipoGen.get());
		
		try {
			usuarioRepository.save(prepararUsuario);
			mensajeError += "Usuario ha sido creado";
			model.addAttribute("mensajeError", mensajeError);
			return "crear";
		} catch (Exception e) {
			mensajeError += "Ha ocurrido un error";
			model.addAttribute("mensajeError", mensajeError);
			return "crear";
		}
		
	}
	
	@RequestMapping("/consultar")
	public String consultar(Model model) {
		String mensajeError = "";
		model.addAttribute("usuario", new ConsultarVM());
		model.addAttribute("consultado", new ConsultadoVM());
		model.addAttribute("encontrado", false);
		
		model.addAttribute("mensajeError", mensajeError);
		return "consultar";
	}
	
	@PostMapping("/consultar")
	public String ejecutarConsulta(@ModelAttribute ConsultarVM consultar,Model model) {
		String mensajeError = "";
		model.addAttribute("usuario", new ConsultarVM(consultar.getNumeroDocumento(),consultar.getNombreUsuario()));
		

			if(consultar.getNumeroDocumento()==null && consultar.getNombreUsuario()==null) {
				model.addAttribute("consultado", new ConsultadoVM());
				model.addAttribute("encontrado", false);
				return "consultar";
			}
			//Execute search
			if(consultar.getNumeroDocumento()!=null) {
				Optional<Usuario> usuarioPorDoc=usuarioRepository.findByNumeroDocumento(consultar.getNumeroDocumento());
				if(usuarioPorDoc.isPresent()) {
					Usuario user= usuarioPorDoc.get();
					ConsultadoVM consultado = new ConsultadoVM(
							user.getId(),
							user.getPrimerNombre()+" "+user.getSegundoNombre()+" "+user.getPrimerApellido()+" "+user.getSegundoApellido(),
							user.getParametroDocumento().getTipoDocumento(),
							user.getNumeroDocumento(),
							user.getParametroGenero().getGenero());
					model.addAttribute("consultado", consultado);
					model.addAttribute("encontrado", true);
					model.addAttribute("idborrar", consultado.getId());
					return "consultar";
				}
			}
			if(consultar.getNombreUsuario()!=null) {
				Optional<Usuario> usuarioPorUser=usuarioRepository.findByNombreUsuario(consultar.getNombreUsuario());
				if(usuarioPorUser.isPresent()) {
					Usuario user= usuarioPorUser.get();
					ConsultadoVM consultado = new ConsultadoVM(
							user.getId(),
							user.getPrimerNombre()+" "+user.getSegundoNombre()+" "+user.getPrimerApellido()+" "+user.getSegundoApellido(),
							user.getParametroDocumento().getTipoDocumento(),
							user.getNumeroDocumento(),
							user.getParametroGenero().getGenero());
					model.addAttribute("consultado", consultado);
					model.addAttribute("encontrado", true);
					model.addAttribute("idborrar", consultado.getId());
					System.out.println(consultado.getId());
					return "consultar";
				}
			}
			
			
			return "consultar";
			
			//Delete usuario
		
		
		
	}
	
	
	@PostMapping("/borrar/{id}")
	public ModelAndView borrar(@ModelAttribute ConsultarVM consultar,@ModelAttribute ConsultadoVM consultado ,Model model,@PathVariable Long id) {
		String mensajeError = "";

		usuarioRepository.deleteById(id);

		model.addAttribute("usuario", new ConsultarVM());
		model.addAttribute("consultado", new ConsultadoVM());
		model.addAttribute("encontrado", false);
		
		return new ModelAndView("redirect:" + "/consultar");
	}

}
