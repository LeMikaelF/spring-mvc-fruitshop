package com.mikaelfrancoeur;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class RestTestUtils {
    @SneakyThrows
    public static String asJsonString(final Object obj) {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
