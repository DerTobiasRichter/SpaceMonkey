package com.camline.inframe.synapse.spacemonkey.controller.space;

import com.camline.inframe.synapse.spacemonkey.api.space.SpaceApiDelegate;
import com.camline.inframe.synapse.spacemonkey.controller.config.util.ServiceResponseMessages;
import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import com.camline.inframe.synapse.spacemonkey.model.space.CaSelectedSamplesData;
import com.camline.inframe.synapse.spacemonkey.model.space.ServiceResponce;
import com.camline.inframe.synapse.spacemonkey.controller.config.Properties;
import com.camline.inframe.synapse.spacemonkey.service.impl.ConnectionServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
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

import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Arrays;

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
    private final Environment environment;

    public CorrectiveActionController(Properties properties, ConnectionServiceImpl connectionService, Environment environment) {
        this.properties = properties;
        this.connectionService = connectionService;
        this.environment = environment;
    }

    @Override
    @GetMapping("/corrective-action")
    public Mono<ResponseEntity<ServiceResponce>> getCorrectiveAction( final ServerWebExchange exchange) {

        OffsetDateTime start = saveInboundConnections(exchange);

        final ServiceResponce serviceResponse = getServiceResponce(start, ServiceResponseMessages.SERVICE_RESPONSE_ACCEPTED);

        return Mono.just(new ResponseEntity<>(serviceResponse, HttpStatus.OK));
    }

    @Override
    @PostMapping("/corrective-action")
    public Mono<ResponseEntity<ServiceResponce>> setCorrectiveAction(Mono<CaSelectedSamplesData> caSelectedSamplesData, ServerWebExchange exchange) {

        OffsetDateTime start = saveInboundConnections(exchange);

        final ServiceResponce serviceResponse = getServiceResponce(start, ServiceResponseMessages.SERVICE_RESPONSE_PROCESSED);

        return Mono.just(new ResponseEntity<>(serviceResponse, HttpStatus.OK));
    }

    private @NotNull ServiceResponce getServiceResponce(OffsetDateTime start, ServiceResponseMessages message) {
        return getServiceResponce(start, message.getMessage());
    }

    private @NotNull ServiceResponce getServiceResponce(OffsetDateTime start, String message) {

        ServiceResponce response = new ServiceResponce();
        response.setMessage(message);
        response.setDurrationquantity(properties.getDurationQuantity());
        response.setDatetime(start);
        OffsetDateTime end = OffsetDateTime.now();
        response.setDurration(Duration.between(start.toInstant(),end.toInstant()).toNanos());
        return response;
    }

    private OffsetDateTime saveInboundConnections(ServerWebExchange exchange) {
        OffsetDateTime start = OffsetDateTime.now();

        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        InetSocketAddress localAddress = exchange.getRequest().getLocalAddress();
        RequestPath path = exchange.getRequest().getPath();
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();

        Connection connection = new Connection();
        connection.setInboundTime(start);

        if(remoteAddress != null) {
            connection.setRemoteAddress(remoteAddress.toString());
            connection.setRemoteHostName(remoteAddress.getHostName());
            connection.setRemotePort(remoteAddress.getPort());
        }
        else if(Arrays.stream(environment.getActiveProfiles()).toList().contains("dev-test")){
            connection.setRemoteAddress("Test Host");
            connection.setRemoteHostName("Test Host Name");
            connection.setRemotePort(12345);
        }

        if (localAddress != null) {
            connection.setLocalAddress(localAddress.toString());
        }
        else if(Arrays.stream(environment.getActiveProfiles()).toList().contains("dev-test")) {
            connection.setLocalAddress("Test Host");
        }

        connection.setLocalPath(path.toString());
        connection.setLocalQueryParams(queryParams);

        log.atDebug().log("InboundTime: " + connection.getInboundTime());
        log.atDebug().log("RemoteAddress: " + connection.getRemoteAddress());
        log.atDebug().log("RemoteHostName: " + connection.getRemotehostName());
        log.atDebug().log("RemotePort: " + connection.getRemotePort());
        log.atDebug().log("LocalAddress: " + connection.getLocalAddress());
        log.atDebug().log("Path: " + connection.getLocalPath());
        log.atDebug().log("QueryParams: " + connection.getLocalQueryParams());

        connectionService.setConnection(connection)
                .doOnSuccess(conn -> log.info("Connection saved: {}", conn))
                .subscribe();

        return start;
    }

}