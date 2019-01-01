# Attribute Based Access Control for Spring Security
The Attribute Based Access Control (ABAC) for Spring Security provides both method and web expressions to secure spring boot applications based on attributes evaluated against a policy from a Policy Decision Point (PDP) server.
The expressions are called ``#abac.evaluate(Category... categories)`` and  ``#abac.evaluateAttributes(String... attributes)`` which send authorization request based on Json Profile of XACML 3.0 Specification (http://docs.oasis-open.org/xacml/xacml-json-http/v1.0/xacml-json-http-v1.0.html)

When using ``#abac.evaluateAttributes(String... attributes)``, the array of Strings must follow the format below:
``access-subject:<attribute id>:<attribute values>``<br>
``resource:<attribute id>:<attribute values>``<br>
``action:<attribute id>:<attribute values>``<br>
``environment:<attribute id>:<attribute values>``<br>

When using ``#abac.evaluate(Category... categories)`` where the arguments is an array of Category objects, the following expressions may be used as arguments:
``#abac.accessSubjectAttribute(<attribute id>, {<list of values>})``<br>
``#abac.resourceAttribute(<attribute id>, {<list of values>})``<br>
``#abac.actionAttribute(<attribute id>, {<list of values>})``<br>
``#abac.environmentAttribute(<attribute id>, {<list of values>})``<br>

## How to use
1. Build and publish this project to maven local: ``$ ./gradlew clean build publishToMavenLocal``
2. Add the published artifacts from maven local to your spring boot project dependency. Example for gradle project:
   	``compile('com.github.joffryferrater:abac-pep-spring-security:0.5.1')``<br>
   	``compile('com.github.joffryferrater:xacml-resource-models:0.5.1')``
3. Create a global method security configuration class. Example below:
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
Here we use ``AbacMethodSecurityExpressionHandler()`` which is provided by this project in order to use the expression ``#abac.evaluate`` in ``@PreAuthorize`` annotation.<br>
4 . Annotate the resource to be protected by ``@PreAuthorize(#abac.evaluate({<array of attributes}))``. Example below:
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
where the value Alice is the current user name and the value helloWorld is the protected resource. <br>5. Add the PDP server information in the application.properties file using the properties below:
````properties
pdp.server.authorize-endpoint=http://localhost:8083/authorize
pdp.server.username=pdp-user
pdp.server.password=password
pdp.server.print-authorization-request=true
````
The ``pdp.server.print-authorization-request`` property is useful for debugging purposes. It prints the authorization request on the console.
##### See sample project here: https://github.com/jferrater/sample-app-with-abac-spring-security
