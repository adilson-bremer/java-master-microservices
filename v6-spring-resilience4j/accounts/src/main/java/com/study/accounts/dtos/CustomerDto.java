package com.study.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
    name = "Customer",
    description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

    @Schema(description = "Name of the customer", example = "Lorem Ipsum")
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 3, max = 30, message = "Name length must be between 3 and 30 characters")
    private String name;

    @Schema(description = "Email of the customer", example = "lorem.ipsum@lipsum.com")
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Mobile number of the customer", example = "11999999999")
    @Pattern(regexp = "^$|[0-9]{11}", message = "Mobile number must be 11 digits")
    private String mobileNumber;

    @Schema(description = "Account information for the customer")
    private AccountDto accountDto;
}