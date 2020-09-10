package com.example.o.repository;

import com.example.o.model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Integer> {

    Consumer getByUsername(String username);


}
