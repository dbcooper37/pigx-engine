package com.pigx.engine.oauth2.extension.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.oauth2.extension.entity.OAuth2UserLogging;


public interface OAuth2UserLoggingRepository extends BaseJpaRepository<OAuth2UserLogging, String> {
}
