package cyber.security.proj.controller.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cyber.security.proj.model.Person;
import cyber.security.proj.model.PersonRole;
import cyber.security.proj.model.PersonRolePK;
import cyber.security.proj.model.Rolee;
import cyber.security.proj.model.Userr;
import cyber.security.proj.model.Userr.AddUser;
import cyber.security.proj.repositories.PersonRepository;
import cyber.security.proj.repositories.PersonRoleRepository;
import cyber.security.proj.repositories.RoleeRepository;
import cyber.security.proj.services.UserrServiceImp;

/**
 * Clase que maneja las rutas de la aplicación.
 * @author Victor
 */
@Controller
public class ApplicationControllerImp {

	/*
	 * Servicio para guardar, editar, borrar y consultar todos los usuarios de la aplicación.
	 */
	@Autowired
	private UserrServiceImp uServ;
	
	/**
	 * Repositorio para guardar, editar, borrar y consultar todas las personas de la aplicación.
	 */
	@Autowired
	private PersonRepository pRep;
	
	/**
	 * Repositorio para guardar, editar, borrar y consultar todos los roles de la aplicación.
	 */
	@Autowired
	private RoleeRepository rRep;
	
	/**
	 * Repositorio para guardar, editar, borrar y consultar todos los personas-rol de la aplicación.
	 */
	@Autowired
	private PersonRoleRepository prolRep;
	
	/**
	 * Ruta inicial del aplicativo web, planeando como implementar las diferentes vistas.
	 * @param model
	 * @return : plantilla a renderizar
	 */
	@PreAuthorize("hasRole('Administrador'||'Regular')")
	@RequestMapping(value="/", method= {RequestMethod.GET, RequestMethod.POST})
	public String index(Model model) {
		return "index";
	}
	
	/**
	 * Vista inicial del aplicativo web, inicio de sesion de usuarios.
	 * @return : plantilla a renderizar, login.
	 */
	@RequestMapping(value="/login", method= {RequestMethod.GET, RequestMethod.POST})
	public String login() {
		return "login";
	}
	
	/**
	 * Método que muestra la página para el registro de un usuario.
	 * @param model : modelo al que se agregará un nuevo usuario en blanco.
	 * @return Plantilla para que el usuario pueda agregar valores.
	 */
	@GetMapping("/sign-in")
	public String signIn(Model model) {
		model.addAttribute("user", new Userr());
		return "register";
	}

	/**
	 * Método que recibe el usuario y contraseña suministrados por el usuario. 
	 * @param user : el objeto usuario que viene en la plantilla con el usuario y contraseña.
	 * @param result : objeto que valida si en los campos del usuario hay alguna irregularidad.
	 * @param model : modelo del que se obtiene el objeto user.
	 * @return Plantilla de inicio de sesión : si no hay errores en los datos ingresados por el usuario.
	 * 			Plantilla de registro : si hay algun error en los datos ingresados por el usuario.
	 */
	@PostMapping("/sign-in")
	public String signIn(@Validated({AddUser.class}) @ModelAttribute("user")Userr user, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "register";
		}else {
			Person p = new Person();
			List<PersonRole> roles=new ArrayList<>();
			
			Rolee rol=new Rolee();
			rol.setRoleName("Regular");
			
			rRep.save(rol);
			pRep.save(p);
			
			PersonRolePK pk=new PersonRolePK();
			pk.setPersPersId(p.getPersId());
			pk.setRoleRoleId(rol.getRoleId());
			
			PersonRole pr=new PersonRole();
			pr.setRolee(rol);
			pr.setPerson(p);
			pr.setId(pk);
			
			roles.add(pr);
			
			user.setPerson(p);
			user.setUserPassword("{noop}"+user.getUserPassword());
			
			prolRep.save(pr);
			uServ.saveUserr(user);
			
			Userr userEd2=new Userr();
			userEd2.setUserId(uServ.findUserr(user.getUserId()).get().getUserId());
			userEd2.setPerson(uServ.findUserr(user.getUserId()).get().getPerson());
			userEd2.setUserName(uServ.findUserr(user.getUserId()).get().getUserName());
			userEd2.setUserPassword(uServ.findUserr(user.getUserId()).get().getUserPassword());
			userEd2.getPerson().setPersonRoles(roles);
			
			uServ.editUser(userEd2);
			
		}
		return "redirect:/login/";
	}
	
	/**
	 * Método que muestra una página con todos los usuarios de rol "Regular" que existen en el sistema.
	 * @param model.
	 * @return Página con un listado de todos los usuarios del sistema.
	 */
	@PreAuthorize("hasRole('Administrador')")
	@GetMapping("/users/")
	public String showUsers(Model model) {
		model.addAttribute("users", uServ.findAll());
		return "users";
	}
	
	/**
	 * Método que setea en blanco la contraseña de un usuario dado su id.
	 * @param id : id del usuario del cual se quiere poner la contraseña en blanco.
	 * @param model : modelo del que se recupera el id dado del usuario.
	 * @return : Plantilla de usuarios si no hay ningun error con el id dado.
	 * 		IllegalArgumentException si hay error con el id dado.
	 */
	@SuppressWarnings("finally")
	@PreAuthorize("hasRole('Administrador')")
	@GetMapping("/users/blank-pwd/{id}")
	public String blankPwd(@PathVariable("id") long id, Model model) {
		if(id==1) {
			throw new IllegalArgumentException("Invalid Autotransition Id:" + id);
		}else {
			try {
				Optional<Userr> u = uServ.findUserr(id);
				if(u.isEmpty()) {
					throw new IllegalArgumentException("Invalid Autotransition Id:" + id);
				}else {
					u.get().setUserPassword("{noop} ");
					uServ.editUser(u.get());
					model.addAttribute("users", uServ.findAll());
					return "users";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				return "users";
			}
		}
	}
	
	
	@PreAuthorize("hasRole('Administrador')")
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		if(id==1) {
			throw new IllegalArgumentException("Invalid Autotransition Id:" + id);
		}else {
			try {
				Optional<Userr> u = uServ.findUserr(id);
				if(u.isEmpty()) {
					throw new IllegalArgumentException("Invalid Autotransition Id:" + id);
				}else {
					uServ.deleteUser(u.get());
					model.addAttribute("users", uServ.findAll());
					return "users";
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return "";
		}
	}
	
	@GetMapping("/pwd-change/")
	public String pwdChange(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String  usrName = auth.getPrincipal().toString().split("; ")[0].split(": ")[2];
		Userr u = uServ.findByUserName(usrName).get(0);
		u.setUserPassword(u.getUserPassword().split("}")[1]);
		model.addAttribute("user", u);
		return "pwd-change";
	}
	
	@PostMapping("/pwd-change/")
	public String pwdChangePost(@Validated({AddUser.class}) @ModelAttribute("user")Userr user, BindingResult result, 
			Model model) {
		if(result.hasErrors()) {
			return "pwd-change";
		}else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String  usrName = auth.getPrincipal().toString().split("; ")[0].split(": ")[2];
			Userr u = uServ.findByUserName(usrName).get(0);
			u.setUserPassword("{noop}"+user.getUserPassword());
			uServ.editUser(u);
		}
		return "index";
	}
	
	/**
	 * Método que muestra una página con la información de losúltimos dos inicios de sesión del usuario actual.
	 * @param model : Modelo al cual se cargará la información del usuario actual.
	 * @return : Página que muestra los dos últimos inicios de sesión del usuario actual.
	 */
	@GetMapping("/last-log/")
	public String lastLogin(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String  usrName = auth.getPrincipal().toString().split("; ")[0].split(": ")[2];
		model.addAttribute("user", uServ.findByUserName(usrName).get(0));
		return "last-login";
	}
	
	/**
	 * Método que muestra una página de acceso denegado si el usuario intenta acceder a una ruta a la que no tiene permiso.
	 * @param model : modelo.
	 * @return : plantilla de acceso denegado.
	 */
	@GetMapping("/access-denied")
	public String accesDenied(Model model) {
		return "denied";
	}
	
}