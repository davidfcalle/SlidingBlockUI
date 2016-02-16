package repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import entities.BoardxFunction;

import org.springframework.data.jpa.repository.JpaRepository;

@RepositoryRestResource
public interface BoardxFunctionRepository extends	JpaRepository< BoardxFunction , Long >
{

}
