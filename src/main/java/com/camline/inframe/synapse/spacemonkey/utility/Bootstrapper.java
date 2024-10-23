package com.camline.inframe.synapse.spacemonkey.utility;

import com.camline.inframe.synapse.spacemonkey.repository.ConfigSynapseRepository;
import com.camline.inframe.synapse.spacemonkey.service.impl.ConfigSynapseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class Bootstrapper implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Bootstrapper.class);

    private final ConfigSynapseRepository configSynapseRepository;
    private final DatabaseHealthService databaseHealthService;
    private final ConfigSynapseServiceImpl configSynapseService;
    private final ApplicationContext appContext;

    public Bootstrapper(ConfigSynapseRepository configSynapseRepository, DatabaseHealthService databaseHealthService, ConfigSynapseServiceImpl configSynapseService, ApplicationContext appContext) {
        this.configSynapseRepository = configSynapseRepository;
        this.databaseHealthService = databaseHealthService;
        this.configSynapseService = configSynapseService;
        this.appContext = appContext;
    }

    @Override
    public void run(String... args) {

        log.atInfo().log("Starting Bootstrapper...");
        log.atInfo().log("Bootstrapper: Mongo Database is accessible");
        boolean isMongoAccessible = Boolean.TRUE.equals(databaseHealthService.isMongoDbAccessible().block());
        if (!isMongoAccessible) {
            tearDownService();
        }
        else {
            log.atInfo().log("Bootstrapper: Validate Synapse Configs");
            configSynapseRepository.count()
                    .flatMap(count -> {
                        if (count == 0) {
                            log.atInfo().log("No Synapse configs found. Initialising Synapse config from properties...");
                            return this.configSynapseService.initSynapse();
                        } else if (count > 1) {
                            log.atError().log("Recognized, more than one synapse config found - validate the controller implementation!");
                            log.atError().log("Reinitializing Synapse config, from properties...");
                            return this.configSynapseService.restoreDefaultSynapse();
                        } else {
                            return Mono.empty();
                        }
                    }).subscribe();
        }



    }

    private void tearDownService(){
        log.atError().log("Bootstrapper: MongoDB not accessible. Shutting down application.");
        SpringApplication.exit(appContext, () -> -1);
    }

}
