# Attribute Based Access Control for Spring Security
The Attribute Based Access Control (ABAC) for Spring Security is a Policy Enforcment Point (PEP) implementation which provides both method and web expressions to secure Spring Boot applications based on attributes evaluated against a policy from a Policy Decision Point (PDP) server.
The expressions are called ``#abac.evaluate(Category... categories)`` and  ``#abac.evaluateAttributes(String... attributes)`` which send authorization request based on [Json Profile of XACML 3.0 Specification](http://docs.oasis-open.org/xacml/xacml-json-http/v1.0/xacml-json-http-v1.0.html)

When using ``#abac.evaluateAttributes(String... attributes)``, the array of Strings must follow the format below:<br>
``access-subject:<attribute id>:<attribute values>``<br>
``resource:<attribute id>:<attribute values>``<br>
``action:<attribute id>:<attribute values>``<br>
``environment:<attribute id>:<attribute values>``<br>

When using ``#abac.evaluate(Category... categories)`` where the arguments is an array of Category objects, the following expressions may be used as arguments:<br>
``#abac.accessSubjectAttribute(<attribute id>, {<list of values>})``<br>
``#abac.resourceAttribute(<attribute id>, {<list of values>})``<br>
``#abac.actionAttribute(<attribute id>, {<list of values>})``<br>
``#abac.environmentAttribute(<attribute id>, {<list of values>})``<br>

## How to use
1. Build and publish this project to maven local: ``$ ./gradlew clean build publishToMavenLocal``
2. Add the two published artifacts from maven local together with the Spring Security to your Spring Boot project dependency. Example for gradle project:<br>
        ``compile('org.springframework.boot:spring-boot-starter-security')``<br>
   	``compile('com.github.joffryferrater:abac-pep-spring-security:0.5.1')``<br>
   	``compile('com.github.joffryferrater:xacml-resource-models:0.5.1')``<br>
3. Add the PDP server information in the application.properties file using the properties below:
    ````properties
    pdp.server.authorize-endpoint=http://localhost:8083/authorize
    pdp.server.username=pdp-user
    pdp.server.password=password
    pdp.server.print-authorization-request=true
    ````
    The ``pdp.server.print-authorization-request`` property is useful for debugging purposes. It prints the authorization request on the console.<br>
    ````bash
    01-01-2019 17:46:56.120 [http-nio-8888-exec-6] INFO  com.github.joffryferrater.pep.client.PdpClient.printAuthorizationRequest - Authorization Request --> {"Request":{"Resource":[{"Attribute":[{"AttributeId":"Attributes.resource.endpoint","Value":["helloWorld/someId"]}]}]}}
4. Include ``org.github.joffryferrater.pep`` in the scanBasePackages of your Spring Boot app. See example below: <br>
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
	scanBasePackages={
		"com.github.joffryferrater.sampleappwithxacmlpepspringsecurity",
		"com.github.joffryferrater.pep" //Scans the abac-spring-security configurations
	})
public class SampleAppWithXacmlPepSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleAppWithXacmlPepSpringSecurityApplication.class, args);
	}
}
````
<br>5. Create a global method security and web security configurations. 

##### An example of using Method Security expression: 
````java
import com.github.joffryferrater.pep.security.AbacMethodSecurityExpressionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new AbacMethodSecurityExpressionHandler();
    }
}
````
Here we created a GlobalMethodSecurityConfiguration where we use ``AbacMethodSecurityExpressionHandler()`` which is provided by this project in order to use the expression ``#abac.evaluate`` and ``#abac.evaluateAttributes`` in ``@PreAuthorize`` annotation.<br>

Annotate the resource to be protected by ``@PreAuthorize(#abac.evaluate({<array of attributes}))`` or ``@PreAuthorize(#abac.evaluateAttributes(<string formatted attributes separated by :>))``. Example below:
````java
import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleResource {
    private static final String HELLOWORLD_ACCESS = "#abac.evaluate("
        + "{#abac.resourceAttribute('Attributes.resource.endpoint', {'helloWorld'}), "
        + "#abac.accessSubjectAttribute('urn:oasis:names:tc:xacml:1.0:subject:subject-id', {#principal.name})})";
    private static final String HELLOWORLD_ID_ACCESS = "#abac.evaluateAttributes({'resource:Attributes.resource.endpoint:helloWorld/'+#id})";
    
    @GetMapping("/helloWorld")
    @PreAuthorize(HELLOWORLD_ACCESS)
    public String printHelloWorld(Principal principal) {
        return "hello world";
    }

    @GetMapping("/helloWorld/{id}")
    @PreAuthorize(HELLOWORLD_ID_ACCESS)
    public String getHelloWorldId(@PathVariable String id) {
        return "hello world id is: " + id;
    }
    
    /**
     *
     * Secured by Web Security expression, see @link{#WebSecurityConfig}
     */
    @GetMapping("/securedPath")
    public String getSecuredPath() {
        return "This is the /securedPath ";
    }
}
````
In the example above, the ``/helloWorld`` resource is protected with ``@PreAuthorize`` annotation with the abac expression. The ``#abac.evaluate`` send the following authorization request to a PDP server. <br>
`````json
{
	"Request": {
		"AccessSubject": [{
			"Attribute": [{
				"AttributeId": "urn:oasis:names:tc:xacml:1.0:subject:subject-id",
				"Value": ["Alice"]
			}]
		}],
		"Resource": [{
			"Attribute": [{
				"AttributeId": "Attributes.resource.endpoint",
				"Value": ["helloWorld"]
			}]
		}]
	}
}
`````
where the value Alice is the current user name and the value helloWorld is the protected resource. <br>

##### An example of using Web Security expression:
````java
import com.github.joffryferrater.pep.security.AbacWebSecurityExpressionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SECURED_PATH_ACCESS = "#abac.evaluateAttributes('resource:Attributes.resource.endpoint:securedPath', 'action:Attributes.action-id:read')";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .formLogin()
            .and()
            .authorizeRequests()
            .expressionHandler(webSecurityExpressionHandler())
            .antMatchers(HttpMethod.GET, "/securedPath").access(SECURED_PATH_ACCESS)
            .anyRequest().authenticated();

    }

    @Bean
    public AbacWebSecurityExpressionHandler webSecurityExpressionHandler() {
        return new AbacWebSecurityExpressionHandler();
    }

}

````
Here we created a Web Security Configuration and we set the expression handler as ``AbacWebSecurityExpressionHandler`` which is also provided by this project in order to use ``#abac.evaluate`` and ``#abac.evaluateAttributes`` expressions in ``access()`` as access expressions for securing the resource /securedPath.

#### See sample project here: https://github.com/jferrater/sample-app-with-abac-spring-security
