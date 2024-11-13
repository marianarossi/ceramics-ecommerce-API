package mariana.thePotteryPlace.service;

import mariana.thePotteryPlace.dto.AddressDTO;
import mariana.thePotteryPlace.model.Address;

public interface IAddressService extends ICrudService<Address, Long>{
    AddressDTO saveCompleteAddress(AddressDTO addressDTO);
}
