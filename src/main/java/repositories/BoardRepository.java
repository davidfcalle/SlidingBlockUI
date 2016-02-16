package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import entities.Board;

@RepositoryRestResource
public interface BoardRepository extends JpaRepository< Board, Long >{

}
