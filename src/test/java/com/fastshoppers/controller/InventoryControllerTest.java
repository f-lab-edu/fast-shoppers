package com.fastshoppers.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fastshoppers.exception.logger.ExceptionLogger;
import com.fastshoppers.service.InventoryService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InventoryController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(ExceptionLogger.class)
public class InventoryControllerTest {

	@MockBean
	private InventoryService inventoryService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getInventory() throws Exception {
		// given
		String productUuid = "test-uuid";
		int productQuantity = 10;

		given(inventoryService.getInventory(productUuid)).willReturn(productQuantity);

		mockMvc.perform(get("/api/v1/inventory/" + productUuid))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(productQuantity));
	}
}
