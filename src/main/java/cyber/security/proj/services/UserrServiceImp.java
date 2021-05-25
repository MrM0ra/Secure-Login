package cyber.security.proj.services;

import java.util.List;
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
	public void deleteUser(Userr user) {
		userRep.delete(user);
	}

	@Override
	@Transactional
	public Optional<Userr> findUserr(long id) {
		return userRep.findById(id);
	}

	public List<Userr> findByUserName(String userName){
		return userRep.findByUserName(userName);
	}
	
	@Override
	public Iterable<Userr> findAll(){
		return userRep.findAll();
	}
	
	@Transactional
	public Optional<Userr> editUser(Userr user){
		userRep.save(user);
		return userRep.findById(user.getUserId());
	}
}
