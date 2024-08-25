package lk.ijse.gdse.weavy_test.dao;

import lk.ijse.gdse.weavy_test.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String email);

}
