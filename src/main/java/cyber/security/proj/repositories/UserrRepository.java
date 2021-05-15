package cyber.security.proj.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cyber.security.proj.model.Userr;

public interface UserrRepository extends CrudRepository<Userr, Long>{

	public List<Userr> findByUserName(String userName);
	
}
