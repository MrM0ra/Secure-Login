package cyber.security.proj.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cyber.security.proj.model.Userr;
import cyber.security.proj.repositories.UserrRepository;

@Service
public class UserrServiceImp implements UserrServiceIntf {

	private UserrRepository userRep;
	
	
	@Autowired
	public UserrServiceImp(UserrRepository userRep) {
		this.userRep = userRep;
	}

	@Override
	@Transactional
	public Optional<Userr> saveUserr(Userr usr) {
		userRep.save(usr);
		return userRep.findById(usr.getUserId());
	}

	@Override
	@Transactional
	public Optional<Userr> findUserr(Userr usr) {
		return userRep.findById(usr.getUserId());
	}

	@Transactional
	public Optional<Userr> editUser(Userr user){
		userRep.save(user);
		return userRep.findById(user.getUserId());
	}
}
