package com.PFE.RH.Services.Keycloak;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    private final KeycloakUserService keycloakUserService;

    @Override
    public void assignRole(String userId, String roleName) {

        UserResource userResource = keycloakUserService.getUserResource(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        System.out.println(representation);
        System.out.println("from assign Role"+userId);
//        userResource.roles().realmLevel().add(Collections.singleton(representation));
        userResource.roles().realmLevel().add(Collections.singletonList(representation));
    }

    @Override
    public List<RoleRepresentation> getRoles() {
        RolesResource rolesResource = getRolesResource();
        return rolesResource.list();
    }

    public RolesResource getRolesResource(){
        return  keycloak.realm(realm).roles();
    }

}

