package cyber.security.proj.services;

import java.util.Optional;

import cyber.security.proj.model.Userr;

public interface UserrServiceIntf {

	public Optional<Userr> saveUserr(Userr usr);
	public Optional<Userr> findUserr(Userr usr);
	
}
