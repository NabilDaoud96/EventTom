package API.EventTom.services.vouchers.interfaces;

import API.EventTom.DTO.VoucherDTO;
import API.EventTom.DTO.response.VoucherResponseDTO;

import java.util.List;

public interface IVoucherQueryService {

    List<VoucherResponseDTO> getVouchersByCustomerId(Long userId);
    VoucherDTO getVoucherById(long voucherId);

}
