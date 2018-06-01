# Attribute Based Access Control for Spring Security

The project, Attribute Based Access Control for Spring Security, provides a custom method security expression with Spring Security. The expression is called ``hasAccessToResource('<attribute id>', <{values}>)`` which can be used with ``@PreAuthorize`` and ``@PostAuthorize`` depending on your global method security configuration. The ``hasAccessToResource`` sends an XACML request in JSON to a PDP server (externalized authorization server, more info at https://www.axiomatics.com/). The JSON request is a Resource Category object representation as shown in the example below. See the "The Category object representation" in the XACML JSON Profile Specification: See http://docs.oasis-open.org/xacml/xacml-json-http/v1.0/cos01/xacml-json-http-v1.0-cos01.html#_Toc497727084

````
{
	"Request": {
		"Resource": {
			"Attribute": []
		}
	}
}
````
Sample usage of ``hasAccessToResource``: 
```
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleResource {

    @GetMapping("/helloWorld")
    @PreAuthorize("hasAccessToResource('Attributes.resource.endpoint', { 'helloWorld' })")
    public String printHelloWorld(){
        return "hello world";
    }

    @GetMapping("/helloWorld/{id}")
    @PreAuthorize("hasAccessToResource('Attributes.resource.endpoint', { 'helloWorld/'+#id })")
    public String getHelloWorldId(@PathVariable String id){
        return "hello world id is: " + id;
    }
}
```

In order to add more Categories to the request, the following methods can be overriden:
* addAccessSubjectCategoryRequest
* addActionCategoryRequest
* addResourceCategoryRequest
* addEnvironmentCategoryRequest
* addCustomCategoryRequest (not yet implemented)

The following is an example of overriding the ``addAccessSubjectCategoryRequest``. The current username is added as AccessSubject category to the request.
```
import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.pep.security.AbacMethodSecurityExpressionRoot;
import com.github.joffryferrater.request.AccessSubjectCategory;
import com.github.joffryferrater.request.Attribute;
import java.util.Collections;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class MyMethodSecurityExpressionRoot extends AbacMethodSecurityExpressionRoot {

    public MyMethodSecurityExpressionRoot(Authentication authentication,
        PdpClient pdpClient) {
        super(authentication, pdpClient);
    }

    @Override
    protected AccessSubjectCategory addAccessSubjectCategoryRequest() {
        // Sends the current user as access subject id attribute
        AccessSubjectCategory accessSubjectCategory = new AccessSubjectCategory();
        Attribute attribute = new Attribute();
        attribute.setAttributeId("urn:oasis:names:tc:xacml:1.0:subject:subject-id");
        final String currentUser =  SecurityContextHolder.getContext().getAuthentication().getName();
        attribute.setValue(Collections.singletonList(currentUser));
        accessSubjectCategory.withAttributes(attribute);
        return accessSubjectCategory;
    }
}

```
By overriding the ``addAccessSubjectCategory`` in the above example, the method security expression sends the XACML request in JSON to the PDP server in the following representation:
````
{
	"Request": {
		"Resource": {
			"Attribute": []
		},
                "AccessSubject": {
			"Attribute": []
		}
	}
}
````

## See sample project here: https://github.com/jferrater/sample-app-with-abac-spring-security 
