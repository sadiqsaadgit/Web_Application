package com.saad.sa.users;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;

@Service("users")
public class UsersService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UsersService.class);

	private UsersRepo usersRepo;

	public UsersService(UsersRepo usersRepo) {
		this.usersRepo = usersRepo;
	}

	public record UsersRequest(List<Users> users) {
	}

	@Transactional
	public List<Users> createUsers(UsersRequest usersRequest) {
		try {
			List<Users> toSaveUsers = new ArrayList<Users>();
			var allUsers = usersRepo.findAll();
			var allEmails = allUsers.stream().map(Users::getEmail).toList();
			for (Users u : usersRequest.users) {
				if (allEmails.contains(u.getEmail())) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate emails are not allowed");
				}
				Users user = new Users();
				user.setEmail(u.getEmail());
				user.setName(u.getName());
				user.setCreatedOn(Instant.now());
				user.setUpdatedOn(Instant.now());
				user.setEnabled(true);
				toSaveUsers.add(user);
			}
			var result = usersRepo.saveAll(toSaveUsers);
			LOG.debug("UserService.createUser({}) => {}", usersRequest, result);
			return result;
		} catch (Exception e) {
			LOG.error("UserService.createUser({}) => {}", usersRequest, e);
			throw e;
		}
	}


}
