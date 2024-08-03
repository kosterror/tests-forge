package ru.kosterror.testsforge.coreservice.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.entity.test.generated.DynamicBlockLink;
import ru.kosterror.testsforge.coreservice.exception.InternalException;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class DynamicBlockLinksConverter implements AttributeConverter<List<DynamicBlockLink>, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<DynamicBlockLink> attribute) {
        if (isEmpty(attribute)) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException exception) {
            throw new InternalException("Failed to convert attribute %s to string".formatted(attribute), exception);
        }
    }

    @Override
    public List<DynamicBlockLink> convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData)) {
            return List.of();
        }

        try {
            return objectMapper.readValue(
                    dbData,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, DynamicBlockLink.class)
            );
        } catch (JsonProcessingException exception) {
            throw new InternalException("Failed to convert dbData %s to list".formatted(dbData), exception);
        }

    }

}
