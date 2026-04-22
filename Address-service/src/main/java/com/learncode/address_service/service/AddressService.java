package com.learncode.address_service.service;

import com.learncode.address_service.model.dto.AddressDto;
import com.learncode.address_service.model.dto.AddressRequest;

import java.util.List;

public interface AddressService {

    List<AddressDto> saveAddress(AddressRequest addressRequest);
    List<AddressDto> updateAddress(AddressRequest addressRequest);
    void deleteAddress(Long id);
    AddressDto getAddressById(Long id);
    List<AddressDto> getAddressByEmpId(Long EmpId);
    List<AddressDto> getAllAddresses();


}
