package com.course.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.course.workshopmongo.domain.Post;
import com.course.workshopmongo.domain.User;
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

		//Inserting new Posts
		Post post1 = new Post(null, sdf.parse("21/03/2022"), "Let's travel", "I'm going to São Paulo!", maria);
		Post post2 = new Post(null, sdf.parse("23/03/2022"), "Good Morning", "I woke up happy today!", maria);
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		postRepository.saveAll(Arrays.asList(post1, post2));
	}

}
