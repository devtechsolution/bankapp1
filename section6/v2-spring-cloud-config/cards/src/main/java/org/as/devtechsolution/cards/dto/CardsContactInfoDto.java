package org.as.devtechsolution.cards.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author Aditya Srivastva on 09-06-2024
 */


@ConfigurationProperties(prefix = "cards")
public record CardsContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
}
