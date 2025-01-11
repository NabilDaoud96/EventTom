package API.EventTom.services.users.interfaces;

import API.EventTom.dto.CustomerDTO;
import API.EventTom.dto.EmployeeDTO;
import API.EventTom.dto.response.VoucherDashboardDTO;
import API.EventTom.models.event.Voucher;
import API.EventTom.services.common.IBaseQueryService;

import java.util.List;

public interface ICustomerQueryService extends IBaseQueryService<CustomerDTO, Long> {
    CustomerDTO getCustomerByCustomerNumber(String customerNumber);
    VoucherDashboardDTO getCustomerVoucherDashboardByUserId(Long userId);
}
