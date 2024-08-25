package lk.ijse.gdse.weavy_test.service.impl;

import lk.ijse.gdse.weavy_test.dao.UserRepo;
import lk.ijse.gdse.weavy_test.dto.UserDto;
import lk.ijse.gdse.weavy_test.entity.UserEntity;
import lk.ijse.gdse.weavy_test.reqAndResp.request.SignIn;
import lk.ijse.gdse.weavy_test.reqAndResp.request.SignUp;
import lk.ijse.gdse.weavy_test.reqAndResp.response.JwtAuthResponse;
import lk.ijse.gdse.weavy_test.service.AuthenticationService;
import lk.ijse.gdse.weavy_test.service.JWTService;
import lk.ijse.gdse.weavy_test.convert.DataConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceIMPL implements AuthenticationService {

    private final UserRepo userRepo;
    private final JWTService jwtService;
    private final DataConvert dataConvert;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        UserEntity userByEmail = userRepo.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Not Found User Mail"));
        String generatedToken = jwtService.generateToken(userByEmail);
        return JwtAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JwtAuthResponse signUp(SignUp signUp) {
        UserDto userDTO = UserDto.builder()
                .email(signUp.getEmail())
                .name(signUp.getName())
                .emp_Id(signUp.getEmp_Id())
                .role(signUp.getRole())  // Assuming Role is an enum or valid type in SignUp
                .password(passwordEncoder.encode(signUp.getPassword()))
                .build();

        UserEntity save = userRepo.save(dataConvert.userDtoConvertUserEntity(userDTO));
        String token = jwtService.generateToken(save);
        return JwtAuthResponse.builder().token(token).build();
    }

    @Override
    public JwtAuthResponse refreshToken(String accessToken) {
        String userName = jwtService.extractUserName(accessToken);
        UserEntity userEntity = userRepo.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String refreshToken = jwtService.generateToken(userEntity);
        return JwtAuthResponse.builder().token(refreshToken).build();
    }
}
