package mariana.thePotteryPlace.service.impl;

import mariana.thePotteryPlace.dto.AddressDTO;
import mariana.thePotteryPlace.model.Address;
import mariana.thePotteryPlace.repository.AddressRepository;
import mariana.thePotteryPlace.service.AuthService;
import mariana.thePotteryPlace.service.IAddressService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends CrudServiceImpl<Address, Long> implements IAddressService {
    private final AddressRepository addressRepository;
    private final AuthService authService;

    public AddressServiceImpl(AddressRepository addressRepository, AuthService authService) {
        this.addressRepository = addressRepository;
        this.authService = authService;
    }

    public AddressDTO saveCompleteAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setComplement(addressDTO.getComplement());
        address.setNeighborhood(addressDTO.getNeighborhood());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setZip(addressDTO.getZip());
        address.setUser(authService.getAuthenticatedUser());

        addressRepository.save(address);
        return addressDTO;
    }
    @Override
    protected JpaRepository<Address, Long> getRepository() {
        return addressRepository;
    }
}
