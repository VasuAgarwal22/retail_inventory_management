package org.example.retail_inventory_managment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.HashSet;
import java.util.Set;
import java.time.Instant;


@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Size(min = 8, message = "password must be atLeast 8 length")
    private String password;

    @Column(nullable = false)
    private String phoneNo;

    @Column(nullable = false)
    private boolean isActive = true;

    private String profilePicture;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private Instant createdDate = Instant.now();

    @Column(nullable = false)
    @Builder.Default
    private Instant updatedDate = Instant.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
