package com.loizenai.jwtauthentication.controller;

import com.loizenai.jwtauthentication.helper.ReadExcelFile;
import com.loizenai.jwtauthentication.message.request.UserRequest;
import com.loizenai.jwtauthentication.model.User;
import com.loizenai.jwtauthentication.service.UserService;
import com.loizenai.jwtauthentication.utils.ConvertObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ReadExcelFile readExcelFile;

	@PostMapping("/api/manager/create")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(userService.add(userRequest));
	}

	@GetMapping("/api/manager/{userId}")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<User> getUserById(@PathVariable(value = "userId") long userId) {
		return ResponseEntity.ok(userService.getUser(userId));
	}

	@GetMapping("/api/manager/list-user")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<List<User>> getAllUser() {
		return ResponseEntity.ok(userService.list());
	}

	@GetMapping("/api/manager/get-user")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
		return ResponseEntity.ok(userService.getUserByUsername(username));
	}

	@PutMapping("/api/manager/update/{id}")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<String> updateUser(@PathVariable(value = "id") long userId, @Valid @RequestBody UserRequest userRequest) {
		userService.update(userId, userRequest);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/api/manager/delete")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<String> deleteUser(@PathVariable(value = "userId") long userId) {
		userService.delete(userId);
		return ResponseEntity.noContent().build();
	}


	@GetMapping("/api/users/import")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<?> importExcelFile(
			@RequestBody(required = true) MultipartFile file
	) throws IOException {
		List<User> users = readExcelFile.readFile(ConvertObject.convertMultipartToFile(file));

		return ResponseEntity.status(200).body(users);
	}


}