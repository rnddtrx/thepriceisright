package be.dsystem.thepriceisright.config.security.userdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import be.dsystem.thepriceisright.model.UserEntity;
import be.dsystem.thepriceisright.services.UserService;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

  private static final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);
	
  @Autowired
  UserService userService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	 //log.info("loadUserByUsername "+username);

	  Optional<MyUserDetails> user = Optional.empty();

	  //Ici normalement je récupère le user selon le paramètre login
	  Optional<UserEntity> userEntity = Optional.of(userService.getUserEntityByUsername(username));

	  if(userEntity.isPresent()) {
		  //Je récupère le user
		  UserEntity ue = userEntity.get();
		  //Je crée une liste de roles vide
		  List<String> rolelist = new ArrayList<String>();
		  //Je récupère les roles du  et je les ajoute à la liste sous forme de string
		  ue.getRoleEntities().forEach(role -> rolelist.add(role.getRoleName().toUpperCase()));

		  //Ici je crée un user spring sur base de mon Student
		  user = Optional.of(new MyUserDetails(ue.getUserName(), ue.getPassword(),rolelist));
	  }

	  if (user.isEmpty()) {
		  throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
	  }

	  //LOG
	  log.info("My pass is "+user.get().getPassword());
	  
	  return user.get();
  }

}