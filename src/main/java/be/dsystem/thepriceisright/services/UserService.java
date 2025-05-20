package be.dsystem.thepriceisright.services;

import be.dsystem.thepriceisright.dtos.UserEntityDto;
import be.dsystem.thepriceisright.mappers.UserEntityMapper;
import be.dsystem.thepriceisright.model.UserEntity;
import be.dsystem.thepriceisright.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //Add user without role
    public UserEntityDto addUser(UserEntityDto userDto) {


        //Convert dto to entity
        var userEntity = userEntityMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        //Save entity
        var savedUser = userRepository.save(userEntity);
        //Convert entity to dto
        return userEntityMapper.toDto(savedUser);
    }

    //Get user by username
    public UserEntityDto getUserByUsername(String userName) {
        var userEntity = userRepository.findByUserName(userName);
        return userEntity.map(entity -> userEntityMapper.toDto(entity)).orElse(null);
    }

    //Get user by username
    public UserEntity getUserEntityByUsername(String userName) {
        var userEntity = userRepository.findByUserName(userName);
        return userEntity.orElse(null);
    }

}
