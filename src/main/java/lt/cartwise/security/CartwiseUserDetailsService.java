package lt.cartwise.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lt.cartwise.user.entities.User;
import lt.cartwise.user.repositories.UserRepository;

@Component
public class CartwiseUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	public CartwiseUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("not found"));
		
		return org.springframework.security.core.userdetails.User.builder()
				.username( user.getEmail() )
				.password( user.getPassword() )
				.authorities( "ROLE_" + user.getRole().name() )
				.disabled( !user.isActive() )
				.build();
	}

}
