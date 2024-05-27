package com.PFE.RH.Services;

import com.PFE.RH.DTO.PrimeDTO;
import com.PFE.RH.Entities.Contact;
import com.PFE.RH.Entities.Prime;
import com.PFE.RH.Entities.TypePrime;
import com.PFE.RH.Mappers.PrimeMapper;
import com.PFE.RH.Repositories.ContactRepository;
import com.PFE.RH.Repositories.PrimeRepository;
import com.PFE.RH.Repositories.TypePrimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrimeService {

    @Autowired
    private PrimeRepository primeRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TypePrimeRepository typePrimeRepository;

    @Autowired
    private PrimeMapper primeMapper;

    public List<PrimeDTO> getAllPrimes() {
        List<Prime> primes = primeRepository.findAll();
        return primes.stream()
                .map(primeMapper::primeToPrimeDTO)
                .collect(Collectors.toList());
    }
    public List<PrimeDTO> addPrimeToEmployeesByCategoryGradeOrGroup(PrimeDTO primeDTO) {
        List<Long> eligibleContactIds = new ArrayList<>();
         Long categoryId=primeDTO.getCategory_id();
         Long groupId= primeDTO.getGroupe_id();
         Long gradeId= primeDTO.getGrade_id();
         System.out.println(primeDTO.getGroupe_id());
        System.out.println(groupId);
        System.out.println(primeDTO.getGrade_id());
                // Get all contacts
        List<Contact> contacts = contactRepository.findAll();

        // Check each contact for eligibility

        for (Contact contact : contacts) {
            boolean eligible = true;

            // Check if the contact matches the grade, group, or category
            if (gradeId != null && !contact.getGrade().getGrade_id().equals(gradeId)) {
                eligible = false;
            }
            if (groupId != null && !contact.getGroupe().getGroupe_id().equals(groupId)) {
                eligible = false;
            }
            if (categoryId != null && !contact.getCategory().getCategory_id().equals(categoryId)) {
                eligible = false;
            }

            // If the contact is eligible, add it to the list
            if (eligible) {
                eligibleContactIds.add(contact.getContactId());
            }
        }

        // Add the prime to the eligible contacts
        List<PrimeDTO> addedPrimes = new ArrayList<>();
        for (Long contactId : eligibleContactIds) {
            PrimeDTO primeToAdd = new PrimeDTO();
            primeToAdd.setYear(primeDTO.getYear());
            primeToAdd.setMonth(primeDTO.getMonth());
            primeToAdd.setMontant(primeDTO.getMontant());
            primeToAdd.setMotif(primeDTO.getMotif());
            primeToAdd.setTypePrimeId(primeDTO.getTypePrimeId());
            primeToAdd.setContactId(contactId);
            primeToAdd.setCategory_id(categoryId);
            primeToAdd.setGroupe_id(groupId);
            primeToAdd.setGrade_id(gradeId);
            addedPrimes.add(savePrime(primeToAdd));
        }
        return addedPrimes;
    }


    public List<Long> getContactIdsByPrimeId(Long primeId) {
        List<Long> contactIds = new ArrayList<>();

        Optional<Prime> optionalPrime = primeRepository.findById(primeId);
        if (optionalPrime.isPresent()) {
            Prime prime = optionalPrime.get();
            Contact contact = prime.getContact();
            if (contact != null) {
                contactIds.add(contact.getContactId());
            }
        }

        return contactIds;
    }

    public PrimeDTO savePrime(PrimeDTO primeDTO) {
        Prime prime = primeMapper.primeDTOToPrime(primeDTO);

        // Fetch the Contact
        Optional<Contact> contactOptional = contactRepository.findById(primeDTO.getContactId());
        if (contactOptional.isPresent()) {
            Contact contact = contactOptional.get();
            prime.setContact(contact);
            contact.getPrimes().add(prime);
        } else {
            throw new NoSuchElementException("Contact not found with ID: " + primeDTO.getContactId());
        }

        // Fetch the TypePrime if typePrimeId is provided
        if (primeDTO.getTypePrimeId() != null) {
            Optional<TypePrime> typePrimeOptional = typePrimeRepository.findById(primeDTO.getTypePrimeId());
            if (typePrimeOptional.isPresent()) {
                TypePrime typePrime = typePrimeOptional.get();
                prime.setTypePrime(typePrime);
            } else {
                throw new NoSuchElementException("TypePrime not found with ID: " + primeDTO.getTypePrimeId());
            }
        }

        Prime savedPrime = primeRepository.save(prime);
        return primeMapper.primeToPrimeDTO(savedPrime);
    }

    public PrimeDTO getPrimeById(Long id) {
        Optional<Prime> optionalPrime = primeRepository.findById(id);
        // Or throw an exception if needed
        return optionalPrime.map(prime -> primeMapper.primeToPrimeDTO(prime)).orElse(null);
    }

    public boolean deletePrime(Long id) {
        Optional<Prime> optionalPrime = primeRepository.findById(id);
        if (optionalPrime.isPresent()) {
            primeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PrimeDTO patchPrime(Long id, PrimeDTO patchedPrimeDTO) {
        Optional<Prime> optionalPrime = primeRepository.findById(id);
        if (optionalPrime.isPresent()) {
            Prime prime = optionalPrime.get();

            if (patchedPrimeDTO.getYear() != 0) {
                prime.setYear(patchedPrimeDTO.getYear());
            }
            if (patchedPrimeDTO.getMonth() != 0) {
                prime.setMonth(patchedPrimeDTO.getMonth());
            }
            if (patchedPrimeDTO.getMontant() != null) {
                prime.setMontant(patchedPrimeDTO.getMontant());
            }
            if (patchedPrimeDTO.getMotif() != null) {
                prime.setMotif(patchedPrimeDTO.getMotif());
            }
            if (patchedPrimeDTO.getCategory_id()!=null){
                prime.setCategory_id(patchedPrimeDTO.getCategory_id());
            }
            if (patchedPrimeDTO.getGrade_id()!=null){
                prime.setGrade_id(patchedPrimeDTO.getGrade_id());
            }
            if (patchedPrimeDTO.getGroupe_id()!=null){
                prime.setGroupe_id(patchedPrimeDTO.getGroupe_id());
            }
            if (patchedPrimeDTO.getTypePrimeId() != null) {
                Optional<TypePrime> typePrimeOptional = typePrimeRepository.findById(patchedPrimeDTO.getTypePrimeId());
                if (typePrimeOptional.isPresent()) {
                    prime.setTypePrime(typePrimeOptional.get());
                } else {
                    throw new NoSuchElementException("TypePrime not found with ID: " + patchedPrimeDTO.getTypePrimeId());
                }
            }

            Prime updatedPrime = primeRepository.save(prime);
            return primeMapper.primeToPrimeDTO(updatedPrime);
        } else {
            throw new NoSuchElementException("Prime not found with ID: " + id);
        }
    }

    public List<PrimeDTO> getPrimesByContactId(Long contactId) {
        List<Prime> primes = primeRepository.findByContactContactId(contactId);
        return primes.stream()
                .map(primeMapper::primeToPrimeDTO)
                .collect(Collectors.toList());
    }


}
