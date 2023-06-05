package com.agus.springboot.belajarspringboot.controler;

import com.agus.springboot.belajarspringboot.entity.User;
import com.agus.springboot.belajarspringboot.model.*;
import com.agus.springboot.belajarspringboot.service.impl.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactControler {

    @Autowired
    private ContactService contactService;

    @PostMapping(path = "/api/contact",
    consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ContactResponse>create(User user, @RequestBody CreateContactRequest request){
        ContactResponse contactResponse = contactService.create(user,request);
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();

    }

    @GetMapping(path = "/api/contact/{contactId}")
    public WebResponse<ContactResponse>get (User user, @PathVariable("contactId") String contactid){
       ContactResponse contactResponse = contactService.get(user,contactid);
       return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }
    @PutMapping(path = "/api/contact/{contactId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ContactResponse>update(User user,
                                              @RequestBody UpdateContactRequest request,
                                              @PathVariable("contactId")String contactid){
        request.setId(contactid);
        ContactResponse contactResponse = contactService.update(user,request);
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }
    @DeleteMapping(path = "/api/contact/{contactId}")
    public WebResponse<String>delete (User user, @PathVariable("contactId") String contactid){
        contactService.delete(user,contactid);
        return WebResponse.<String>builder().data("ok").build();
    }
}
