package com.o4.open.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * JsonUtils is a utility class that help you convert a JSON String to Java Object
 * and vice versa, it also supports conversion of JSON array to List object
 *
 * @author M. Mazhar Hassan
 * @version 1.0
 * @since 25.08.2022
 *
 */
public interface JsonUtils {

    ObjectMapper mapper = new ObjectMapper();

    static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static <T> T toObject(String json, Class<T> clazz) {
        try {

            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static <T> List<T> toObjectList(String json, Class<T> clazz) {
        try {

            List<T> myObjects = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
            return myObjects;
        } catch (JsonMappingException ex) {
            throw new RuntimeException(ex);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
