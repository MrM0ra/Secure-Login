package cyber.security.proj.controller.implementation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationControllerImp {

	/**
	 * Ruta inicial del aplicativo web, planeando como implementar las diferentes vistas
	 * @param model
	 * @return : plantilla a renderizar
	 */
	@PreAuthorize("hasRole('Administrador'||'Operador')")
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	/**
	 * Vista inicial del aplicativo web, inicio de sesion de usuarios
	 * @param model
	 * @return : plantilla a renderizar, login
	 */
	@GetMapping("/login")
	public String login(Model model) {
		return "index-login";
	}

	@GetMapping("/access-denied")
	public String accesDenied(Model model) {
		return "denied";
	}

	
	
}
