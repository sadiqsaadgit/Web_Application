package com.saad.sa.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface UsersRepo extends JpaRepository<Users, Long> {
	
	@Modifying
	@Transactional
	@Query("""
			DELETE FROM Users u WHERE u.enabled = true AND id = :id
			 """)
	int deleteUsers(Long id);

}
