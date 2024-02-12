package by.itacademy.user.core.exceptions.body;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StructuredErrorResponse {

    private String logRef;

    private List<ErrorDetails> errors;
}
