package mariana.thePotteryPlace.service.impl;

import jakarta.persistence.EntityNotFoundException;
import mariana.thePotteryPlace.dto.request.AddressDTO;
import mariana.thePotteryPlace.error.UnauthorizedAccessException;
import mariana.thePotteryPlace.model.Address;
import mariana.thePotteryPlace.model.User;
import mariana.thePotteryPlace.repository.AddressRepository;
import mariana.thePotteryPlace.service.AuthService;
import mariana.thePotteryPlace.service.IAddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Address updateAddress(Long addressId, AddressDTO addressDTO) {
        // Get the authenticated user
        User authenticatedUser = authService.getAuthenticatedUser();

        // Fetch the address from the database
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        // Check if the address belongs to the authenticated user
        if (!address.getUser().getId().equals(authenticatedUser.getId())) {
            throw new UnauthorizedAccessException("You are not allowed to update this address");
        }

        // Map the DTO fields to the existing address
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setComplement(addressDTO.getComplement());
        address.setNeighborhood(addressDTO.getNeighborhood());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setZip(addressDTO.getZip());

        // Save the updated address
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long addressId) {
        User authenticatedUser = authService.getAuthenticatedUser();

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        // Check if the authenticated user owns the address
        if (!address.getUser().getId().equals(authenticatedUser.getId())) {
            // Throw UnauthorizedAccessException if the user doesn't own the address
            throw new UnauthorizedAccessException("You are not allowed to delete this address");
        }

        // Proceed with deleting the address if the user owns it
        addressRepository.delete(address);
    }

    public List<Address> findAddressesByUser() {
        Long authenticatedUserId = authService.getAuthenticatedUser().getId();
        return addressRepository.findByUser_Id(authenticatedUserId);
    }

    public Page<Address> findAddressesByUser(Pageable pageable) {
        Long authenticatedUserId = authService.getAuthenticatedUser().getId();
        return addressRepository.findByUser_Id(authenticatedUserId, pageable);
    }


    @Override
    protected JpaRepository<Address, Long> getRepository() {
        return addressRepository;
    }
}
