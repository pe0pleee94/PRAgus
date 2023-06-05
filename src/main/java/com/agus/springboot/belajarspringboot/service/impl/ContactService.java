package com.agus.springboot.belajarspringboot.service.impl;

import com.agus.springboot.belajarspringboot.entity.Contact;
import com.agus.springboot.belajarspringboot.entity.User;
import com.agus.springboot.belajarspringboot.model.ContactResponse;
import com.agus.springboot.belajarspringboot.model.CreateContactRequest;
import com.agus.springboot.belajarspringboot.model.SearchContactRequest;
import com.agus.springboot.belajarspringboot.model.UpdateContactRequest;
import com.agus.springboot.belajarspringboot.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class ContactService {
    @Autowired
    private ValidationService validationService;
    @Autowired
    ContactRepository contactRepository;

    @Transactional
    public ContactResponse create(User user, CreateContactRequest request) {
        validationService.validate(request);

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstname(request.getFirstname());
        contact.setLastname(request.getLastname());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setUser(user);

        contactRepository.save(contact);


        return toContactRepsonse(contact);

    }

    private ContactResponse toContactRepsonse(Contact contact){
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstname())
                .lastName(contact.getLastname())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }

    @Transactional(readOnly = true)
    public ContactResponse get(User user,String id){
        Contact contact = contactRepository.findFirstByUserAndId(user, id )
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact Not Found"));

        return toContactRepsonse(contact);
    }
    @Transactional
    public ContactResponse update(User user, UpdateContactRequest request){
        validationService.validate(request);
        Contact contact = contactRepository.findFirstByUserAndId(user, request.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact Not Found"));
        contact.setFirstname(request.getFirstname());
        contact.setLastname(request.getLastname());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());

        contactRepository.save(contact);

        return toContactRepsonse(contact);
}

@Transactional
public void delete(User user, String contacid){
    Contact contact = contactRepository.findFirstByUserAndId(user, contacid)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact Not Found"));
        contactRepository.delete(contact);
}
/*public Page<ContactResponse> search(User user, SearchContactRequest request){
    Specification<Contact>specification = ((root, query, builder) -> {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("user"), user));
        if(Objects.nonNull(request.getName())){
            predicates.add(builder.or(
                    builder.like(root.get("firstname"), "%" + request.getName()+"%"),
                    builder.like(root.get("lastname"), "%" + request.getName()+"%")
            ));

        }


        };
    }*/
}
