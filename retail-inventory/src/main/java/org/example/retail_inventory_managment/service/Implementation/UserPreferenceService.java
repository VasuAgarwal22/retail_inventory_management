package org.example.retail_inventory_managment.service.Implementation;

import lombok.RequiredArgsConstructor;
import org.example.retail_inventory_managment.dto.requestDTO.UserPreferenceRequest;
import org.example.retail_inventory_managment.dto.resposneDTO.UserPreferenceResponse;
import org.example.retail_inventory_managment.entity.UserPreference;
import org.example.retail_inventory_managment.repository.UserPreferenceRepository;
import org.example.retail_inventory_managment.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class UserPreferenceService {
    private final UserPreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserPreferenceResponse> list(String email) { Long userId = userRepository.findByEmail(email).orElseThrow().getId(); return preferenceRepository.findByUserIdOrderByPreferenceKeyAsc(userId).stream().map(this::toResponse).toList(); }
    @Transactional
    public UserPreferenceResponse save(UserPreferenceRequest request, String email) { var user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found")); var preference = preferenceRepository.findByUserIdAndPreferenceKey(user.getId(), request.getPreferenceKey()).orElseGet(() -> UserPreference.builder().user(user).preferenceKey(request.getPreferenceKey()).build()); preference.setPreferenceValue(request.getPreferenceValue()); return toResponse(preferenceRepository.save(preference)); }
    private UserPreferenceResponse toResponse(UserPreference p) { return UserPreferenceResponse.builder().id(p.getId()).preferenceKey(p.getPreferenceKey()).preferenceValue(p.getPreferenceValue()).build(); }
}
