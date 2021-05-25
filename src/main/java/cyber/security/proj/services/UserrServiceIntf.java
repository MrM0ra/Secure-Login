package cyber.security.proj.services;

import java.util.Optional;

import cyber.security.proj.model.Userr;

public interface UserrServiceIntf {

	public void deleteUser(Userr user);
	public Optional<Userr> findUserr(long id);
	public Iterable<Userr> findAll();
	public Optional<Userr> saveUserr(Userr usr);
	
}
