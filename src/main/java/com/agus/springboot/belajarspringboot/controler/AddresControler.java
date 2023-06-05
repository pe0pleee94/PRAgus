package com.agus.springboot.belajarspringboot.controler;

import com.agus.springboot.belajarspringboot.entity.User;
import com.agus.springboot.belajarspringboot.model.AddressRequest;
import com.agus.springboot.belajarspringboot.model.AddressResponse;
import com.agus.springboot.belajarspringboot.model.CreateContactRequest;
import com.agus.springboot.belajarspringboot.model.WebResponse;
import com.agus.springboot.belajarspringboot.service.impl.AddressService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AddresControler {

    @Autowired
    private AddressService addressService;


    /*@PostMapping(name = "/api/contact/{contactId}/address/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<AddressResponse> create(User user,
                                               @RequestBody AddressRequest request,
                                               @PathVariable("contactId") String contactid){
        request.setContactId(contactid);
        AddressResponse addressResponse = addressService.create(user, request);
        log.info("Login Request: {}", new Gson().toJson(request).replace("\"local", "\""));
        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }*/

    @PostMapping(
            path = "/api/contacts/{contactId}/addresses",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> create(User user,
                                               @RequestBody AddressRequest request,
                                               @PathVariable("contactId") String contactId) {
        request.setContactId(contactId);
        AddressResponse addressResponse = addressService.create(user, request);
        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }
}
