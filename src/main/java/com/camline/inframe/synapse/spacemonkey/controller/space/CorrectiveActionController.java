package com.camline.inframe.synapse.spacemonkey.controller.space;

import com.camline.inframe.synapse.spacemonkey.api.space.SpaceApiDelegate;
import com.camline.inframe.synapse.spacemonkey.model.space.CaSelectedSamplesData;
import com.camline.inframe.synapse.spacemonkey.model.space.ServiceResponce;
import com.camline.inframe.synapse.spacemonkey.controller.config.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.RequestPath;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.OffsetDateTime;

/**
 * Controller handling corrective actions in the space API.
 * <p>
 *  Info: RequestMappings are for Unit Tests not necessarily for the API mappings
 * </p>
 */

@RestController
@RequestMapping("/space/")
public class CorrectiveActionController implements SpaceApiDelegate {


    private static final Logger log = LoggerFactory.getLogger(CorrectiveActionController.class);
    private final Properties properties;

    public CorrectiveActionController(Properties properties) {
        this.properties = properties;
    }

    @Override
    @GetMapping("/corrective-action")
    public Mono<ResponseEntity<ServiceResponce>> getCorrectiveAction( final ServerWebExchange exchange) {
        OffsetDateTime start = OffsetDateTime.now();

        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        assert remoteAddress   != null;

        InetAddress address = remoteAddress.getAddress();
        String hostName = remoteAddress.getHostName();
        String hostString = remoteAddress.getHostString();
        int port = remoteAddress.getPort();

        InetSocketAddress localAddress = exchange.getRequest().getLocalAddress();
        RequestPath path = exchange.getRequest().getPath();
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();

        log.atError().log( "Address: " + address);
        log.atError().log( "HostName: " + hostName);
        log.atError().log("HostString: " + hostString);
        log.atError().log("Port: " + port);
        log.atError().log("LocalAddress: " + localAddress);
        log.atError().log("Path: " + path);
        log.atError().log("QueryParams: " + queryParams);

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