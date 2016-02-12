package co.edu.javeriana.galileo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import entities.Person;
import repositories.PersonRepository;

@EnableWebMvc
@SpringBootApplication
@EnableJpaRepositories(basePackages="repositories")
@EntityScan(basePackages={"entities"})
@ComponentScan(basePackages={"rest.controllers", "repositories", "co.edu.javeriana.galileo"})
@EnableAutoConfiguration
public class ManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}
}
/**
 * Este componente se ejecuta como una aplicaciòn de línea de comandos una vez corre el servidor
 * @author David
 * Es un ejemplo de còmo usar la injecciòn de los respositorios
 *
 */
@Component
class CommandLineRun implements  CommandLineRunner{

	@Autowired
	private PersonRepository personRepository;
	
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Inicio");
		Arrays.asList("Alfredo", "Bermeo", "Juan Pablo", "Maria Jose", "Fabian").forEach( name ->{
			Person newPerson = new Person(null, name, name, 10);
			personRepository.save(newPerson);
		});
	}
}

@Configuration
class RestMVCConfiguration{
	@Bean
	public RepositoryRestConfigurer respositoryRestConfigurer(){
		return new RepositoryRestConfigurerAdapter(){
			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config){
				config.setBasePath("/api");
				config.exposeIdsFor(Person.class);
			}
		};
	}
	
}