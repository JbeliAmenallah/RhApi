package com.PFE.RH.Services.Keycloak;


import com.PFE.RH.DTO.ContactDTO;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;


public interface KeycloakUserService {
    ContactDTO createUserFromContactDTO(ContactDTO contactDTO);
    UserRepresentation getUserById(String userId);
    void deleteUserById(String userId);
    void emailVerification(String userId);
    UserResource getUserResource(String userId);
    void updatePassword(String userId);

}
