package API.EventTom.controller.voucher;

import API.EventTom.DTO.response.VoucherResponseDTO;
import API.EventTom.DTO.request.BulkVoucherGenerationRequestDTO;
import API.EventTom.DTO.request.VoucherGenerationRequestDTO;
import API.EventTom.config.AuthenticatedUserId;
import API.EventTom.models.Voucher;
import API.EventTom.services.vouchers.interfaces.IVoucherClaimService;
import API.EventTom.services.vouchers.interfaces.IVoucherGenerationService;
import API.EventTom.services.vouchers.interfaces.IVoucherQueryService;
import API.EventTom.services.vouchers.interfaces.IVoucherUsageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
@Validated
public class VoucherController {
    private final IVoucherGenerationService voucherGenerationService;
    private final IVoucherClaimService voucherClaimService;
    private final IVoucherUsageService voucherUsageService;
    private final IVoucherQueryService voucherQueryService;

    @PostMapping
    @PreAuthorize("hasRole('EVENT_MANAGER')")
    public ResponseEntity<VoucherResponseDTO> generateVoucher(
            @Valid @RequestBody VoucherGenerationRequestDTO request) {
        Voucher voucher = voucherGenerationService.generateVoucher(
                request.getAmount(),
                request.getValidityDays(),
                request.getType()
        );
        return ResponseEntity.ok(VoucherResponseDTO.fromVoucher(voucher));
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('EVENT_MANAGER')")
    public ResponseEntity<List<VoucherResponseDTO>> generateBulkVouchers(
            @Valid @RequestBody BulkVoucherGenerationRequestDTO request) {
        List<Voucher> vouchers = voucherGenerationService.generateBulkVouchers(
                request.getAmount(),
                request.getValidityDays(),
                request.getType(),
                request.getCount()
        );
        return ResponseEntity.ok(
                vouchers.stream()
                        .map(VoucherResponseDTO::fromVoucher)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{code}/validate")
    public ResponseEntity<VoucherResponseDTO> validateVoucher(@PathVariable String code) {
        Voucher voucher = voucherUsageService.validateVoucher(code);
        return ResponseEntity.ok(VoucherResponseDTO.fromVoucher(voucher));
    }

    @PutMapping("/{code}/claim")
    public ResponseEntity<VoucherResponseDTO> claimVoucher(
            @PathVariable String code,
            @AuthenticatedUserId Long userId) {
        Voucher voucher = voucherClaimService.claimVoucher(code, userId);
        return ResponseEntity.ok(VoucherResponseDTO.fromVoucher(voucher));
    }

    @GetMapping("/my-vouchers")
    public ResponseEntity<List<VoucherResponseDTO>> getMyVouchers(@AuthenticatedUserId Long userId) {
        List<VoucherResponseDTO> vouchers = voucherQueryService.getVouchersByCustomerId(userId);
        return ResponseEntity.ok(vouchers);
    }
}