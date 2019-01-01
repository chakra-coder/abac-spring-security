# Attribute Based Access Control for Spring Security
The Attribute Based Access Control (ABAC) for Spring Security provides both method and web expressions to secure spring boot applications based on attributes evaluated against a policy from a Policy Decision Point (PDP) server.
The expression is called ``#abac.evaluate(Category ... categories)`` which send an authorization request based on Json Profile of XACML 3.0 Specification (http://docs.oasis-open.org/xacml/xacml-json-http/v1.0/xacml-json-http-v1.0.html)
The argument is an array of Category objects.

## How to use
1. Build and publish this project to maven local: ``$ ./gradlew clean build publishToMavenLocal``
2. Add the published artifacts from maven local to your spring boot project dependency. Example for gradle project:
   	``compile('com.github.joffryferrater:abac-pep-spring-security:0.5.1')
   	compile('com.github.joffryferrater:xacml-resource-models:0.5.1')``
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
    private static final String HELLOWORLD_ID_ACCESS = "#abac.evaluate("
        + "{#abac.resourceAttribute('Attributes.resource.endpoint', {'helloWorld/'+#id})})";
    
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
where the value Alice is the current user name and the value helloWorld is the protected resource.

##### See sample project here: https://github.com/jferrater/sample-app-with-abac-spring-security
