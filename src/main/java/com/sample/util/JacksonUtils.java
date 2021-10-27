package com.sample.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class JacksonUtils {

    private static final ObjectMapper objectMapper = commonObjectMapper();

    public static <T> JsonNode toJsonTree(T t) {
        return objectMapper.valueToTree(t);
    }

    public static Optional<JsonNode> nodeAt(JsonNode root, String path) {
        return Optional.of(root.at(path)).filter(node -> !node.isMissingNode());
    }

    public static Optional<String> stringAt(JsonNode root, String path) {
        return nodeAt(root, path).map(JsonNode::textValue);
    }

    public static Optional<Boolean> booleanAt(JsonNode root, String path) {
        return nodeAt(root, path).map(JsonNode::booleanValue);
    }

    public static Optional<JsonNode> nodeAt(Map<String, Object> root, String path) {
        return nodeAt(toJsonTree(root), path);
    }

    public static Optional<String> stringAt(Map<String, Object> root, String path) {
        return stringAt(toJsonTree(root), path);
    }

    public static Optional<Boolean> booleanAt(Map<String, Object> root, String path) {
        return booleanAt(toJsonTree(root), path);
    }

    public static <T> String toJson(T t) {
        return unchecked(() -> objectMapper.writeValueAsString(t));
    }

    public static <T> T deserialise(String json, Class<T> classType) {
        return unchecked(() -> objectMapper.readValue(json, classType));
    }

    public static <T> T deserialise(String json, TypeReference<T> typeReference) {
        return unchecked(() -> objectMapper.readValue(json, typeReference));
    }

    public static ObjectMapper commonObjectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .registerModule(new SimpleModule())
                .setSerializationInclusion(NON_NULL)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private static <T> T unchecked(Callable<T> statement) {
        try {
            return statement.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
