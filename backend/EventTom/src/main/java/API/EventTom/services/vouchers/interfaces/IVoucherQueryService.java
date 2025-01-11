package API.EventTom.services.vouchers.interfaces;

import API.EventTom.dto.VoucherDTO;
import API.EventTom.dto.response.VoucherResponseDTO;
import API.EventTom.services.common.IBaseQueryService;

import java.util.List;

public interface IVoucherQueryService extends IBaseQueryService<VoucherDTO, Long> {

    List<VoucherResponseDTO> getVouchersByCustomerId(Long userId);

}
