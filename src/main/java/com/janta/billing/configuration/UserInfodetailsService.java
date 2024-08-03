package com.janta.billing.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.janta.billing.entity.EmployeeDetails;
import com.janta.billing.repository.EmployeeDeatilRepository;

@Component
public class UserInfodetailsService implements UserDetailsService{
	 	@Autowired
	    private EmployeeDeatilRepository repository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Optional<EmployeeDetails> userInfo = repository.findByEmail(username);
	        
	        return userInfo.map(UserInfoDetails::new)
	                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

	    }
}
