package be.dsystem.thepriceisright.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDto {
    private String username;
    private String password;
}
