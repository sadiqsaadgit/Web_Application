package com.saad.sa.users;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saad.sa.users.UsersService.UsersRequest;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

	private UsersService usersService;

	private UsersRepo usersRepo;

	public UsersController(UsersService usersService, UsersRepo usersRepo) {
		this.usersService = usersService;
		this.usersRepo = usersRepo;

	}

	@PostMapping("/create")
	@Operation(summary = "create users", description = "This api will create a users record", tags = "users")
	public List<Users> createUsers(@RequestBody UsersRequest user) {
		try {
			List<Users> response = usersService.createUsers(user);
			LOG.info("UsersController.createUser({}) => {}", user, response);
			return response;
		} catch (Exception e) {
			LOG.error("UsersController.createUser({}) => {}", user, e);
			throw e;
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "get users", description = "This api will get a users data based on id", tags = "users")
	public Optional<Users> getUsers(@PathVariable Long id) {
		try {
			Optional<Users> response = usersRepo.findById(id);
			LOG.info("UsersController.createUser({}) => {}", id, response);
			return response;
		} catch (Exception e) {
			LOG.error("UsersController.createUser({}) => {}", id, e);
			throw e;
		}
	}

	@DeleteMapping("/{id}/delete")
	@Operation(summary = "delete Users", description = "This api will delete the users record based on the id", tags = "users")
	public int deleteUser(@PathVariable Long id) {
		try {
			int response = usersRepo.deleteUsers(id);
			LOG.info("UsersController.deleteUser({}) => {}", id, response);
			return response;
		} catch (Exception e) {
			LOG.error("UsersController.deleteUser({}) => {}", id, e);
			throw e;
		}

	}
}
