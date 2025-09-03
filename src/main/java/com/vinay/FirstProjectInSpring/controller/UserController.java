// package com.vinay.FirstProjectInSpring.controller;

// import com.vinay.FirstProjectInSpring.dto.*;
// import com.vinay.FirstProjectInSpring.services.UserService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/auth")
// public class UserController {

//     @Autowired
//     private UserService userService;

//     @PostMapping("/register")
//     public ResponseEntity<ApiResponse<UserResponseDTO>> register(@RequestBody UserRegisterDTO userDto) {
//         UserResponseDTO savedUser = userService.register(userDto);

//         ApiResponse<UserResponseDTO> response = new ApiResponse<>(
//                 "success",
//                 HttpStatus.CREATED.value(),
//                 "User registered successfully!",
//                 1,
//                 savedUser
//         );

//         return new ResponseEntity<>(response, HttpStatus.CREATED);
//     }

//     @PostMapping("/login")
//     public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequestDTO loginDto) {
//         String token = userService.login(loginDto.getEmail(), loginDto.getPassword());

//         if (token != null) {
//             ApiResponse<String> response = new ApiResponse<>(
//                     "success",
//                     HttpStatus.OK.value(),
//                     "Login successful!",
//                     1,
//                     token
//             );
//             return ResponseEntity.ok(response);
//         } else {
//             ApiResponse<String> response = new ApiResponse<>(
//                     "error",
//                     HttpStatus.UNAUTHORIZED.value(),
//                     "Invalid email or password!",
//                     0,
//                     null
//             );
//             return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//         }
//     }

//     @GetMapping("/getAllUser")
//     public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
//         List<UserResponseDTO> users = userService.getAllUsers();

//         ApiResponse<List<UserResponseDTO>> response = new ApiResponse<>(
//                 "success",
//                 HttpStatus.OK.value(),
//                 "Fetched all users successfully!",
//                 users.size(),
//                 users
//         );

//         return ResponseEntity.ok(response);
//     }
// }
