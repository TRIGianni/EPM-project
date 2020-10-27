package be.heh.epm.adapter.web;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.HashMap;

@SpringBootTest
public class EmployeeControllerTest {
    @Test
    public void createReturn201() throws URISyntaxException, IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/employees/salaried"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Paths.get("src/test/java/be/heh/epm/adapter/web/employeOK.json"))).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201,response.statusCode());


    }
    @Test
    public void invalidatingDataReturn400() throws URISyntaxException, IOException, InterruptedException, JSONException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/employees/salaried"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Paths.get("src/test/java/be/heh/epm/adapter/web/employeWrong.json"))).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        assertEquals(400,response.statusCode());

        JSONObject jsonObject = new JSONObject(response.body());

        JSONArray error = jsonObject.getJSONArray("errors");
        assertEquals("mail : doit être une adresse électronique syntaxiquement correcte",error.getString(0));

    }

}
