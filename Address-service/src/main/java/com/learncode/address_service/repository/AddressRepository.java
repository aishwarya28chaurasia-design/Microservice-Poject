package com.learncode.address_service.repository;

import com.learncode.address_service.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findByEmpId(Long empId);
}
