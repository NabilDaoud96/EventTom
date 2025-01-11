package API.EventTom.config.initializers;

import API.EventTom.models.Voucher;
import API.EventTom.models.VoucherType;
import API.EventTom.services.vouchers.interfaces.IVoucherGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class VoucherInitializationConfig {

    private final IVoucherGenerationService voucherGenerationService;

    @Bean
    @Order(2) // Run after user initialization but before events
    public CommandLineRunner initializeVouchers() {
        return args -> {
            // Create different types of vouchers
            createPromotionalVouchers();
            createLoyaltyVouchers();
            createSpecialEventVouchers();
            createBulkDiscountVouchers();
        };
    }

    private void createPromotionalVouchers() {
        try {
            // Create 5 promotional vouchers worth $20 each, valid for 30 days
            List<Voucher> promoVouchers = voucherGenerationService.generateBulkVouchers(
                    new BigDecimal("20.00"),
                    30,
                    VoucherType.PROMOTIONAL,
                    5
            );
            System.out.println("Created " + promoVouchers.size() + " promotional vouchers");
        } catch (Exception e) {
            System.err.println("Failed to create promotional vouchers: " + e.getMessage());
        }
    }

    private void createLoyaltyVouchers() {
        try {
            // Create 3 loyalty vouchers worth $50 each, valid for 60 days
            List<Voucher> loyaltyVouchers = voucherGenerationService.generateBulkVouchers(
                    new BigDecimal("50.00"),
                    60,
                    VoucherType.LOYALTY,
                    3
            );
            System.out.println("Created " + loyaltyVouchers.size() + " loyalty vouchers");
        } catch (Exception e) {
            System.err.println("Failed to create loyalty vouchers: " + e.getMessage());
        }
    }

    private void createSpecialEventVouchers() {
        try {
            // Create 10 special event vouchers worth $15 each, valid for 14 days
            List<Voucher> specialEventVouchers = voucherGenerationService.generateBulkVouchers(
                    new BigDecimal("15.00"),
                    14,
                    VoucherType.PROMOTIONAL,
                    10
            );
            System.out.println("Created " + specialEventVouchers.size() + " special event vouchers");
        } catch (Exception e) {
            System.err.println("Failed to create special event vouchers: " + e.getMessage());
        }
    }

    private void createBulkDiscountVouchers() {
        try {
            // Create different tiers of bulk discount vouchers
            List<Voucher> smallBulkVouchers = voucherGenerationService.generateBulkVouchers(
                    new BigDecimal("25.00"),
                    45,
                    VoucherType.PROMOTIONAL,
                    3
            );

            List<Voucher> mediumBulkVouchers = voucherGenerationService.generateBulkVouchers(
                    new BigDecimal("75.00"),
                    45,
                    VoucherType.PROMOTIONAL,
                    2
            );

            List<Voucher> largeBulkVouchers = voucherGenerationService.generateBulkVouchers(
                    new BigDecimal("100.00"),
                    45,
                    VoucherType.PROMOTIONAL,
                    1
            );

            System.out.println("Created bulk discount vouchers of different tiers");
        } catch (Exception e) {
            System.err.println("Failed to create bulk discount vouchers: " + e.getMessage());
        }
    }
}