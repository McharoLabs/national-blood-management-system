package com.nbts.management.donor_service.config;

public class ApiPaths {
    public static final String BASE_URL = "http://identity-service/api/v1/identity";

    public static final String STAFF_EXISTS = BASE_URL + "/{staffId}/exists";
    public static final String STAFF_ZONE_ID = BASE_URL + "/{staffId}/zone-id";
}
