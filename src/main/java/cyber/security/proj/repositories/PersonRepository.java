package cyber.security.proj.repositories;

import org.springframework.data.repository.CrudRepository;

import cyber.security.proj.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{

}
