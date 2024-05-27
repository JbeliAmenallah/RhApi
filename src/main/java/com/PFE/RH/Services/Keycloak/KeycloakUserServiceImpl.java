package com.PFE.RH.Services.Keycloak;

import com.PFE.RH.DTO.ContactDTO;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class KeycloakUserServiceImpl implements KeycloakUserService {

    @Value("${keycloak.realm}")
    private String realm;
    private Keycloak keycloak;

    public KeycloakUserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public ContactDTO createUserFromContactDTO(ContactDTO contactDTO) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(contactDTO.getUsername());
        user.setEmail(contactDTO.getEmail());
        user.setFirstName(contactDTO.getName());
        user.setLastName(contactDTO.getName());
        user.setEmailVerified(false);

        System.out.println(contactDTO.getContactId());
        // Convert contactId to string and set it as a custom attribute
        user.singleAttribute("contactId", String.valueOf(contactDTO.getContactId()));

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(contactDTO.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> credentials = new ArrayList<>();
        credentials.add(credentialRepresentation);
        user.setCredentials(credentials);

        // Get UsersResource from Keycloak realm
        UsersResource usersResource = keycloak.realm(realm).users();

        // Create the user in Keycloak
        Response response = usersResource.create(user);

        // Check if user creation was successful (HTTP status code 201)
        if (Objects.equals(201, response.getStatus())) {
            log.info("User created successfully in Keycloak: {}", contactDTO.getUsername());
            return contactDTO;
        } else {
            log.error("Failed to create user in Keycloak: {}", contactDTO.getUsername());
            // Handle failure scenario
            return null;
        }
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        return  getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public void deleteUserById(String userId) {

        getUsersResource().delete(userId);
    }


    @Override
    public void emailVerification(String userId){
        UsersResource usersResource = getUsersResource();
//        usersResource.get(userId).sendVerifyEmail();
    }
    @Override
    public UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    @Override
    public void updatePassword(String userId) {

        UserResource userResource = getUserResource(userId);
        List<String> actions= new ArrayList<>();
        actions.add("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);

    }
}
