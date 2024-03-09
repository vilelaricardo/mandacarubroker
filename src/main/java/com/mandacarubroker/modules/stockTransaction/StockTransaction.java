package com.mandacarubroker.modules.stockTransaction;

import com.mandacarubroker.modules.stock.Stock;
import com.mandacarubroker.modules.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "stock_transaction")
@Table(name = "stock_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "transaction_id")
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transaction_id;

    // ----------- Variável estrangeira "stockId"
    @ManyToOne // Uma mesma ação para várias transações
    @JoinColumn(name = "stockId", insertable = false, updatable = false)
    private Stock stock;

    @Column(name = "stockId", nullable = false)
    @NotBlank(message = "O campo 'stockId' não pode ser vazio")
    private String stockId;


    // ----------- Variável estrangeira "userId"
    @ManyToOne // Um mesmo usuário para várias transações
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @Column(name = "userId", nullable = false)
    @NotBlank(message = "O campo 'userId' não pode ser vazio")
    private String userId;

    // -----------
    @Column(nullable = false)
    @NotNull(message = "A quantidade de ações na transação não pode ser nula")
    private int quantity;
}