package com.PFE.RH.Services.Keycloak;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface RoleService {

    void assignRole(String userId, String roleName);
    List<RoleRepresentation> getRoles();
}
