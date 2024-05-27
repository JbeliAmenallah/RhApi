package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.ContactDTO;
import com.PFE.RH.Services.Keycloak.KeycloakUserService;
import com.PFE.RH.Services.Keycloak.RoleService;
import lombok.AllArgsConstructor;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class KeycloakUserApi {


    private final KeycloakUserService keycloakUserService;
    private final RoleService roleService;

   /* @PostMapping
    public ContactDTO
    createUser(@RequestBody ContactDTO userRegistrationRecord) {

        return keycloakUserService.createUser(userRegistrationRecord);
    }*/
    @PutMapping("/assign-role/user/{userId}")
    public ResponseEntity<?> assignRole(@PathVariable String userId, @RequestParam("roleName") String roleName){

        roleService.assignRole(userId, roleName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    public UserRepresentation getUser(Principal principal) {

        return keycloakUserService.getUserById(principal.getName());
    }
    @GetMapping("/{userId}")
    public UserRepresentation getUserById(@PathVariable String userId) {

        return keycloakUserService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        keycloakUserService.deleteUserById(userId);
    }


    @PutMapping("/{userId}/send-verify-email")
    public void sendVerificationEmail(@PathVariable String userId) {
        keycloakUserService.emailVerification(userId);
    }
    @PutMapping("/update-password")
    public void updatePassword(Principal principal) {
        keycloakUserService.updatePassword(principal.getName());
    }
}