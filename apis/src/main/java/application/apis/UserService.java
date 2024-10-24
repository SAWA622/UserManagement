package application.apis;

import application.apis.database.UserRepository;
import application.apis.database.User;
import org.springframework.beans.factory.annotation.Autowired;
import application.apis.common.*;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
	public void hi() {
		System.out.println("hi");
	}
    @Autowired
    private UserRepository userRepository;
    public User createUser(User user) {
    	user.setPassword(PasswordUtil.encodePassword(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setMobileNo(userDetails.getMobileNo());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
    	 if (!userRepository.existsById(id)) {
             throw new RuntimeException("User not found");
         }
         userRepository.deleteById(id);
    }

    public User validateLogin(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && PasswordUtil.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
