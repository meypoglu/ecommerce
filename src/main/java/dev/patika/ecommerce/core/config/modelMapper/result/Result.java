package dev.patika.ecommerce.core.config.modelMapper.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private boolean status;
    private String message;
    private String httpCode;
}
