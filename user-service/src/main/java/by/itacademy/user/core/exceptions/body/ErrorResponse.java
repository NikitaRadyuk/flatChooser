package by.itacademy.user.core.exceptions.body;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorResponse {

    private String logRef;

    private String message;

}
