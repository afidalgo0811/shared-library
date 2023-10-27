package shared.library.order

import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table

@Table("orders")
data class Order(
    @Id val id: Long?,
    val bookIsbn: String,
    val bookName: String?,
    val bookPrice: Double?,
    val quantity: Int,
    val status: OrderStatus,
    @CreatedDate val createdDate: Instant?,
    @LastModifiedDate val lastModifiedDate: Instant?,
    @Version val version: Int,
) {
  companion object {
    fun of(
        bookIsbn: String,
        bookName: String?,
        bookPrice: Double?,
        quantity: Int,
        status: OrderStatus,
    ): Order {
      return Order(null, bookIsbn, bookName, bookPrice, quantity, status, null, null, 0)
    }

    fun of(
        id: Long?,
        bookIsbn: String,
        bookName: String?,
        bookPrice: Double?,
        quantity: Int,
        status: OrderStatus,
        createdDate: Instant?,
        lastModifiedDate: Instant?,
        version: Int,
    ): Order {
      return Order(
          id,
          bookIsbn,
          bookName,
          bookPrice,
          quantity,
          status,
          createdDate,
          lastModifiedDate,
          version)
    }
  }
}

enum class OrderStatus {
  ACCEPTED,
  REJECTED,
  DISPATCHED
}
