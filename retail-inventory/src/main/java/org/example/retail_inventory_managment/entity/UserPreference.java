package org.example.retail_inventory_managment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_preferences", uniqueConstraints = @UniqueConstraint(name = "uk_user_preference_key", columnNames = {"user_id", "preference_key"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserPreference {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "preference_key", nullable = false, length = 100)
    private String preferenceKey;

    @Column(name = "preference_value", nullable = false, columnDefinition = "text")
    private String preferenceValue;
}
