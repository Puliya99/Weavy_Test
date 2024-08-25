package lk.ijse.gdse.weavy_test.service.impl;

import lk.ijse.gdse.weavy_test.dao.UserRepo;
import lk.ijse.gdse.weavy_test.dto.UserDto;
import lk.ijse.gdse.weavy_test.entity.UserEntity;
import lk.ijse.gdse.weavy_test.exception.ResourceNotFoundException;
import lk.ijse.gdse.weavy_test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    private final UserRepo userRepo;
    private final ModelMapper modelMapper; // For mapping between entities and DTOs

    @Autowired
    public UserService(UserRepo userRepository, ModelMapper modelMapper) {
        this.userRepo = userRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<UserEntity> findByUid(String uid) {
        return userRepo.findByEmail(uid);
    }

    public UserDto getUserByUid(String uid, boolean includeTrashed) throws ResourceNotFoundException {
        UserEntity user = userRepo.findByEmail(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with uid: " + uid));
        if (user.isTrashed() && !includeTrashed) {
            throw new ResourceNotFoundException("User not found with uid: " + uid);
        }
        return convertToDTO(user);
    }

    public UserDto getAuthenticatedUser(String email) throws ResourceNotFoundException {
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
        return convertToDTO(user);
    }

    public UserDto updateUser(String uid, UserDto updateDTO) throws ResourceNotFoundException {
        UserEntity user = userRepo.findByEmail(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with uid: " + uid));

        // Update fields if they are present in updateDTO
        if (updateDTO.getUid() != null) user.setUid(updateDTO.getUid());
        if (updateDTO.getEmail() != null) user.setEmail(updateDTO.getEmail());
        if (updateDTO.getGivenName() != null) user.setGivenName(updateDTO.getGivenName());
        if (updateDTO.getMiddleName() != null) user.setMiddleName(updateDTO.getMiddleName());
        if (updateDTO.getName() != null) user.setName(updateDTO.getName());
        if (updateDTO.getFamilyName() != null) user.setFamilyName(updateDTO.getFamilyName());
        if (updateDTO.getNickname() != null) user.setNickname(updateDTO.getNickname());
        if (updateDTO.getPhoneNumber() != null) user.setPhoneNumber(updateDTO.getPhoneNumber());
        if (updateDTO.getComment() != null) user.setComment(updateDTO.getComment());
        if (updateDTO.getPicture() != null) user.setPicture(updateDTO.getPicture());
        if (updateDTO.getDirectory() != null) user.setDirectory(updateDTO.getDirectory());
        if (updateDTO.getMetadata() != null) user.setMetadata();
        if (updateDTO.getTags() != null) user.setTags(updateDTO.getTags());
        if (updateDTO.getIsSuspended() != null) user.setSuspended(updateDTO.getIsSuspended());
        if (updateDTO.getIsBot() != null) user.setBot(updateDTO.getIsBot());

        UserEntity updatedUser = userRepo.save(user);
        return convertToDTO(updatedUser);
    }

    public UserDto upsertUser(String uid, UserDto upsertDTO) {
        Optional<UserEntity> optionalUser = userRepo.findByUid(uid);
        UserEntity user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (upsertDTO.getUid() != null) user.setUid(upsertDTO.getUid());
            if (upsertDTO.getEmail() != null) user.setEmail(upsertDTO.getEmail());
            if (upsertDTO.getGivenName() != null) user.setGivenName(upsertDTO.getGivenName());
            if (upsertDTO.getMiddleName() != null) user.setMiddleName(upsertDTO.getMiddleName());
            if (upsertDTO.getName() != null) user.setName(upsertDTO.getName());
            if (upsertDTO.getFamilyName() != null) user.setFamilyName(upsertDTO.getFamilyName());
            if (upsertDTO.getNickname() != null) user.setNickname(upsertDTO.getNickname());
            if (upsertDTO.getPhoneNumber() != null) user.setPhoneNumber(upsertDTO.getPhoneNumber());
            if (upsertDTO.getComment() != null) user.setComment(upsertDTO.getComment());
            if (upsertDTO.getPicture() != null) user.setPicture(upsertDTO.getPicture());
            if (upsertDTO.getDirectory() != null) user.setDirectory(upsertDTO.getDirectory());
            if (upsertDTO.getMetadata() != null) user.setMetadata(/* Convert map to Metadata enum or object */);
            if (upsertDTO.getTags() != null) user.setTags(upsertDTO.getTags());
            if (upsertDTO.getIsSuspended() != null) user.setSuspended(upsertDTO.getIsSuspended());
            if (upsertDTO.getIsBot() != null) user.setBot(upsertDTO.getIsBot());
        } else {
            // Create new user
            user = modelMapper.map(upsertDTO, UserEntity.class);
            user.setUid(uid);
            user.setTrashed(false);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
        }

        UserEntity savedUser = userRepo.save(user);
        return convertToDTO(savedUser);
    }

    private UserDto convertToDTO(UserEntity user) {
        return modelMapper.map(user, UserDto.class);
    }

}
