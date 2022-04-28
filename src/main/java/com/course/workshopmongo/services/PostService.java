package com.course.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.workshopmongo.domain.Post;
import com.course.workshopmongo.repository.PostRepository;
import com.course.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository repo;

	public Post findById(String id) {
		Optional<Post> user = repo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
	}

	// Finding by posts by text by JPA
	public List<Post> findByTitleByJPA(String text) {
		return repo.findByTitleContainingIgnoreCase(text);
	}

	// Finding by posts by text by query
	public List<Post> findByTitleByQuery(String text) {
		return repo.searchTitle(text);
	}

}
