package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.dto.AddressDTO;
import mariana.thePotteryPlace.dto.Response.ResponseAddressDTO;
import mariana.thePotteryPlace.model.Address;
import mariana.thePotteryPlace.service.IAddressService;
import mariana.thePotteryPlace.service.ICrudService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("addresses")
public class AddressController extends CrudController<Address, AddressDTO, ResponseAddressDTO, Long> //T, D, ID{
{
    private final IAddressService addressService;
    private final ModelMapper modelMapper;

    public AddressController(IAddressService addressService, ModelMapper modelMapper) {
        super(Address.class, AddressDTO.class, ResponseAddressDTO.class);
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<List<ResponseAddressDTO>> findAll() {
        List<Address> userAddresses = addressService.findAddressesByUser();
        List<ResponseAddressDTO> responseAddresses = userAddresses.stream()
                .map(address -> modelMapper.map(address, ResponseAddressDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseAddresses);
    }

    @Override
    public ResponseEntity<ResponseAddressDTO> create(AddressDTO entity) {
        addressService.saveCompleteAddress(entity);
        return ResponseEntity.ok(modelMapper.map(entity, ResponseAddressDTO.class));
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

