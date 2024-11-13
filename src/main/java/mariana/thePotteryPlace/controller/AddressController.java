package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.dto.AddressDTO;
import mariana.thePotteryPlace.model.Address;
import mariana.thePotteryPlace.service.IAddressService;
import mariana.thePotteryPlace.service.ICrudService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("addresses")
public class AddressController extends CrudController<Address, AddressDTO, Long> //T, D, ID{
{
    private final IAddressService addressService;
    private final ModelMapper modelMapper;

    public AddressController(IAddressService addressService, ModelMapper modelMapper) {
        super(Address.class, AddressDTO.class);
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<AddressDTO> create(AddressDTO entity) {
        return ResponseEntity.ok(addressService.saveCompleteAddress(entity));
    }

    @Override
    protected ICrudService<Address, Long> getService() {
        return this.addressService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }
}

