package car.repair.shop.springbootlambdaapi;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This works as handler for lambda function, need to upload shadow jar to lambda function
 * and set handler to "car.repair.shop.springbootlambdaapi.StreamLambdaHandler::handleRequest"
 * Create Rest api API Gateway
 * create new resource for /api and additional (select /api) /cars
 * then choose create method, select method, choose Lambda Function and IMPORTANT check Lambda proxy integration
 * remember that lambda needs resource based policy to be invoked by API Gateway
 * For better startup use lambda snapStart, go to lambda configuration -> SnapStart -> Published Version
 * Then go yor Lambda - select Actions - Publish new version
 */
public class StreamLambdaHandler implements RequestStreamHandler {

    private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(SpringBootLambdaApiApplication.class);
        } catch (ContainerInitializationException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        handler.proxyStream(inputStream, outputStream, context);
    }
}
