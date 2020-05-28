package com.mikaelfrancoeur.controllers;

import com.mikaelfrancoeur.api.v1.dto.VendorDTO;
import com.mikaelfrancoeur.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mikaelfrancoeur.RestTestUtils.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest {

    public static final int NO_OF_VENDORS = 14;
    public static final long VENDOR_ID = 90L;
    public static final String VENDOR_NAME = "vendor name";
    @Mock
    VendorService vendorService;
    MockMvc mockMvc;
    VendorController controller;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new VendorController(vendorService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(RestExceptionHandler.class).build();
    }

    @Test
    void getAllVendors() throws Exception {
        //given
        when(vendorService.getAllVendors())
                .thenReturn(Stream.generate(VendorDTO::new)
                        .limit(NO_OF_VENDORS).collect(Collectors.toList()));

        //when
        mockMvc.perform(get(VendorController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(NO_OF_VENDORS)));

        //then
        verify(vendorService).getAllVendors();
    }

    @Test
    void saveVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        when(vendorService.saveVendor(any())).thenReturn(vendorDTO);

        //when
        mockMvc.perform(post(VendorController.BASE_URL)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", anything()));

        //then
        verify(vendorService).saveVendor(vendorDTO);
    }

    @Test
    void deleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + VENDOR_ID))
                .andExpect(status().isOk());
        verify(vendorService).deleteVendor(VENDOR_ID);
    }

    @Test
    void getVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        when(vendorService.findById(any())).thenReturn(vendorDTO);

        //when
        mockMvc.perform(get(VendorController.BASE_URL + VENDOR_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", anything()));

        //then
        verify(vendorService).findById(VENDOR_ID);
    }

    @Test
    void patchVendor() throws Exception {
        //given
        final VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);
        when(vendorService.patchVendor(anyLong(), any())).thenReturn(vendorDTO);

        //when
        mockMvc.perform(patch(VendorController.BASE_URL + VENDOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(VENDOR_NAME)));

        //then
        verify(vendorService).patchVendor(VENDOR_ID, vendorDTO);
    }

    @Test
    void putVendor() throws Exception {
        //given
        final VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);
        when(vendorService.saveVendor(any())).thenReturn(vendorDTO);

        //when
        mockMvc.perform(put(VendorController.BASE_URL + VENDOR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(VENDOR_NAME)));

        //then
        verify(vendorService, never()).findById(VENDOR_ID);
        verify(vendorService).saveVendor(MockitoHamcrest.argThat(hasProperty("id", equalTo(VENDOR_ID))));
    }
}
