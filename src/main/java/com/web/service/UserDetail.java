package com.web.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.model.Register;
import com.web.model.Register1;
import com.web.repo.Register1Repo;
import com.web.repo.RegisterRepo;

@Service

public class UserDetail implements UserDetailsService {
	@Autowired
	private RegisterRepo repo;
	@Autowired
	private Register1Repo repo1;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Register user = repo.findByEmail(email);
		Register1 user1 = repo1.findByEmail(email);

		if (user1 != null) {

			return new User(user1.getEmail(), user1.getPassword(), new ArrayList<>());
		}

		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}

		return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	public void loginCred(Register register) throws UsernameNotFoundException {
		System.out.println("ok");
		Register reg = repo.findByEmailAndPassword(register.getEmail(), register.getPassword());
		if (reg == null) {
			throw new UsernameNotFoundException("User not found with email: " + register.getEmail());
		}
	}

	public void loginAdminCred(Register1 register1) throws UsernameNotFoundException {
		System.out.println("ok");
		Register1 reg = repo1.findByEmailAndPassword(register1.getEmail(), register1.getPassword());
		if (reg == null) {
			throw new UsernameNotFoundException("User not found with email: " + register1.getEmail());
		}
	}

}
