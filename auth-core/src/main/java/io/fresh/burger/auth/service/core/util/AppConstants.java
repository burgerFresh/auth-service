package io.fresh.burger.auth.service.core.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class AppConstants {
    public static final String DELETE_CLIENT_ROLE = "UPDATE client_role SET is_actual = false WHERE client_role_id = ?";
    public static final String DELETE_CLIENT_ROLE_GROUP = "UPDATE client_role_group SET is_actual = false WHERE client_role_group_id = ?";
    public static final String DELETE_CLIENT_SECURITY_INFO = "UPDATE client_security_info SET deleted_date = CURRENT_DATE WHERE client_security_info_id = ?";
    public static final String API_VERSION = "/v1";
}