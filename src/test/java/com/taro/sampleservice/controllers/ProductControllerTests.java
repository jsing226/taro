package com.taro.sampleservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taro.sampleservice.models.Product;
import com.taro.sampleservice.services.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product controller should")
class ProductControllerTests {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("get the product by id")
    void testGetProduct() throws Exception {
        Product mockProduct = new Product(1,"Product Name",1,10);
        doReturn(Optional.of(mockProduct)).when(productService).findById(1);

        mockMvc.perform(get("/product/{id}",1))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.ETAG,"\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION,"/product/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1) ))
                .andExpect(jsonPath("$.name", is("Product Name")))
                .andExpect(jsonPath("$.quantity",is(10)))
                .andExpect(jsonPath("$.version",is(1)));
    }

    @Test
    @DisplayName("get the product by id - Not Found")
    void testGetProductByIdNotFound() throws Exception {

        doReturn(Optional.empty()).when(productService).findById(1);

        mockMvc.perform(get("/product/{id}",1))
                // Validate that we get 404 not found
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("be able to create a product")
    void testCreateProduct() throws Exception {
        Product postProduct = new Product("Product Name",10);
        Product mockProduct = new Product(1,"Product Name",1,10);
        doReturn(mockProduct).when(productService).save(any());

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postProduct)))
                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the headers
                .andExpect(header().string(HttpHeaders.ETAG,"\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION,"/product/1"))
                // Validate the returned fields
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("Product Name")))
                .andExpect(jsonPath("$.quantity",is(10)))
                .andExpect(jsonPath("$.version",is(1)));
    }

    private static String asJsonString(final Object obj){
        try{
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }


}