package pl.dashboard.nbp.service;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import pl.dashboard.nbp.dto.Currency;
import pl.dashboard.nbp.exceptions.InternalServerException;
import pl.dashboard.nbp.exceptions.NotFoundResourceException;

import java.io.IOException;

public class EntityToJsonServiceImpl implements EntityToJsonService {

    private final ConnectionService connectionService;

    public EntityToJsonServiceImpl(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }


    public Currency convert(String URL) throws IOException {
        CloseableHttpResponse response = connectionService.connect(URL);
        checkForErrors(response);
        HttpEntity entity = response.getEntity();
        String jsonString = EntityUtils.toString(entity);
        return mapJSONObjectToCurrencyObject(jsonString);
    }

    private void checkForErrors(CloseableHttpResponse response) {
        if (response == null) {
            throw new InternalServerException("Błąd podczas połączenia z serwerem");
        }
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 404) {
            throw new NotFoundResourceException("404 Not Found - Brak danych");
        }
    }

    private Currency mapJSONObjectToCurrencyObject(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Currency.class);
    }
}
