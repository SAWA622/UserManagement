package application.apis;

import application.apis.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Operations pertaining to user in User Management System")
public class UserController {
	@Autowired
	private UserService userService;

	@Operation(summary = "Create a new user")
//	@GetMapping("/csrf-token")
//	public CsrfToken getCsrfToken(HttpServletRequest request) {
//		return (CsrfToken) ( request.getAttribute(s:"_csrf");
//	}
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
		System.out.println("Creating user: " + user);
//        return ResponseEntity.ok(userService.createUser(user));
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
	}

	@Operation(summary = "Update an existing user")
	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
		return ResponseEntity.ok(userService.updateUser(id, userDetails));
	}

	@Operation(summary = "Delete a user")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Validate user login")
	@PostMapping("/login")
	public ResponseEntity<?> validateLogin(@RequestBody User user) {
		User validUser = userService.validateLogin(user.getUsername(), user.getPassword());
		if (validUser != null) {
			return ResponseEntity.ok(validUser);
		}
		return ResponseEntity.status(401).body("Unauthorized");
	}

	@Operation(summary = "Get all users")
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@Operation(summary = "Get user by username")
	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		User user = userService.findByUsername(username);
		if (user != null) {
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.status(404).body(null);
	}
	 @Operation(summary = "Delete a user by username")
	    @DeleteMapping("/delete/username/{username}")
	    public ResponseEntity<?> deleteUserByUsername(@PathVariable String username) {
	        User user = userService.findByUsername(username);
	        if (user != null) {
	            userService.deleteUser(user.getId());
	            return ResponseEntity.ok().build();
	        }
	        return ResponseEntity.status(404).body("User not found");
	    }

	    @Operation(summary = "Update a user by username")
	    @PutMapping("/update/username/{username}")
	    public ResponseEntity<User> updateUserByUsername(@PathVariable String username, @Valid @RequestBody User userDetails) {
	        User user = userService.findByUsername(username);
	        if (user != null) {
	            userDetails.setId(user.getId());
	            return ResponseEntity.ok(userService.updateUser(user.getId(), userDetails));
	        }
	        return ResponseEntity.status(404).body(null);
	    }

	@GetMapping("/hello")
	String hello() {
		return "Hello";
	}
}