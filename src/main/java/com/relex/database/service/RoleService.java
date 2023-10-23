package com.relex.database.service;

import lombok.RequiredArgsConstructor;
import com.relex.models.Role;
import org.springframework.stereotype.Service;
import com.relex.database.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
