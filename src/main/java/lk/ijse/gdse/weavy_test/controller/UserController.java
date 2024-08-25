package lk.ijse.gdse.weavy_test.controller;

import lk.ijse.gdse.weavy_test.dto.UserDto;
import lk.ijse.gdse.weavy_test.exception.ResourceNotFoundException;
import lk.ijse.gdse.weavy_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user/")
@CrossOrigin(origins = "http://localhost:63342", methods = {RequestMethod.PATCH, RequestMethod.DELETE ,RequestMethod.POST,RequestMethod.PUT,RequestMethod.GET})
public class UserController {
    @RestController
    @RequestMapping("/api")
    public class UserController {

        private final UserService userService;

        @Autowired
        public UserController(UserService userService) { this.userService = userService; }

        /**
         * Get User by UID or ID
         */
        @GetMapping("/api/users/{user}")
        public ResponseEntity<UserDto> getUser(
                @PathVariable("user") String userIdentifier,
                @RequestParam(value = "trashed", required = false, defaultValue = "false") boolean trashed
        ) throws ResourceNotFoundException {
            // Determine if userIdentifier is UID or ID
            // For simplicity, assume UID
            UserDto userDTO = userService.getUserByUid(userIdentifier, trashed);
            return ResponseEntity.ok(userDTO);
        }

        /**
         * Get Authenticated User
         */
        @GetMapping("/user")
        public ResponseEntity<UserDto> getAuthenticatedUser(Authentication authentication) throws ResourceNotFoundException {
            String email = authentication.getName();
            UserDto userDTO = userService.getAuthenticatedUser(email);
            return ResponseEntity.ok(userDTO);
        }

        /**
         * Update User
         */
        @PatchMapping("/users/{user}")
        public ResponseEntity<UserDto> updateUser(
                @PathVariable("user") String userIdentifier,
                @RequestBody UserDto updateDTO
        ) throws ResourceNotFoundException {
            UserDto updatedUser = userService.updateUser(userIdentifier, updateDTO);
            return ResponseEntity.ok(updatedUser);
        }

        /**
         * Upsert User
         */
        @PutMapping("/users/{uid}")
        public ResponseEntity<UserDto> upsertUser(
                @PathVariable("uid") String uid,
                @RequestBody UserDto upsertDTO
        ) {
            UserDto userDTO = userService.upsertUser(uid, upsertDTO);
            // Determine if created or updated
            // For simplicity, always return 200 OK
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }

        // Implement other endpoints (List Users, Issue Access Token, Revoke Access Token, Trash User, Restore User)


    }
