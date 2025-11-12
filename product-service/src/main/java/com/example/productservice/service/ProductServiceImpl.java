package com.example.productservice.service;

import com.example.productservice.ProductResp;
import com.example.productservice.ProductServiceGrpc;
import com.example.productservice.ProductSkuReq;
import com.example.productservice.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

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
}
