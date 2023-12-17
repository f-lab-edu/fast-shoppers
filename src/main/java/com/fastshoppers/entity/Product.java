package com.fastshoppers.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fastshoppers.util.BooleanToCharConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", nullable = false)
	private int id;

	@Column(name = "product_uuid", nullable = false, unique = true)
	private String productUuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "product_name", length = 255, nullable = false)
	private String productName;

	@Column(name = "product_subtitle", length = 255)
	private String productSubtitle;

	@Column(name = "list_price", nullable = false)
	private BigDecimal listPrice;

	@Column(name = "selling_price", nullable = false)
	private BigDecimal sellingPrice;

	@Column(name = "product_status", length = 255, nullable = false)
	private String productStatus;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "limit_quantity")
	private Integer limitQuantity;

	@Column(name = "remain_quantity", nullable = false)
	private Integer remainQuantity;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@Column(name = "delete_yn", nullable = false, length = 1, columnDefinition = "char(1)")
	@Convert(converter = BooleanToCharConverter.class)
	private boolean deleteYn;
}
