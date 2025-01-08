package API.EventTom.services.vouchers.interfaces;

import API.EventTom.DTO.VoucherDTO;
import API.EventTom.DTO.VoucherResponse;

import java.util.List;

public interface IVoucherQueryService {

    List<VoucherResponse> getVouchersByCustomerId(Long userId);
    VoucherDTO getVoucherById(long voucherId);

}
