package com.fastshoppers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshoppers.common.ResponseMessage;
import com.fastshoppers.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@GetMapping("/{productUuid}")
	public ResponseMessage getInventory(@PathVariable String productUuid) {
		int productQuantity = inventoryService.getInventory(productUuid);
		return ResponseMessage.ok(productQuantity);
	}
}
