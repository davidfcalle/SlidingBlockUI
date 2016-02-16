package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import entities.PersonxRole;

@RepositoryRestResource
public interface PersonxRoleRepository extends JpaRepository< PersonxRole, Long >{

}
