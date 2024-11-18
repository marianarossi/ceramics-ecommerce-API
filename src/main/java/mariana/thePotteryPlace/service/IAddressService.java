package mariana.thePotteryPlace.service;

import mariana.thePotteryPlace.dto.request.AddressDTO;
import mariana.thePotteryPlace.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAddressService extends ICrudService<Address, Long> {
    AddressDTO saveCompleteAddress(AddressDTO addressDTO);
    List<Address> findAddressesByUser();
    Page<Address> findAddressesByUser(Pageable pageable);
    Address updateAddress(Long addressId, AddressDTO addressDTO);
    void deleteAddress(Long addressId);
}
