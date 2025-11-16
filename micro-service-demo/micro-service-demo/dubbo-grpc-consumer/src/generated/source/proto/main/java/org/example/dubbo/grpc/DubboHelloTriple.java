/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.example.dubbo.grpc;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.PathResolver;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.ServerService;
import org.apache.dubbo.rpc.TriRpcStatus;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ServiceDescriptor;
import org.apache.dubbo.rpc.model.StubMethodDescriptor;
import org.apache.dubbo.rpc.model.StubServiceDescriptor;
import org.apache.dubbo.rpc.service.Destroyable;
import org.apache.dubbo.rpc.stub.BiStreamMethodHandler;
import org.apache.dubbo.rpc.stub.ServerStreamMethodHandler;
import org.apache.dubbo.rpc.stub.StubInvocationUtil;
import org.apache.dubbo.rpc.stub.StubInvoker;
import org.apache.dubbo.rpc.stub.StubMethodHandler;
import org.apache.dubbo.rpc.stub.StubSuppliers;
import org.apache.dubbo.rpc.stub.UnaryStubMethodHandler;

import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.concurrent.CompletableFuture;

public final class DubboHelloTriple {

    public static final String SERVICE_NAME = Hello.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME,Hello.class);

    static {
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME,HelloOuterClass.getDescriptor());
        StubSuppliers.addSupplier(SERVICE_NAME, DubboHelloTriple::newStub);
        StubSuppliers.addSupplier(Hello.JAVA_SERVICE_NAME,  DubboHelloTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(Hello.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("all")
    public static Hello newStub(Invoker<?> invoker) {
        return new HelloStub((Invoker<Hello>)invoker);
    }

    private static final StubMethodDescriptor helloMethod = new StubMethodDescriptor("hello",
    org.example.dubbo.grpc.HelloRequest.class, org.example.dubbo.grpc.HelloResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.example.dubbo.grpc.HelloRequest::parseFrom,
    org.example.dubbo.grpc.HelloResponse::parseFrom);

    private static final StubMethodDescriptor helloAsyncMethod = new StubMethodDescriptor("hello",
    org.example.dubbo.grpc.HelloRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.example.dubbo.grpc.HelloRequest::parseFrom,
    org.example.dubbo.grpc.HelloResponse::parseFrom);

    private static final StubMethodDescriptor helloProxyAsyncMethod = new StubMethodDescriptor("helloAsync",
    org.example.dubbo.grpc.HelloRequest.class, org.example.dubbo.grpc.HelloResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.example.dubbo.grpc.HelloRequest::parseFrom,
    org.example.dubbo.grpc.HelloResponse::parseFrom);




    static{
        serviceDescriptor.addMethod(helloMethod);
        serviceDescriptor.addMethod(helloProxyAsyncMethod);
    }

    public static class HelloStub implements Hello, Destroyable {
        private final Invoker<Hello> invoker;

        public HelloStub(Invoker<Hello> invoker) {
            this.invoker = invoker;
        }

        @Override
        public void $destroy() {
              invoker.destroy();
         }

        @Override
        public org.example.dubbo.grpc.HelloResponse hello(org.example.dubbo.grpc.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, helloMethod, request);
        }

        public CompletableFuture<org.example.dubbo.grpc.HelloResponse> helloAsync(org.example.dubbo.grpc.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, helloAsyncMethod, request);
        }

        public void hello(org.example.dubbo.grpc.HelloRequest request, StreamObserver<org.example.dubbo.grpc.HelloResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, helloMethod , request, responseObserver);
        }



    }

    public static abstract class HelloImplBase implements Hello, ServerService<Hello> {

        private <T, R> BiConsumer<T, StreamObserver<R>> syncToAsync(java.util.function.Function<T, R> syncFun) {
            return new BiConsumer<T, StreamObserver<R>>() {
                @Override
                public void accept(T t, StreamObserver<R> observer) {
                    try {
                        R ret = syncFun.apply(t);
                        observer.onNext(ret);
                        observer.onCompleted();
                    } catch (Throwable e) {
                        observer.onError(e);
                    }
                }
            };
        }

        @Override
        public CompletableFuture<org.example.dubbo.grpc.HelloResponse> helloAsync(org.example.dubbo.grpc.HelloRequest request){
                return CompletableFuture.completedFuture(hello(request));
        }

        /**
        * This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
        * It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.
        */
        public void hello(org.example.dubbo.grpc.HelloRequest request, StreamObserver<org.example.dubbo.grpc.HelloResponse> responseObserver){
            helloAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        @Override
        public final Invoker<Hello> getInvoker(URL url) {
            PathResolver pathResolver = url.getOrDefaultFrameworkModel()
            .getExtensionLoader(PathResolver.class)
            .getDefaultExtension();
            Map<String,StubMethodHandler<?, ?>> handlers = new HashMap<>();

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/hello");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/helloAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/hello");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/helloAsync");


            BiConsumer<org.example.dubbo.grpc.HelloRequest, StreamObserver<org.example.dubbo.grpc.HelloResponse>> helloFunc = this::hello;
            handlers.put(helloMethod.getMethodName(), new UnaryStubMethodHandler<>(helloFunc));
            BiConsumer<org.example.dubbo.grpc.HelloRequest, StreamObserver<org.example.dubbo.grpc.HelloResponse>> helloAsyncFunc = syncToAsync(this::hello);
            handlers.put(helloProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(helloAsyncFunc));




            return new StubInvoker<>(this, url, Hello.class, handlers);
        }


        @Override
        public org.example.dubbo.grpc.HelloResponse hello(org.example.dubbo.grpc.HelloRequest request){
            throw unimplementedMethodException(helloMethod);
        }





        @Override
        public final ServiceDescriptor getServiceDescriptor() {
            return serviceDescriptor;
        }
        private RpcException unimplementedMethodException(StubMethodDescriptor methodDescriptor) {
            return TriRpcStatus.UNIMPLEMENTED.withDescription(String.format("Method %s is unimplemented",
                "/" + serviceDescriptor.getInterfaceName() + "/" + methodDescriptor.getMethodName())).asException();
        }
    }

}
