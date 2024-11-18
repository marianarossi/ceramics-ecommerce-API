package mariana.thePotteryPlace.controller;

import jakarta.validation.Valid;
import mariana.thePotteryPlace.dto.request.AddressDTO;
import mariana.thePotteryPlace.dto.response.ResponseAddressDTO;
import mariana.thePotteryPlace.model.Address;
import mariana.thePotteryPlace.service.IAddressService;
import mariana.thePotteryPlace.service.ICrudService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<ResponseAddressDTO>> findAll(int page, int size, String order, Boolean asc) {
        if (order == null) {
            order = "id"; // sorts by id
        }
        if (asc == null) {
            asc = true; // Default to ascending
        }
        // Convert the order and asc values into a Pageable object
        Sort sort = Sort.by(asc ? Sort.Order.asc(order) : Sort.Order.desc(order));
        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch the paginated addresses from the service
        Page<Address> pagedAddresses = addressService.findAddressesByUser(pageable);

        // Map the paged addresses to the ResponseAddressDTO
        Page<ResponseAddressDTO> responseAddresses = pagedAddresses.map(address -> modelMapper.map(address, ResponseAddressDTO.class));

        // Return the paginated response
        return ResponseEntity.ok(responseAddresses);
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<ResponseAddressDTO> update(@PathVariable Long id, @RequestBody @Valid AddressDTO dto) {
        // Call the service to update the address
        Address updatedAddress = addressService.updateAddress(id, dto);

        // Map the updated address to the response DTO
        ResponseAddressDTO responseDTO = modelMapper.map(updatedAddress, ResponseAddressDTO.class);

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content if deletion is successful
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

