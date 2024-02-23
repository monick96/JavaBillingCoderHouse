package org.coderhouse.billing.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Getter //only getter for id- field-level annotation takes priority
    private Integer id;

    @NonNull
    private Long price;

    @NonNull
    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne //many-to-one relationship with Product
    @JoinColumn(name ="product_id")
    @NonNull
    private Product product;
}

