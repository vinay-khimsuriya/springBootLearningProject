// package com.vinay.FirstProjectInSpring.services;

// import com.vinay.FirstProjectInSpring.dto.UserRegisterDTO;
// import com.vinay.FirstProjectInSpring.dto.UserResponseDTO;
// import com.vinay.FirstProjectInSpring.model.User;
// import com.vinay.FirstProjectInSpring.repository.UserRepository;
// import com.vinay.FirstProjectInSpring.security.JwtUtil;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class UserService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private JwtUtil jwtUtil;

//     private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//     public UserResponseDTO register(UserRegisterDTO userDto) {
//         User user = new User();
//         user.setName(userDto.getName());
//         user.setEmail(userDto.getEmail());
//         user.setPassword(passwordEncoder.encode(userDto.getPassword()));

//         User savedUser = userRepository.save(user);

//         return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
//     }

//     public String login(String email, String password) {
//         Optional<User> userOpt = userRepository.findByEmail(email);
//         if (userOpt.isPresent()) {
//             User user = userOpt.get();
//             if (passwordEncoder.matches(password, user.getPassword())) {
//                 return jwtUtil.generateToken(user.getEmail());
//             }
//         }
//         return null;
//     }

//     public List<UserResponseDTO> getAllUsers() {
//         return userRepository.findAll()
//                 .stream()
//                 .map(u -> new UserResponseDTO(u.getId(), u.getName(), u.getEmail()))
//                 .collect(Collectors.toList());
//     }
// }
