package com.agus.springboot.belajarspringboot.service.impl;

import com.agus.springboot.belajarspringboot.entity.Address;
import com.agus.springboot.belajarspringboot.entity.Contact;
import com.agus.springboot.belajarspringboot.entity.User;
import com.agus.springboot.belajarspringboot.model.AddressRequest;
import com.agus.springboot.belajarspringboot.model.AddressResponse;
import com.agus.springboot.belajarspringboot.model.CreateContactRequest;
import com.agus.springboot.belajarspringboot.repository.AddressRepository;
import com.agus.springboot.belajarspringboot.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ValidationService validationService;


    @Transactional
    public AddressResponse create(User user, AddressRequest request){
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getContactId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact Not Found"));
        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setContact(contact);
        address.setStreet(request.getStreet());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

        addressRepository.save(address);

        return toAddressResponse(address);
    }
    private AddressResponse toAddressResponse(Address address){
        return AddressResponse.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .province(address.getProvince())
                .postalCode(address.getPostalCode())
                .build();
    }
}
