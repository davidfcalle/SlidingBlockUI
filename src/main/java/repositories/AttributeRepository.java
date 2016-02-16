
package repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import entities.Attribute;

import org.springframework.data.jpa.repository.JpaRepository;

@RepositoryRestResource
public interface AttributeRepository extends JpaRepository< Attribute, Long >{

}
