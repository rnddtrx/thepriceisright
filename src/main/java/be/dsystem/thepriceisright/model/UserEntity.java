package be.dsystem.thepriceisright.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user_entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    private String dateOfBirth;
    private String password;
    private String profilePictureUrl;

    @ManyToMany
    @JoinTable(name = "user_entity_roleEntities",
            joinColumns = @JoinColumn(name = "userEntity_user_id"),
            inverseJoinColumns = @JoinColumn(name = "roleEntities_role_id"))
    private Set<RoleEntity> roleEntities = new LinkedHashSet<>();

}
