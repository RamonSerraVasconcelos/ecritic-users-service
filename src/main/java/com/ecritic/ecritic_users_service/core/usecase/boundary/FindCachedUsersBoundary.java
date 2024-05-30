package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindCachedUsersBoundary {

    List<User> execute(Pageable pageable);
}
