package API.EventTom.services.users.interfaces;

import API.EventTom.DTO.CustomerDTO;

import java.util.List;

public interface ICustomerQueryService {

    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(String id);

}
