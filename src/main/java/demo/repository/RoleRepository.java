package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import demo.domain.Role;

@RepositoryRestResource
public interface RoleRepository extends CrudRepository<Role, Long> {

}
