package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import entities.Session;

@RepositoryRestResource
public interface SessionRepository extends JpaRepository< Session , Long >{

}
