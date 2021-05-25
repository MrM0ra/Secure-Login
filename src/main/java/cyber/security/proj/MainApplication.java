package cyber.security.proj;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import cyber.security.proj.model.Person;
import cyber.security.proj.model.PersonRole;
import cyber.security.proj.model.PersonRolePK;
import cyber.security.proj.model.Rolee;
import cyber.security.proj.model.Userr;
import cyber.security.proj.repositories.PersonRepository;
import cyber.security.proj.repositories.PersonRoleRepository;
import cyber.security.proj.repositories.RoleeRepository;
import cyber.security.proj.services.UserrServiceImp;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

	@Autowired
	private UserrServiceImp uService;
	
	@Autowired
	private PersonRepository pRep;
	
	@Autowired
	private RoleeRepository rRep;
	
	@Autowired
	private PersonRoleRepository prolRep;
	
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
	}
	
	@Bean
	@Transactional
	public CommandLineRunner dummy(/*UserrService uService, PersonRepository pRep, 
			RoleeRepository rRep, PersonRoleRepository prolRep*/) {
		return (args) -> {
			
			//Creación del primer usuario
			Person per1=new Person();
			List<PersonRole> roles1=new ArrayList<>();
			
			Rolee rol1=new Rolee();
			rol1.setRoleName("Administrador");
			
			rRep.save(rol1);
			pRep.save(per1);
			
			PersonRolePK pk1=new PersonRolePK();
			pk1.setPersPersId(per1.getPersId());
			pk1.setRoleRoleId(rol1.getRoleId());
			

			PersonRole pr1=new PersonRole();
			pr1.setRolee(rol1);
			pr1.setPerson(per1);
			pr1.setId(pk1);
			
			roles1.add(pr1);
			
			Userr user1=new Userr();
			user1.setUserName("admin");
			user1.setUserPassword("{noop}admin");
			user1.setPerson(per1);
			
			prolRep.save(pr1);
			uService.saveUserr(user1);			

			Userr userEd=new Userr();
			userEd.setUserId(uService.findUserr(user1).get().getUserId());
			userEd.setPerson(uService.findUserr(user1).get().getPerson());
			userEd.setUserName(uService.findUserr(user1).get().getUserName());
			userEd.setUserPassword(uService.findUserr(user1).get().getUserPassword());
			userEd.getPerson().setPersonRoles(roles1);
			
			uService.editUser(userEd);
			
			//Creación del segundo usuario
			Person p2=new Person();
			List<PersonRole> roles2=new ArrayList<>();
			
			Rolee rol2=new Rolee();
			rol2.setRoleName("Operador");
			
			rRep.save(rol2);
			pRep.save(p2);
			
			PersonRolePK pk2=new PersonRolePK();
			pk2.setPersPersId(p2.getPersId());
			pk2.setRoleRoleId(rol2.getRoleId());
			
			PersonRole pr2=new PersonRole();
			pr2.setRolee(rol2);
			pr2.setPerson(p2);
			pr2.setId(pk2);
			
			roles2.add(pr2);

			Userr user2=new Userr();
			user2.setUserName("usuario-operador");
			user2.setUserPassword("{noop}contraseña345");
			user2.setPerson(p2);
			
			prolRep.save(pr2);
			uService.saveUserr(user2);
			
			Userr userEd2=new Userr();
			userEd2.setUserId(uService.findUserr(user2).get().getUserId());
			userEd2.setPerson(uService.findUserr(user2).get().getPerson());
			userEd2.setUserName(uService.findUserr(user2).get().getUserName());
			userEd2.setUserPassword(uService.findUserr(user2).get().getUserPassword());
			userEd2.getPerson().setPersonRoles(roles2);
			
			uService.editUser(userEd2);
		};
	}
	
}
