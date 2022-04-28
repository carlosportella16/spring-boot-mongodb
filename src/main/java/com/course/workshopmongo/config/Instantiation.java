package com.course.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.course.workshopmongo.domain.Post;
import com.course.workshopmongo.domain.User;
import com.course.workshopmongo.dto.AuthorDTO;
import com.course.workshopmongo.dto.CommentDTO;
import com.course.workshopmongo.repository.PostRepository;
import com.course.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		// Cleaning MongoDB
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		// Inserting new users
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		//Saving the users
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		//Inserting new Posts
		Post post1 = new Post(null, sdf.parse("21/03/2022"), "Let's travel", "I'm going to São Paulo!", new AuthorDTO (maria));
		Post post2 = new Post(null, sdf.parse("23/03/2022"), "Good Morning", "I woke up happy today!", new AuthorDTO (maria));
		
		//Making some comments in the Posts
		CommentDTO c1 = new CommentDTO("Bon voyage!", sdf.parse("21/03/2022"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Enjoy it!", sdf.parse("22/03/2022"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Have a great day!", sdf.parse("23/03/2022"), new AuthorDTO(alex));
		
		//Making the associations
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().add(c3);
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		userRepository.save(maria);
	}

}
