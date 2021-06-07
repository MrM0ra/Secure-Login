package cyber.security.proj;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

	/**
	 * Hashing SALT for password hashing
	 */
	public static final String SALT = BCrypt.gensalt(12);
	
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
			user1.setUserName("admin12");
			String hashed = BCrypt.hashpw("admin12", SALT);
			user1.setUserPassword("{bcrypt}"+hashed);
			user1.setPerson(per1);
			
			prolRep.save(pr1);
			uService.saveUserr(user1);			

			Userr userEd=new Userr();
			userEd.setUserId(uService.findUserr(user1.getUserId()).get().getUserId());
			userEd.setPerson(uService.findUserr(user1.getUserId()).get().getPerson());
			userEd.setUserName(uService.findUserr(user1.getUserId()).get().getUserName());
			userEd.setUserPassword(uService.findUserr(user1.getUserId()).get().getUserPassword());
			userEd.getPerson().setPersonRoles(roles1);
			
			uService.editUser(userEd);
			
			//Creación del segundo usuario
			Person p2=new Person();
			List<PersonRole> roles2=new ArrayList<>();
			
			Rolee rol2=new Rolee();
			rol2.setRoleName("Regular");
			
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
			user2.setUserName("regular");
			hashed = BCrypt.hashpw("regular", SALT);
			user2.setUserPassword("{bcrypt}"+hashed);
			user2.setPerson(p2);
			
			prolRep.save(pr2);
			uService.saveUserr(user2);
			
			Userr userEd2=new Userr();
			userEd2.setUserId(uService.findUserr(user2.getUserId()).get().getUserId());
			userEd2.setPerson(uService.findUserr(user2.getUserId()).get().getPerson());
			userEd2.setUserName(uService.findUserr(user2.getUserId()).get().getUserName());
			userEd2.setUserPassword(uService.findUserr(user2.getUserId()).get().getUserPassword());
			userEd2.getPerson().setPersonRoles(roles2);
			
			uService.editUser(userEd2);
		};
	}
	
}
