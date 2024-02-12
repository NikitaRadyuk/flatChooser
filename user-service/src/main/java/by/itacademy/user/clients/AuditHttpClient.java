package by.itacademy.user.clients;

import by.itacademy.user.controller.utils.JwtTokenHandler;
import by.itacademy.user.core.dto.AuditDTO;
import by.itacademy.user.core.dto.UserDetailsDTO;
import by.itacademy.user.core.enums.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class AuditHttpClient {
    private final JwtTokenHandler jwtTokenHandler;
    private final ObjectMapper objectMapper;

    public AuditHttpClient(JwtTokenHandler jwtTokenHandler, ObjectMapper objectMapper) {
        this.jwtTokenHandler = jwtTokenHandler;
        this.objectMapper = objectMapper;
    }

    public AuditDTO sendRequestToCreateLog(AuditDTO auditDTO){
        try{
            String jwtToken = jwtTokenHandler.generateAccessToken(new UserDetailsDTO().setRole(UserRole.ADMIN));
            String body = objectMapper.writeValueAsString(auditDTO);
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8084/audit"))
                    .headers(
                            "Authorization", "Bearer " + jwtToken,
                            "Content-Type", APPLICATION_JSON_VALUE
                    )
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), AuditDTO.class);
        } catch (Exception exception) {
            throw new RuntimeException("Error while sending request to audit-service: " + exception);
        }
    }
}
