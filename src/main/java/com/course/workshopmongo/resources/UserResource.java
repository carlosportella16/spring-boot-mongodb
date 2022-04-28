package com.course.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.workshopmongo.domain.Post;
import com.course.workshopmongo.domain.User;
import com.course.workshopmongo.dto.UserDTO;
import com.course.workshopmongo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	// find all resource
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {

		List<User> list = service.findAll();

		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);

	}

	// find by id resource
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {

		User obj = service.findById(id);

		return ResponseEntity.ok().body(new UserDTO(obj));

	}

	// insert user with POST
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {

		User obj = service.fromDTO(objDto);
		obj = service.isert(obj);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	// Delete by ID
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

	// Update by ID
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody UserDTO objDto, @PathVariable String id) {
		User obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	// EndPoint to user's Post
	@GetMapping(value = "/{id}/posts")
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {

		User obj = service.findById(id);

		return ResponseEntity.ok().body(obj.getPosts());

	}
}
