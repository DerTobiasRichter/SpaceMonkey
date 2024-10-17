package com.camline.inframe.synapse.spacemonkey.services.space;

import com.camline.inframe.synapse.spacemonkey.model.space.CaSelectedSamplesData;
import com.camline.inframe.synapse.spacemonkey.model.space.ServiceResponce;
import com.camline.inframe.synapse.spacemonkey.services.config.Properties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/fix/space")
public class CorrectiveActionController /**implements SpaceApiDelegate**/ {

    private final Properties properties;

    public CorrectiveActionController(Properties properties) {
        this.properties = properties;
    }

    //@Override
    @GetMapping("/corrective-action")
    public Mono<ResponseEntity<ServiceResponce>> getCorrectiveAction(HttpServletRequest request){
        OffsetDateTime start = OffsetDateTime.now();

        String clientIp = request.getRemoteAddr();

        ServiceResponce responce = new ServiceResponce();
        responce.setMessage("I'm a Tea Pot. " + clientIp);
        responce.setDurrationquantity(properties.getDurationQuantity());
        responce.setDatetime(start);
        OffsetDateTime end = OffsetDateTime.now();
        responce.setDurration(end.toEpochSecond() - start.toEpochSecond());

        return Mono.just(new ResponseEntity<>(responce,HttpStatus.valueOf(418))).log();
    }

    //@Override
    @PostMapping("/corrective-action")
    public Mono<String> setCorrectiveAction(Mono<CaSelectedSamplesData> caSelectedSamplesData) {

        return Mono.just("Hallo-CA").log();
    }

}

//@Component
//public class CorrectiveActionController implements SpaceApiDelegate {
//
//    @Override
//    public Mono<ResponseEntity<Void>> getCorrectiveAction(final ServerWebExchange exchange){
//
//        return Mono.empty();
//    }
//
//    @Override
//    public Mono<ResponseEntity<ServiceResponce>> setCorrectiveAction(Mono<CaSelectedSamplesData> caSelectedSamplesData,
//                                                                      ServerWebExchange exchange) {
//        Mono<Void> result = Mono.empty();
//        exchange.getResponse().setStatusCode(HttpStatus.OK);
//        return result.then(caSelectedSamplesData).then(Mono.empty());
//    }
//
//}