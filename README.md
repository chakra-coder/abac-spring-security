# Attribute Based Access Control for Spring Security

The project, Attribute Based Access Control for Spring Security, provides a custom method security expression with Spring Security. The expression is called ``hasAccessToResource('<attribute id>', <{values}>)`` which can be used with ``@PreAuthorize`` and ``@PostAuthorize`` depending on your global method security configuration. The hasAccessToResource sends an XACML request in JSON to a PDP server (externalized authorization server, more info at https://www.axiomatics.com/). The JSON request is a Resource Category object representation as shown in the example below. See the "The Category object representation" in the XACML JSON Profile Specification: See http://docs.oasis-open.org/xacml/xacml-json-http/v1.0/cos01/xacml-json-http-v1.0-cos01.html#_Toc497727084

````
{
	"Request": {
		"Resource": {
			"Attribute": []
		}
	}
}
````

In order to add more Categories to the request, the following methods can be overriden:
* addAccessSubjectCategoryRequest
* addActionCategoryRequest
* addResourceCategoryRequest
* addEnvironmentCategoryRequest
* addCustomCategoryRequest (not yet implemented)

The following is an example of overriding the ``addAccessSubjectCategoryRequest``. The current username is added as AccessSubject category to the request.
```
...

@Autowired
private HttpServletRequest httpServletRequest;

@Override
protected AccessSubjectCategory addAccessSubjectCategory() {
    AccessSubjectCategory accessSubjectCategory = new AccessSubjectCategory();
    Attribute attribute = new Attribute();
    attribute.setAttributeId("<some access subject attribute id>");
    final String currentUser = httpServletRequest.getUserPrincipal().getName();
    attribute.setValue(Collections.singletonList(currentUser));
    accessSubjectCategory.withAttribute(attribute);
    return accessSubjectCategory;
}

...
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
