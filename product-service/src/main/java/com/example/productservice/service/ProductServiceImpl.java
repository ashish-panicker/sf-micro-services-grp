package com.example.productservice.service;

import com.example.productservice.*;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;

import java.util.Random;

import java.util.concurrent.TimeUnit;

@GrpcService
public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {


    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

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

    @Override
    public StreamObserver<BulkProductReq> bulkProductLoading(StreamObserver<BulkProductResp> responseObserver) {
        return new StreamObserver<BulkProductReq>() {

            private int totalOrders = 0;
            private int totalSuccess = 0;

            @Override
            public void onNext(BulkProductReq bulkProductReq) {
                Product product = new Product(bulkProductReq.getId(), bulkProductReq.getName(), bulkProductReq.getDescription(),
                        bulkProductReq.getSku(), bulkProductReq.getPrice(), bulkProductReq.getQuantity(), true);
                productRepository.save(product);
                totalOrders++;
                totalSuccess++;
                logger.info("Product saved successfully: {}", product);
            }

            @Override
            public void onError(Throwable throwable) {
                --totalSuccess;
                responseObserver.onError(throwable);

            }

            @Override
            public void onCompleted() {
                var summary = BulkProductResp.newBuilder()
                        .setTotalOrders(totalOrders)
                        .setTotalSuccess(totalSuccess)
                        .build();
                responseObserver.onNext(summary);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<BulkProductReq> liveProductLoading(StreamObserver<BulkProductResp> responseObserver) {
        return new StreamObserver<BulkProductReq>() {
            @Override
            public void onNext(BulkProductReq bulkProductReq) {
                Product product = new Product(bulkProductReq.getId(), bulkProductReq.getName(), bulkProductReq.getDescription(),
                        bulkProductReq.getSku(), bulkProductReq.getPrice(), bulkProductReq.getQuantity(), true);
                productRepository.save(product);
                logger.info("Product saved successfully: {}", product);
                responseObserver.onNext(BulkProductResp.newBuilder()
                        .setTotalOrders(1)
                        .setTotalSuccess(1)
                        .build());
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
                logger.error("Error while saving product", throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
