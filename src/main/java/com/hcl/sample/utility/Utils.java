package com.hcl.sample.utility;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public String map2Json(Object object) throws  Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return  objectMapper.writeValueAsString(object);

    }
}
