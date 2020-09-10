package com.example.o.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "consumer")
public class Consumer  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String first_name;

    private String last_name;

    private String username;

    private String password;

    @Enumerated
    @Column(name = "status", nullable = false)
    private DConsumerStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "consumer_authority",
            joinColumns = @JoinColumn(name = "consumer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;


}
