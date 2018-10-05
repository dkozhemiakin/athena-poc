package com.jet;

import com.amazonaws.services.athena.AmazonAthena;
import com.amazonaws.services.athena.model.GetQueryExecutionRequest;
import com.amazonaws.services.athena.model.GetQueryExecutionResult;
import com.amazonaws.services.athena.model.QueryExecutionContext;
import com.amazonaws.services.athena.model.QueryExecutionState;
import com.amazonaws.services.athena.model.ResultConfiguration;
import com.amazonaws.services.athena.model.StartQueryExecutionRequest;
import com.amazonaws.services.athena.model.StartQueryExecutionResult;
import com.jet.config.properties.AthenaProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Slf4j
public class AppRunner implements CommandLineRunner {

    private final AmazonAthena amazonAthena;
    private final AthenaProperties athenaProperties;

    @Autowired
    public AppRunner(AmazonAthena amazonAthena, AthenaProperties athenaProperties) {
        this.amazonAthena = amazonAthena;
        this.athenaProperties = athenaProperties;
    }

    @Override
    @SneakyThrows(InterruptedException.class)
    public void run(String... args) {
        log.info("Application started");
        StopWatch stopWatch = new StopWatch("Athena");
        runQuery(stopWatch, "SELECT * FROM csv_athena_table LIMIT 30");
        runQuery(stopWatch, "SELECT * FROM csv_athena_table LIMIT 100");
        runQuery(stopWatch, "SELECT * FROM csv_athena_table WHERE region = 'Sub-Saharan Africa'");
        runQuery(stopWatch, "SELECT order_id, order_date FROM csv_athena_table");
        runQuery(stopWatch, "SELECT * FROM csv_athena_table");
        runQuery(stopWatch, "SELECT * FROM parq_athena_table LIMIT 30");
        runQuery(stopWatch, "SELECT * FROM para_athena_table LIMIT 100");
        runQuery(stopWatch, "SELECT * FROM parq_athena_table WHERE region = 'Sub-Saharan Africa'");
        runQuery(stopWatch, "SELECT order_id, order_date FROM parq_athena_table");
        runQuery(stopWatch, "SELECT * FROM parq_athena_table");
        log.info(stopWatch.prettyPrint());
        log.info("Application finished");
    }

    private void runQuery(StopWatch stopWatch, String query) throws InterruptedException {
        log.info("Running query '{}'", query);
        StartQueryExecutionRequest startQueryExecutionRequest = createRequest(query);

        stopWatch.start(query);
        StartQueryExecutionResult startQueryExecutionResult = amazonAthena
                .startQueryExecution(startQueryExecutionRequest);
        GetQueryExecutionRequest queryExecutionRequest = new GetQueryExecutionRequest()
                .withQueryExecutionId(startQueryExecutionResult.getQueryExecutionId());

        GetQueryExecutionResult queryExecutionResult;
        boolean isQueryStillRunning = true;
        while (isQueryStillRunning) {
            queryExecutionResult = amazonAthena.getQueryExecution(queryExecutionRequest);
            String queryState = queryExecutionResult.getQueryExecution().getStatus().getState();
            switch (QueryExecutionState.fromValue(queryState)) {
                case FAILED:
                    log.warn("Query Failed to run with Error Message: {}",
                            queryExecutionResult.getQueryExecution().getStatus().getStateChangeReason());
                    isQueryStillRunning = false;
                    break;
                case CANCELLED:
                    log.warn("Query was cancelled.");
                    isQueryStillRunning = false;
                    break;
                case SUCCEEDED:
                    isQueryStillRunning = false;
                    break;
                default:
                    Thread.sleep(athenaProperties.getSleepMs());
            }
            log.info("Current Status is: {}", queryState);
        }
        stopWatch.stop();
    }

    private StartQueryExecutionRequest createRequest(String query) {
        QueryExecutionContext queryExecutionContext = new QueryExecutionContext()
                .withDatabase(athenaProperties.getDatabase());

        ResultConfiguration resultConfiguration = new ResultConfiguration()
                .withOutputLocation(athenaProperties.getOutputBucket());

        return new StartQueryExecutionRequest()
                .withQueryString(query)
                .withQueryExecutionContext(queryExecutionContext)
                .withResultConfiguration(resultConfiguration);
    }

}
