package com.camline.inframe.synapse.spacemonkey.services.space;

import com.camline.inframe.synapse.spacemonkey.api.space.SpaceApiDelegate;
import com.camline.inframe.synapse.spacemonkey.model.space.CaSelectedSamplesData;
import com.camline.inframe.synapse.spacemonkey.model.space.ServiceResponce;
import com.camline.inframe.synapse.spacemonkey.services.config.Properties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

/**
 * Controller handling corrective actions in the space API.
 *
 * Info: RequestMappings are for Unit Tests not necessarily for the API mappings
 */

@RestController
@RequestMapping("/space/")
public class CorrectiveActionController implements SpaceApiDelegate {

    private final Properties properties;

    public CorrectiveActionController(Properties properties) {
        this.properties = properties;
    }

    @Override
    @GetMapping("/corrective-action")
    public Mono<ResponseEntity<ServiceResponce>> getCorrectiveAction( final ServerWebExchange exchange) {
        OffsetDateTime start = OffsetDateTime.now();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);

        ServiceResponce response = new ServiceResponce();
        response.setMessage("I'm a Tea Pot.");
        response.setDurrationquantity(properties.getDurationQuantity());
        response.setDatetime(start);
        OffsetDateTime end = OffsetDateTime.now();
        response.setDurration(end.toEpochSecond() - start.toEpochSecond());

        return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Override
    @PostMapping("/corrective-action")
    public Mono<ResponseEntity<ServiceResponce>> setCorrectiveAction(Mono<CaSelectedSamplesData> caSelectedSamplesData, ServerWebExchange exchange) {

        return Mono.empty();
    }

}