package com.camline.inframe.synapse.spacemonkey.services.space;


import com.camline.inframe.synapse.spacemonkey.api.space.SpaceApiDelegate;
import com.camline.inframe.synapse.spacemonkey.model.space.CaSelectedSamplesData;
import com.camline.inframe.synapse.spacemonkey.model.space.ServiceResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
public class CorrectiveActionService implements SpaceApiDelegate {

    public CorrectiveActionService() {
    }

    @Override
    public Mono<ResponseEntity<Void>> getCorrectiveAction(ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.resolve(418));
        return result.then(Mono.empty());
    }

    @Override
    public Mono<ResponseEntity<ServiceResponce>> setCorrectiveAction(Mono<CaSelectedSamplesData> caSelectedSamplesData,
                                                                      ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        return result.then(caSelectedSamplesData).then(Mono.empty());
    }

}
