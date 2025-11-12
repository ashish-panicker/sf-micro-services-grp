package com.example.productservice.service;

import com.example.productservice.ProductResp;
import com.example.productservice.ProductServiceGrpc;
import com.example.productservice.ProductSkuReq;
import com.example.productservice.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.util.Random;

import java.util.concurrent.TimeUnit;

@GrpcService
public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void getProductBySku(ProductSkuReq request, StreamObserver<ProductResp> responseObserver) {
        var sku = request.getSku();
        var product = productRepository.findBySku(sku);
        if (product.isPresent()) {
            responseObserver.onNext(ProductResp.newBuilder()
                    .setId(product.get().getId())
                    .setName(product.get().getName())
                    .setDescription(product.get().getDescription())
                    .setSku(product.get().getSku())
                    .setPrice(product.get().getPrice())
                    .setQuantity(product.get().getQuantity())
                    .build());
            responseObserver.onCompleted();
            return;
        }
        responseObserver.onError(new RuntimeException("Product not found"));

    }

    @Override
    public void streamProductBySku(ProductSkuReq request, StreamObserver<ProductResp> responseObserver) {
        // Simulate a delay
        try {
            // Generate a random number between 1 and 100
            for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(5);
                int randomNumber = new Random().nextInt(100) + 1;
                int randomPrice = new Random().nextInt(1000) + 1;
                responseObserver.onNext(ProductResp.newBuilder()
                        .setId(i)
                        .setName("Product " + i)
                        .setDescription("Product " + i)
                        .setSku("SKU-" + i)
                        .setPrice(randomPrice)
                        .setQuantity(randomNumber)
                        .build());
            }
            responseObserver.onCompleted();
        } catch (InterruptedException e) {
            responseObserver.onError(e);
        }
    }
}
