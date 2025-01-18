package com.example.demogrpcserver.grpc;


import com.example.api_gateway.grpc.MyGrpcServiceGrpc;
import com.example.api_gateway.grpc.TestRequest;
import com.example.api_gateway.grpc.TestResponse;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GrpcServer extends MyGrpcServiceGrpc.MyGrpcServiceImplBase {

    public StreamObserver<TestResponse> streamObserver;

    @Override
    public void testGateway(TestRequest request, StreamObserver<TestResponse> responseObserver) {

        try {

            streamObserver = responseObserver;

            System.out.println(JsonFormat.printer().print(request));

            responseObserver.onNext(TestResponse.newBuilder().setResult(request.getData()).build());
            responseObserver.onCompleted();
        } catch (InvalidProtocolBufferException e) {
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                    .withDescription("Invalid JSON format")
                    .withCause(e)
                    .asRuntimeException());
        }


    }

}
