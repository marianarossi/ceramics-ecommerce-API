package mariana.thePotteryPlace.service;

import mariana.thePotteryPlace.dto.AddressDTO;
import mariana.thePotteryPlace.model.Address;

import java.util.List;

public interface IAddressService extends ICrudService<Address, Long>{
    AddressDTO saveCompleteAddress(AddressDTO addressDTO);
    List<Address> findAddressesByUser();
}
