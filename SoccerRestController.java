package com.example;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/soccer")
public class SoccerRestController {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Soccer read(@PathVariable Long id) {

		return new Soccer(1L, "Soccer");
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public Soccer create(Soccer soccer) {

		return new Soccer(1L, "Soccer");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public Soccer update(Soccer soccer) {

		return new Soccer(1L, "US Soccer");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {

	}
}