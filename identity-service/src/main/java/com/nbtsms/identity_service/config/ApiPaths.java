package com.nbtsms.identity_service.config;

public class ApiPaths {

    public static final String BASE_URL = "http://zone-service/api/v1/zones"; // Base URL

    // Zones endpoints
    public static final String ZONE_EXISTS = BASE_URL + "/zone/{zoneId}/exists";
    public static final String ZONE = BASE_URL + "/zone/{zoneId}/zone";
    public static final String ZONE_ID = BASE_URL + "/zone/{zoneId}/zone-id";

    // Centers endpoints
    public static final String CENTER_EXISTS = BASE_URL + "/centers/{centerId}/exists";
    public static final String CENTER = BASE_URL + "/centers/{centerId}/center";

    // Association check endpoint
    public static final String CENTER_ZONE_ASSOCIATION =
            BASE_URL + "/centers/{zoneId}/center/{centerId}/is-associated";
}
