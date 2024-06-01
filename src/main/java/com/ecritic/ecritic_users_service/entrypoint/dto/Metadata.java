package com.ecritic.ecritic_users_service.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Metadata {

    private Integer page;
    private Integer nextPage;
    private Integer size;
    private Long total;
}
