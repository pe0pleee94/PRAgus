package com.agus.springboot.belajarspringboot.repository;

import com.agus.springboot.belajarspringboot.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,String> {
}
