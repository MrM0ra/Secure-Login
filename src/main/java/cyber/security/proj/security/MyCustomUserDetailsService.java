package cyber.security.proj.security;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cyber.security.proj.model.Userr;
import cyber.security.proj.repositories.UserrRepository;

@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserrRepository userRep;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Userr userApp = userRep.findByUserName(username).get(0);
		if (userApp != null) {
			if(userApp.getLastLastLog()!=null) {
				userApp.setLastLastLog(userApp.getLastLog());
				userApp.setLastLog(LocalDateTime.now());
				userRep.save(userApp);
			}else {
				userApp.setLastLastLog(LocalDateTime.now());
				userApp.setLastLog(userApp.getLastLastLog());
				userRep.save(userApp);
			}
			User.UserBuilder builder = User.withUsername(username).password(userApp.getUserPassword()).
					roles(userApp.getPerson().getPersonRoles().get(0).getRolee().getRoleName());
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
}