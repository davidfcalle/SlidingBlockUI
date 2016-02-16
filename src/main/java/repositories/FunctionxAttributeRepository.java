package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import entities.FunctionxAttribute;

@RepositoryRestResource
public interface FunctionxAttributeRepository extends JpaRepository< FunctionxAttribute, Long >{

}
