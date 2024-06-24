package com.example.ProgettoCap.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public boolean existsByRoleType(String roleType) {
        return roleRepository.existsById(roleType);
    }
}
