package com.restapi.rest.webservices.restful_web_services.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	private MessageSource messageSource;
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@GetMapping(path = "/hello-world")
	public String helloworld() {
		return "Hello Guddu";
	}
	
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloworldBean() {
		return new HelloWorldBean("Hello Guddu");
	}
	
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloworldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello developer, %s", name));
	}
	@GetMapping(path = "/hello-world-internationalized")
	public String helloworldInternationalized() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
//		return "Hello Guddu V2";
	}

}