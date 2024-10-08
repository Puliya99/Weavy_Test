package lk.ijse.gdse.weavy_test.service;

import lk.ijse.hello_shoes_shop_backend.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String extractUserName(String token);
    String generateToken(UserEntity userEntity);
    boolean isTokenValid(String token,UserDetails userDetails);

}
