package API.EventTom.DTO.response;

import API.EventTom.models.Voucher;
import API.EventTom.models.VoucherType;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VoucherResponseDTO(
        String code,
        BigDecimal amount,
        LocalDateTime expirationDate,
        VoucherType type,
        Long id
) {
    public static VoucherResponseDTO fromVoucher(Voucher voucher) {
        return new VoucherResponseDTO(
                voucher.getCode(),
                voucher.getAmount(),
                voucher.getExpirationDate(),
                voucher.getType(),
                voucher.getId()
        );
    }
}