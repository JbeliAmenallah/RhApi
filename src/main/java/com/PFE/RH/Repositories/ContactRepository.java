package com.PFE.RH.Repositories;

import com.PFE.RH.Entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}


