package com.shasha.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shasha.blog.domain.RoleEntity;
import com.shasha.blog.domain.UserEntity;
import com.shasha.blog.exception.DuplicateEmailException;
import com.shasha.blog.exception.IdNotFoundException;
import com.shasha.blog.payload.UserDTO;
import com.shasha.blog.repository.RoleRepo;
import com.shasha.blog.repository.UserRepo;
import com.shasha.blog.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public UserDTO addUser(UserDTO userDto, Integer roleId) {

		LOGGER.info("request to addUSER::" + userDto, roleId);
		Optional<UserEntity> userEmail = userRepo.findByEmail(userDto.getEmail());
		if (userEmail.isPresent()) {
			LOGGER.error("Duplicate Email"+userDto.getEmail());
			throw new DuplicateEmailException("Duplicate Email " + userDto.getEmail());
		}
		RoleEntity roles = roleRepo.findById(roleId).orElseThrow(() -> new IdNotFoundException("RoleId not found"));

		UserEntity user = UserDtoToUserEntity(userDto);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String pwd = encoder.encode(user.getPassword());
		user.setPwd(pwd);
		user.setRoles(java.util.Collections.singletonList(roles));
		UserEntity entity = userRepo.save(user);
		return EntityToDto(entity);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {
		LOGGER.info("request to updateUser::" + userDto, userId);

		Optional<UserEntity> optional = userRepo.findById(userId);
		if (optional.isPresent()) {
			UserEntity dtoToUserEntity = UserDtoToUserEntity(userDto);
			UserEntity entity = userRepo.save(dtoToUserEntity);
			return EntityToDto(entity);

		}

		throw new IdNotFoundException("Id not found Exception::" + userId);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		LOGGER.info("request to getUserById::" , userId);

		// without java 8
		/*
		 * Optional<UserEntity> optional = userRepo.findById(userId); if
		 * (optional.isPresent()) { UserEntity userEntity = optional.get(); return
		 * EntityToDto(userEntity); } throw new
		 * IdNotFoundException("Id not found Exception::" + userId);
		 */
		// java 8
		UserEntity entity = userRepo.findById(userId)
				.orElseThrow(() -> new IdNotFoundException("Id not found Exception::" + userId));
		return EntityToDto(entity);

	}

	@Override
	public List<UserDTO> getAllUsers() {
		LOGGER.info("request to getAllUsers::" );
		return userRepo.findAll().stream().map(entity -> EntityToDto(entity)).collect(Collectors.toList());

	}

	@Override
	public void deleteUser(Integer userId) {
		
		/*
		 * //without java 8 Optional<UserEntity> optional = userRepo.findById(userId);
		 * if (!optional.isPresent()) { throw new
		 * IdNotFoundException("Id not found Exception::" + userId);
		 * 
		 * } userRepo.deleteById(userId);
		 */
		// java 8
		userRepo.findById(userId).orElseThrow(() -> new IdNotFoundException("Id not found Exception::" + userId));
		userRepo.deleteById(userId);

	}

	public UserEntity UserDtoToUserEntity(UserDTO userDto) {
		UserEntity entity = mapper.map(userDto, UserEntity.class);

		/*
		 * UserEntity entity = new UserEntity(); entity.setUserId(userDto.getUserId());
		 * entity.setUsername(userDto.getUsername()); entity.setPwd(userDto.getPwd());
		 * entity.setEmail(userDto.getEmail()); entity.setAbout(userDto.getAbout());
		 */
		return entity;
	}

	public UserDTO EntityToDto(UserEntity entity) {
		UserDTO dto = mapper.map(entity, UserDTO.class);
		/*
		 * dto.setUserId(entity.getUserId()); dto.setUsername(entity.getUsername());
		 * dto.setPwd(entity.getPwd()); dto.setAbout(entity.getAbout());
		 * dto.setEmail(entity.getEmail());
		 */
		return dto;
	}
}
