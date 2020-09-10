package com.example.o.service;



import com.example.o.model.Consumer;
import com.example.o.model.DConsumerStatus;
import com.example.o.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Component
public class ConsumerServiceImpl implements UserDetailsService {

    @Autowired
    private ConsumerRepository consumerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Consumer consumer = consumerRepository.getByUsername(username);
        if (consumer == null) {
            throw new UsernameNotFoundException("consumer not found");
        }

        if (consumer.getStatus() != DConsumerStatus.ACTIVE) {
            throw new LockedException("consumer is unverified");
        }

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = consumer.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(consumer.getUsername(), consumer.getPassword(), simpleGrantedAuthorities);
    }
}
