package com.learncode.Address_service.controller;

import com.learncode.Address_service.model.dto.AddressDto;
import com.learncode.Address_service.model.dto.AddressRequest;
import com.learncode.Address_service.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/save")
    public ResponseEntity<List<AddressDto>> saveAddress(@RequestBody AddressRequest addressRequest){
        List<AddressDto> addressDtoList = addressService.saveAddress(addressRequest);
        return new ResponseEntity<>(addressDtoList, HttpStatus.CREATED);

    }

    @PutMapping("/update")
    public ResponseEntity<List<AddressDto>> updateAddress(@RequestBody AddressRequest addressRequest){
        List<AddressDto> addressDtoList = addressService.updateAddress(addressRequest);
        return new ResponseEntity<>(addressDtoList, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id){
        AddressDto addressDto = addressService.getAddressById(id);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AddressDto>> getAllAddresses(){
        List<AddressDto> addressDtoList = addressService.getAllAddresses();
        return new ResponseEntity<>(addressDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
        return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
    }
}
