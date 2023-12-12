package com.fastshoppers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshoppers.common.ResponseMessage;
import com.fastshoppers.service.InventoryService;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

	private final InventoryService inventoryService;

	@Autowired
	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@GetMapping("/{productUuid}")
	public ResponseMessage getInventory(@PathVariable String productUuid) {
		int productQuantity = inventoryService.getInventory(productUuid);
		return ResponseMessage.ok(productQuantity);
	}
}
