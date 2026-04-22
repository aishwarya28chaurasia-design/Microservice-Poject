package com.learncode.address_service.service.impl;

import com.learncode.address_service.client.EmployeeClient;
import com.learncode.address_service.exception.ResourceNotFoundException;
import com.learncode.address_service.model.dto.AddressDto;
import com.learncode.address_service.model.dto.AddressRequest;
import com.learncode.address_service.model.dto.AddressRequestDto;
import com.learncode.address_service.model.entity.Address;
import com.learncode.address_service.repository.AddressRepository;
import com.learncode.address_service.service.AddressService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService {

    Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final EmployeeClient employeeClient;

    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper, EmployeeClient employeeClient) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.employeeClient = employeeClient;
    }

    @Override
    public List<AddressDto> saveAddress(AddressRequest addressRequest) {
        employeeClient.getEmployeeById(addressRequest.getEmpId());
        List<Address> listToSave = this.saveOrUpdateAddress(addressRequest);
        List<Address> savedAddressList = addressRepository.saveAll(listToSave);
        return savedAddressList.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public List<AddressDto> updateAddress(AddressRequest addressRequest) {
        employeeClient.getEmployeeById(addressRequest.getEmpId());
        List<Address> addressList = addressRepository.findByEmpId(addressRequest.getEmpId());
        if(addressList.isEmpty()){
            logger.info("No Address found with this empId {} ", addressRequest.getEmpId());
            logger.info("Creating aw Address for this empId {}", addressRequest.getEmpId());
        }
        List<Address> listToUpdate = this.saveOrUpdateAddress(addressRequest);
        List<Long> nonNullIds = listToUpdate.stream().map(Address:: getId).filter(Objects::nonNull).toList();
        List<Long> existingIds = addressList.stream().map(Address::getId).toList();
        List<Long> listToDelete = existingIds.stream().filter( id -> !nonNullIds.contains(id)).toList();
        if(!listToDelete.isEmpty()){
            addressRepository.deleteAllById(listToDelete);
        }
        List<Address> updatedAddress = addressRepository.saveAll(listToUpdate);
        return updatedAddress.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();

    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + id));
        addressRepository.delete(address);

    }

    @Override
    public AddressDto getAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + id));
        return modelMapper.map(address,AddressDto.class);

    }
    public List<AddressDto> getAddressByEmpId(Long empId){
        List<Address> addressByEmpId = addressRepository.findByEmpId(empId);
        if(addressByEmpId.isEmpty()){
            throw new ResourceNotFoundException("No Address found with this empId " + empId);
        }
        return addressByEmpId.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        List<Address> addressList = addressRepository.findAll();
        if(addressList.isEmpty()){
            throw new ResourceNotFoundException("No Address found");
        }
        return addressList.stream().map(address -> modelMapper.map(address,AddressDto.class)).toList() ;
    }

    private List<Address> saveOrUpdateAddress(AddressRequest addressRequest){
        List<Address> addressList = new ArrayList<>();
        for(AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtoList()) {
            Address address = new Address();
            address.setId(addressRequestDto.getId() != null ? addressRequestDto.getId() : null);
            address.setStreet(addressRequestDto.getStreet());
            address.setCity(addressRequestDto.getCity());
            address.setCountry(addressRequestDto.getCountry());
            address.setPinCode(addressRequestDto.getPinCode());
            address.setAddressType(addressRequestDto.getAddressType());
            address.setEmpId(addressRequest.getEmpId());
            addressList.add(address);
        }
        return addressList;
    }
}

