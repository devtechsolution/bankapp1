package org.as.devtechsolution.message.functions;

import org.as.devtechsolution.message.dto.AccountMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * @author Aditya Srivastva
 */
@Configuration
public class MessageFunction {

    private static final Logger logger = LoggerFactory.getLogger(MessageFunction.class);

    @Bean
    public Function<AccountMsgDto, AccountMsgDto> email(){
        return accountMsgDto -> {
            logger.info("Sending email with the details {}", accountMsgDto.toString());
            return accountMsgDto;
        };
    }

    @Bean
    public Function<AccountMsgDto, Long> sms(){
        return accountMsgDto -> {
            logger.info("Sending sms with the details {}", accountMsgDto.toString());
            return accountMsgDto.accountNumber();
        };
    }


}
