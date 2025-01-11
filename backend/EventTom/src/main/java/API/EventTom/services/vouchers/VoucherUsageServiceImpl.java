package API.EventTom.services.vouchers;

import API.EventTom.models.event.Voucher;
import API.EventTom.repositories.VoucherRepository;
import API.EventTom.services.vouchers.interfaces.IVoucherUsageService;
import API.EventTom.services.vouchers.interfaces.IVoucherValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class VoucherUsageServiceImpl implements IVoucherUsageService {
    private final VoucherRepository voucherRepository;
    private final IVoucherValidationService validationService;

    @Override
    @Transactional
    public void useVoucherForPurchase(String code, Long customerId) {
        Voucher voucher = validationService.validateVoucherExists(code);
        validationService.validateVoucherNotExpired(voucher);
        validationService.validateVoucherNotUsed(voucher);
        validationService.validateVoucherOwnership(voucher, customerId);

        voucher.setUsed(true);
        voucherRepository.save(voucher);
    }

    @Override
    @Transactional
    public void markVouchersAsUsed(List<Voucher> vouchers, Long customerId) {
        if (vouchers == null || vouchers.isEmpty()) {
            return;
        }

        for (Voucher voucher : vouchers) {
            useVoucherForPurchase(voucher.getCode(), customerId);
        }
    }

    @Override
    public Voucher validateVoucher(String code) {
        Voucher voucher = validationService.validateVoucherExists(code);

        validationService.validateVoucherNotExpired(voucher);
        validationService.validateVoucherNotUsed(voucher);

        return voucher;
    }
    @Override
    public List<Voucher> validateVouchers(List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return new ArrayList<>();
        }

        return codes.stream()
                .map(this::validateVoucher)
                .collect(Collectors.toList());
    }
    @Override
    public BigDecimal calculateDiscountedAmount(BigDecimal originalAmount, Voucher voucher) {
        return originalAmount.subtract(voucher.getAmount()).max(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal calculateTotalDiscount(List<Voucher> vouchers) {
        if (vouchers == null || vouchers.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return vouchers.stream()
                .map(Voucher::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}