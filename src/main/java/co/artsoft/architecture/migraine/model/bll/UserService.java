package co.artsoft.architecture.migraine.model.bll;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.artsoft.architecture.migraine.model.dao.UserRepository;
import co.artsoft.architecture.migraine.model.entity.UserType;

@Service("userService")
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String documentId) throws UsernameNotFoundException {
		co.artsoft.architecture.migraine.model.entity.User user = userRepository.findByDocumentId(documentId);
		List<GrantedAuthority> authorities = buildAuthorities(user.getUserType());
		return buildUser(user, authorities);
	}

	private User buildUser(co.artsoft.architecture.migraine.model.entity.User user, List<GrantedAuthority> authorities){
		return new User(user.getDocumentId(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildAuthorities(UserType userType){
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		auths.add(new SimpleGrantedAuthority(userType.getType()));
		return new ArrayList<GrantedAuthority>(auths);
	}
}
