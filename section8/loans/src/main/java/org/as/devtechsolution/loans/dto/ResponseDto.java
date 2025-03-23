package org.as.devtechsolution.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Aditya Srivastva on 23-03-2024
 */
@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
@Data
@AllArgsConstructor
public class ResponseDto {

    @Schema(
            description = "Status code in the response"
    )
    private String statusCode;

    @Schema(
            description = "Status message in the response"
    )
    private String statusMsg;

}