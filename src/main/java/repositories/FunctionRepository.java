package repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import entities.Function;

@RepositoryRestResource
public interface FunctionRepository extends JpaRepository< Function , Long >{

}
