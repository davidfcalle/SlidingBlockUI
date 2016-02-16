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
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import entities.Person;
import repositories.PersonRepository;

@EnableWebMvc
@SpringBootApplication
@EnableJpaRepositories(basePackages="repositories")
@EntityScan(basePackages={"entities"})
@ComponentScan(basePackages={"rest.controllers", "repositories", "co.edu.javeriana.galileo", "socket.controllers", "web.controllers"})
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


@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/hello").withSockJS();
	}
}



/**
 * Manejador de recursos estáticos Clase encargada de configurar cómo se manejan
 * los archivos estáticos
 *
 * @author David Calle
 * @version 1.0
 * @since
 */

@Configuration
class StaticResourceConfiguration extends WebMvcConfigurerAdapter {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS).setCachePeriod(0);
	}
}

/**
 * Clase encargada de la configuración de spring MVC
 *
 * @author David Calle
 * @version 1.0
 * @since 2015-12-14
 */
@EnableWebMvc
@ComponentScan("org.springframework.security.samples.mvc")
@Configuration
@Component
class WebMvcConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
}

