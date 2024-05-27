package com.PFE.RH.Services;

import com.PFE.RH.DTO.TypePrimeDTO;
import com.PFE.RH.Entities.TypePrime;
import com.PFE.RH.Mappers.TypePrimeMapper;
import com.PFE.RH.Repositories.TypePrimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TypePrimeService {

    @Autowired
    private TypePrimeRepository typePrimeRepository;

    @Autowired
    private TypePrimeMapper typePrimeMapper;

    public List<TypePrimeDTO> getAllTypePrimes() {
        List<TypePrime> typePrimes = typePrimeRepository.findAll();
        return typePrimes.stream()
                .map(typePrimeMapper::typePrimeToTypePrimeDTO)
                .collect(Collectors.toList());
    }

    public TypePrimeDTO saveTypePrime(TypePrimeDTO typePrimeDTO) {
        TypePrime typePrime = typePrimeMapper.typePrimeDTOToTypePrime(typePrimeDTO);
        TypePrime savedTypePrime = typePrimeRepository.save(typePrime);
        return typePrimeMapper.typePrimeToTypePrimeDTO(savedTypePrime);
    }

    public TypePrimeDTO updateTypePrime(Long id, TypePrimeDTO updatedTypePrimeDTO) {
        TypePrime typePrime = typePrimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypePrime not found with ID: " + id));

        typePrime.setCode(updatedTypePrimeDTO.getCode());
        typePrime.setLibele(updatedTypePrimeDTO.getLibele());
        typePrime.setCnss(updatedTypePrimeDTO.isCnss());
        typePrime.setImpo(updatedTypePrimeDTO.isImpo());
        typePrime.setMontant(updatedTypePrimeDTO.getMontant());
        typePrime.setType(updatedTypePrimeDTO.getType());
        typePrime.setAbasedesalaire(updatedTypePrimeDTO.getAbasedesalaire());
        typePrime.setObligatoire(updatedTypePrimeDTO.getObligatoire());

        TypePrime updatedTypePrime = typePrimeRepository.save(typePrime);
        return typePrimeMapper.typePrimeToTypePrimeDTO(updatedTypePrime);
    }

    public TypePrimeDTO patchTypePrime(Long id, TypePrimeDTO patchedTypePrimeDTO) {
        TypePrime typePrime = typePrimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypePrime not found with ID: " + id));

        // Patch the fields if they are not null
        if (patchedTypePrimeDTO.getCode() != null) {
            typePrime.setCode(patchedTypePrimeDTO.getCode());
        }
        if (patchedTypePrimeDTO.getLibele() != null) {
            typePrime.setLibele(patchedTypePrimeDTO.getLibele());
        }
        if (patchedTypePrimeDTO.isCnss() != null) {
            typePrime.setCnss(patchedTypePrimeDTO.isCnss());
        }
        if (patchedTypePrimeDTO.isImpo() != null) {
            typePrime.setImpo(patchedTypePrimeDTO.isImpo()); // changed to isImpo()
        }
        if (patchedTypePrimeDTO.getMontant() != null) {
            typePrime.setMontant(patchedTypePrimeDTO.getMontant());
        }
        if (patchedTypePrimeDTO.getType() != null) {
            typePrime.setType(patchedTypePrimeDTO.getType());
        }
        if (patchedTypePrimeDTO.getAbasedesalaire() != null) {
            typePrime.setAbasedesalaire(patchedTypePrimeDTO.getAbasedesalaire());
        }

        if (patchedTypePrimeDTO.getObligatoire() != null) {
            typePrime.setObligatoire(patchedTypePrimeDTO.getObligatoire());
        }

        TypePrime patchedTypePrime = typePrimeRepository.save(typePrime);
        return typePrimeMapper.typePrimeToTypePrimeDTO(patchedTypePrime);
    }

    public TypePrimeDTO getTypePrimeByCode(String code) {
        TypePrime typePrime = typePrimeRepository.findByCode(code);
        if (typePrime != null) {
            return typePrimeMapper.typePrimeToTypePrimeDTO(typePrime);
        } else {
            throw new NoSuchElementException("TypePrime not found with code: " + code);
        }
    }

    public TypePrimeDTO getTypePrimeById(Long id) {
        TypePrime typePrime = typePrimeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TypePrime not found with ID: " + id));

        return typePrimeMapper.typePrimeToTypePrimeDTO(typePrime);
    }

    public TypePrimeDTO getTypePrimeByLibele(String libele) {
        TypePrime typePrime = typePrimeRepository.findByLibele(libele);
        if (typePrime != null) {
            return typePrimeMapper.typePrimeToTypePrimeDTO(typePrime);
        } else {
            throw new NoSuchElementException("TypePrime not found with libele: " + libele);
        }
    }
    public void deleteTypePrime(Long id) {
        TypePrime typePrime = typePrimeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TypePrime not found with ID: " + id));

        typePrimeRepository.delete(typePrime);
    }

}
