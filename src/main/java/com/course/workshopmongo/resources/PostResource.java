package com.course.workshopmongo.resources;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.workshopmongo.domain.Post;
import com.course.workshopmongo.resources.util.URL;
import com.course.workshopmongo.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

	@Autowired
	private PostService service;

	// find by id resource
	@GetMapping(value = "/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post obj = service.findById(id);

		return ResponseEntity.ok().body(obj);
	}

	// find title by JPA
	@GetMapping(value = "/titlesearchbyjpa")
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
		text = URL.decodeParam(text);
		List<Post> list = service.findByTitleByJPA(text);

		return ResponseEntity.ok().body(list);
	}

	// find title by Query
	@GetMapping(value = "/titlesearch")
	public ResponseEntity<List<Post>> findTitle(@RequestParam(value = "text", defaultValue = "") String text) {
		text = URL.decodeParam(text);
		List<Post> list = service.findByTitleByQuery(text);

		return ResponseEntity.ok().body(list);
	}

	// full search by Query
	@GetMapping(value = "/fullsearch")
	public ResponseEntity<List<Post>> fullSearch(@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate) {

		text = URL.decodeParam(text);
		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date());

		List<Post> list = service.fullSearch(text, min, max);

		return ResponseEntity.ok().body(list);
	}
}
