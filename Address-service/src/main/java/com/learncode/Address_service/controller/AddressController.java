package com.learncode.Address_service.controller;

import com.learncode.Address_service.model.dto.AddressDto;
import com.learncode.Address_service.model.dto.AddressRequest;
import com.learncode.Address_service.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@Tag(name = "Address Controller", description = "Management APIs for Address details")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Save addresses", description = "Saves new address information and returns the list of associated addresses")
    @PostMapping("/save")
    public ResponseEntity<List<AddressDto>> saveAddress(@RequestBody AddressRequest addressRequest){
        List<AddressDto> addressDtoList = addressService.saveAddress(addressRequest);
        return new ResponseEntity<>(addressDtoList, HttpStatus.CREATED);

    }

    @Operation(summary = "Update addresses", description = "Updates existing address details based on the request body")
    @PutMapping("/update")
    public ResponseEntity<List<AddressDto>> updateAddress(@RequestBody AddressRequest addressRequest){
        List<AddressDto> addressDtoList = addressService.updateAddress(addressRequest);
        return new ResponseEntity<>(addressDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Get address by ID", description = "Fetches a specific address record using its primary key ID")
    @GetMapping("/getById/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id){
        AddressDto addressDto = addressService.getAddressById(id);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @Operation(summary = "Get address by Employee ID", description = "Fetches a list of addresses associated with a specific employee ID")
    @GetMapping("/get/{empId}")
    public ResponseEntity<List<AddressDto>> getAddressByEmpId(@PathVariable Long empId){
        List<AddressDto> addressDtoList = addressService.getAddressByEmpId(empId);
        return new ResponseEntity<>(addressDtoList,HttpStatus.OK);
    }

    @Operation(summary = "Get all addresses", description = "Retrieves a complete list of all addresses stored in the system")
    @GetMapping("/getAll")
    public ResponseEntity<List<AddressDto>> getAllAddresses(){
        List<AddressDto> addressDtoList = addressService.getAllAddresses();
        return new ResponseEntity<>(addressDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Delete address", description = "Permanently removes an address record by its ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
        return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
    }
}
