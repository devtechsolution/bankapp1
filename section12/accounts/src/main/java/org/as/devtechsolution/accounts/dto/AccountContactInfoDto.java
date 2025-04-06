package org.as.devtechsolution.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author Aditya Srivastva on 09-06-2024
 */



@ConfigurationProperties(prefix = "accounts")
@Getter
@Setter
public class AccountContactInfoDto {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;

}