package cyber.security.proj.controller.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

@Controller
public class ApplicationControllerImp {

	@Autowired
	private UserrServiceImp uServ;
	
	@Autowired
	private PersonRepository pRep;
	
	@Autowired
	private RoleeRepository rRep;
	
	@Autowired
	private PersonRoleRepository prolRep;
	
	/**
	 * Ruta inicial del aplicativo web, planeando como implementar las diferentes vistas
	 * @param model
	 * @return : plantilla a renderizar
	 */
	@PreAuthorize("hasRole('Administrador'||'Regular')")
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
		model.addAttribute("user", new Userr());
		return "index-login";
	}
	
	@PostMapping("/login")
	public String login() {
		return "";
	}
	
	@GetMapping("/sign-in")
	public String signIn(Model model) {
		model.addAttribute("user", new Userr());
		return "register";
	}

	@PostMapping("/sign-in")
	public String signIn(@Validated({AddUser.class}) @ModelAttribute("user")Userr user, BindingResult result, 
			Model model) {
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
			userEd2.setUserId(uServ.findUserr(user).get().getUserId());
			userEd2.setPerson(uServ.findUserr(user).get().getPerson());
			userEd2.setUserName(uServ.findUserr(user).get().getUserName());
			userEd2.setUserPassword(uServ.findUserr(user).get().getUserPassword());
			userEd2.getPerson().setPersonRoles(roles);
			
			uServ.editUser(userEd2);
			
		}
		return "index-login";
	}
	
	@GetMapping("/access-denied")
	public String accesDenied(Model model) {
		return "denied";
	}

	
	
}
