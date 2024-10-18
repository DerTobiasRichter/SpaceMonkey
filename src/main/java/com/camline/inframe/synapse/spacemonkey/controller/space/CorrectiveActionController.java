package com.camline.inframe.synapse.spacemonkey.controller.space;

import com.camline.inframe.synapse.spacemonkey.api.space.SpaceApiDelegate;
import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import com.camline.inframe.synapse.spacemonkey.model.space.CaSelectedSamplesData;
import com.camline.inframe.synapse.spacemonkey.model.space.ServiceResponce;
import com.camline.inframe.synapse.spacemonkey.controller.config.Properties;
import com.camline.inframe.synapse.spacemonkey.service.ConnectionServiceImpl;
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
import java.time.Duration;
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
    private final ConnectionServiceImpl connectionService;

    public CorrectiveActionController(Properties properties, ConnectionServiceImpl connectionService) {
        this.properties = properties;
        this.connectionService = connectionService;
    }

    @Override
    @GetMapping("/corrective-action")
    public Mono<ResponseEntity<ServiceResponce>> getCorrectiveAction( final ServerWebExchange exchange) {
        OffsetDateTime start = OffsetDateTime.now();

        saveInboundConnections(exchange, start);

        ServiceResponce response = new ServiceResponce();
        response.setMessage("I'm a Tea Pot.");
        response.setDurrationquantity(properties.getDurationQuantity());
        response.setDatetime(start);
        OffsetDateTime end = OffsetDateTime.now();
        response.setDurration(Duration.between(start.toInstant(),end.toInstant()).toNanos());

        return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
    }

    private void saveInboundConnections(ServerWebExchange exchange, OffsetDateTime now) {
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        assert remoteAddress   != null;

        InetAddress address = remoteAddress.getAddress();
        String hostName = remoteAddress.getHostName();
        int port = remoteAddress.getPort();

        InetSocketAddress localAddress = exchange.getRequest().getLocalAddress();
        RequestPath path = exchange.getRequest().getPath();
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();

        log.atDebug().log( "InboundTime: " + now);
        log.atDebug().log( "Address: " + address);
        log.atDebug().log( "HostName: " + hostName);
        log.atDebug().log("Port: " + port);
        log.atDebug().log("LocalAddress: " + localAddress);
        log.atDebug().log("Path: " + path);
        log.atDebug().log("QueryParams: " + queryParams);

        Connection connection = new Connection();
        connection.setInboundTime(now);
        connection.setAddress(address.toString());
        connection.setHostName(hostName);
        connection.setPort(port);
        assert localAddress != null;
        connection.setLocalAddress(localAddress.toString());
        connection.setPath(path.toString());
        connection.setQueryParams(queryParams);

        connectionService.setConnection(connection)
                .doOnSuccess(conn -> log.info("Connection saved: {}", conn))
                .subscribe();
    }

    @Override
    @PostMapping("/corrective-action")
    public Mono<ResponseEntity<ServiceResponce>> setCorrectiveAction(Mono<CaSelectedSamplesData> caSelectedSamplesData, ServerWebExchange exchange) {
        return Mono.empty();
    }

}