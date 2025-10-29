package com.pigx.engine.logic.identity.service;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.identity.entity.OAuth2Product;
import com.pigx.engine.logic.identity.repository.OAuth2ProductRepository;
import org.springframework.stereotype.Service;


@Service
public class OAuth2ProductService extends AbstractJpaService<OAuth2Product, String> {

    private final OAuth2ProductRepository productRepository;

    public OAuth2ProductService(OAuth2ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public BaseJpaRepository<OAuth2Product, String> getRepository() {
        return productRepository;
    }
}
