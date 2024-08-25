package lk.ijse.gdse.weavy_test.convert;

import lk.ijse.gdse.weavy_test.dto.*;
import lk.ijse.gdse.weavy_test.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataConvert {

    private final ModelMapper modelMapper;

    public UserDto userEntityConvertUserDto(UserEntity userEntity){
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserEntity userDtoConvertUserEntity(UserDto userDto){
        return modelMapper.map(userDto, UserEntity.class);
    }

}
