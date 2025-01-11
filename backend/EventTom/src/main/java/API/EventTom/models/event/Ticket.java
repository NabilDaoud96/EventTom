package API.EventTom.models.event;

import API.EventTom.models.user.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "purchase_date")
    @NotNull(message = "Purchase date is required")
    private LocalDateTime purchaseDate;

    @Column(name = "status_used", nullable = false)
    private boolean statusUsed = false;

    @Column(name = "final_price")
    @NotNull(message = "Final price is required")
    @Positive(message = "Final price must be positive")
    private BigDecimal finalPrice;

    @Column(name = "base_price")
    @NotNull(message = "Base price is required")
    @Positive(message = "Base price must be positive")
    private BigDecimal basePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @JsonBackReference
    private Event event;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher appliedVoucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

    @Transient
    public void calculateFinalPrice() {
        BigDecimal basePrice = event.getBasePrice();
        if (appliedVoucher != null && !appliedVoucher.isUsed()) {
            BigDecimal discountedPrice = basePrice.subtract(appliedVoucher.getAmount());
            this.finalPrice = discountedPrice.max(BigDecimal.ZERO);
            appliedVoucher.setUsed(true);
        } else {
            this.finalPrice = basePrice;
        }
    }
}
