package lk.ijse.gdse.weavy_test.service;

import lk.ijse.gdse.weavy_test.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void saveUser(UserDto userDTO);
    UserDetailsService userDetailsService();
}
