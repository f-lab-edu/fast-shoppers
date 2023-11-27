package com.fastshoppers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastshoppers.common.StatusCode;
import com.fastshoppers.entity.Member;
import com.fastshoppers.exception.logger.ExceptionLogger;
import com.fastshoppers.model.MemberDto;
import com.fastshoppers.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.context.annotation.Import;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(ExceptionLogger.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    private MemberDto memberDto;

    @BeforeEach
    void setUp() {
        memberDto = new MemberDto("newuser@example.com", "Password1234");
    }


    @Test
    @WithMockUser
    void registerMember() throws Exception {
        // Given
        given(memberService.registerMember(any(MemberDto.class))).willReturn(new Member());

        // when then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDto))
                        .with(csrf()))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.statusCode").value(StatusCode.OK.getCode()));

        then(memberService).should().registerMember(any(MemberDto.class));
    }
}


