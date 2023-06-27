package com.thanh.ebacsi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private Long userId;
    private Long roleId;
    private String roleName;
}
