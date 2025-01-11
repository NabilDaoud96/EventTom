package API.EventTom.services.users;

import API.EventTom.dto.CustomerDTO;
import API.EventTom.exceptions.notFoundExceptions.CustomerNotFoundException;
import API.EventTom.exceptions.notFoundExceptions.ResourceNotFoundException;
import API.EventTom.mappers.StandardDTOMapper;
import API.EventTom.models.event.Ticket;
import API.EventTom.models.user.Customer;
import API.EventTom.repositories.CustomerRepository;
import API.EventTom.services.common.BaseQueryService;
import API.EventTom.services.users.interfaces.ICustomerQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerQueryServiceImpl extends BaseQueryService<Customer, CustomerDTO, Long>
        implements ICustomerQueryService {


    public CustomerQueryServiceImpl(
            CustomerRepository customerRepository,
            StandardDTOMapper standardDTOMapper, CustomerRepository customerRepository1) {
        super(customerRepository,
                standardDTOMapper,
                standardDTOMapper::mapCustomerToCustomerDTO,
                "Customer");
    }

    public CustomerDTO getCustomerByCustomerNumber(String customerNumber) {
        return ((CustomerRepository) repository).findCustomerByCustomerNumber(customerNumber)
                .map(mapperFunction)
                .orElseThrow(() -> new RuntimeException("Customer not found with Customer Number: " + customerNumber));
    }

    @Override
    public List<CustomerDTO> findAllByUserId(Long userId) {
        return List.of();
    }

    @Override
    public Page<CustomerDTO> findAllByUserId(Long userId, Pageable pageable) {
        return Page.empty(pageable);
    }
}