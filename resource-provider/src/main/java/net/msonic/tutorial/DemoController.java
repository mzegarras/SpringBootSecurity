package net.msonic.tutorial;

import java.security.Principal;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class DemoController {

	
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/")
	public String securedCall(Principal principal) {
		return "success (id: " + UUID.randomUUID().toString().toUpperCase() + principal.getName() + ")";
	}
	
	
	@RequestMapping(value = "/post",method = RequestMethod.POST)
	public String securedCallPost(Principal principal) {
		return "success (id: " + UUID.randomUUID().toString().toUpperCase() + principal.getName() + ")";
	}
}
