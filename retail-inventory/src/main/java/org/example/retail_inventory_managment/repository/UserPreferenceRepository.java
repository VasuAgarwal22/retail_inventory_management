package org.example.retail_inventory_managment.repository;

import org.example.retail_inventory_managment.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    List<UserPreference> findByUserIdOrderByPreferenceKeyAsc(Long userId);
    Optional<UserPreference> findByUserIdAndPreferenceKey(Long userId, String preferenceKey);
}
