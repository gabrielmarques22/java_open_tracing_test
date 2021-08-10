package com.example.restservice;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;


@RestController
public class LoanController {
        //@Autowired
	//LoanRequest loanService;

        static Logger log = Logger.getLogger(LoanController.class.getName());
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
        
	@PostMapping(path = "new_loan",
                     consumes = MediaType.APPLICATION_JSON_VALUE, 
                     produces = MediaType.APPLICATION_JSON_VALUE)
	public LoanResponse loanRequestProcessor(@RequestBody LoanRequest loanRequest) {
                try(InputStream input = new FileInputStream("config.properties")){
                    Properties properties = new Properties();
                    properties.load(input);
                    String approvalFunction = properties.getProperty("approval_lambda_function");
                    String access_key_id = properties.getProperty("access_key_id");
                    String secret_access_key = properties.getProperty("secret_access_key");
                    String approvalEndPoint = properties.getProperty("approval_endpoint");
                    long LoanRequestId = System.currentTimeMillis();
                    ArrayList<String> trusted_cpfs = new ArrayList<String>();
                    trusted_cpfs.add("11088036015");
                    trusted_cpfs.add("86264276626");
                    trusted_cpfs.add("71609155505");
                    trusted_cpfs.add("49439314214");
                    trusted_cpfs.add("15687847162");
                    trusted_cpfs.add("87453911656");
                    trusted_cpfs.add("61547427892");
                    trusted_cpfs.add("56763187831");
                    trusted_cpfs.add("81250474418");
                    trusted_cpfs.add("76152567203");
                    boolean isRiskLoan;
                    if (trusted_cpfs.contains(loanRequest.getCPF())) isRiskLoan = false;
                    else isRiskLoan = true;
                    RestTemplate restTemplate = new RestTemplate();
                    ApprovalRequest approvalRequest = new ApprovalRequest(loanRequest.getValue(), isRiskLoan, LoanRequestId);
                    //ApprovalResponse approvalResponse = restTemplate.postForObject(approvalEndPoint, approvalRequest, ApprovalResponse.class);
                    String approvalResponse = restTemplate.postForObject(approvalEndPoint, approvalRequest, String.class);
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode root = objectMapper.readTree(approvalResponse);
                            //"xxx", ,ApprovalResponse.class);
                    /*InvokeRequest invokeRequest = new InvokeRequest()
                        .withFunctionName(approvalFunction)
                        .withPayload(approvalRequest.toString());
                    InvokeResult invokeResult = null;*/
                    
                    /*try {
                        //AwsBasicCredentials awsCreds = AwsBasicCredentials.create(access_key_id, secret_access_key);
                        BasicAWSCredentials awsCreds = new BasicAWSCredentials(access_key_id, secret_access_key);
                        //AwsCredentialsProvider provider = AwsCredentialsProvider.class
                        ProfileCredentialsProvider profile = new ProfileCredentialsProvider();
                        //System.out.println(profile.getCredentials().getAWSAccessKeyId());
                        //System.out.println(profile.getCredentials().getAWSSecretKey());
                        AWSLambda awsLambda = AWSLambdaClientBuilder.standard().withCredentials(profile)//new AWSStaticCredentialsProvider(awsCreds))
                                //.withCredentials(new ProfileCredentialsProvider())
                                .withRegion(Regions.US_EAST_1).build();

                        invokeResult = awsLambda.invoke(invokeRequest);

                        String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

                        //write out the return value
                        log.info(ans);

                    } catch (ServiceException e) {
                        log.error(e);
                    }*/

                    //long id, String cpf, double requested_value, double approved_value, int approval_status_code
                    //return new LoanResponse(LoanRequestId, loanRequest.getCPF(), loanRequest.getValue(), approvalResponse.getApprovedValue(), approvalResponse.getApprovalStatusCode());
                    LoanResponse loanResponse = new LoanResponse(LoanRequestId, loanRequest.getCPF(), loanRequest.getValue(), root.path("approved_value").asDouble(), root.path("approval_status_code").asInt());
                    System.out.println(loanResponse.toString());
                    System.out.println(approvalResponse.toString());
                    log.info(loanResponse.toString());
                    return loanResponse;
                }
                catch (IOException ex){
                    log.error("Could not get the approval endpoint from configuration file.");
                    return new LoanResponse(0, loanRequest.getCPF(), loanRequest.getValue(), 0, 0);
                }
                
	}
}
