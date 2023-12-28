package com.example.test.web.security;

import com.example.test.client.MembershipClient;
import com.example.test.client.model.request.ValidatePrivilegeClientRequest;
import com.example.test.client.model.response.AuthenticationClientResponse;
import com.example.test.common.enums.PermissionEnum;
import com.example.test.common.enums.RoleEnum;
import com.example.test.web.model.resolver.AccessTokenParameter;
import com.example.test.web.model.resolver.RequestTokenAccess;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component
public class MustAuthenticatedValidatorImpl implements MustAuthenticatedValidator {

  private final MembershipClient membershipClient;

  public MustAuthenticatedValidatorImpl(MembershipClient membershipClient) {
    this.membershipClient = membershipClient;
  }

  @Override
  public Mono<AccessTokenParameter> throwIfInvalid(MustAuthenticated mustAuthenticated,
      RequestTokenAccess requestTokenAccess) {
    return Mono.fromSupplier(() -> requestTokenAccess)
        .filter(check -> mustAuthenticated.value())
        .flatMap(token -> Mono.fromSupplier(() -> token)
            .flatMap(tokenReq -> checkTokenToAuth(tokenReq, mustAuthenticated)));
  }

  private Mono<AccessTokenParameter> checkTokenToAuth(RequestTokenAccess tokenReq,
      MustAuthenticated mustAuthenticated) {
    System.out.println(mustAuthenticated);
    System.out.println(tokenReq);
    return membershipClient.validatePrivilege(tokenReq.getCompanyId(), tokenReq.getToken(),
            createRequest(mustAuthenticated.userRole(), mustAuthenticated.userPermission()))
        .map(s -> toAccessTokenParameter(s.getData()));
  }

  private AccessTokenParameter toAccessTokenParameter(AuthenticationClientResponse data) {
    return AccessTokenParameter.builder()
        .accountId(data.getAccountId())
        .companyGroupId(data.getCompanyGroupId())
        .companyId(data.getCompanyId())
        .companyGroupIdDefault(data.getCompanyGroupIdDefault())
        .companyIdDefault(data.getCompanyIdDefault())
        .roles(data.getRoles())
        .permissions(data.getPermissions())
        .name(data.getName())
        .username(data.getUsername())
        .acceptedRole(data.getAcceptedRole())
        .acceptedPermission(data.getAcceptedPermission())
        .build();
  }

  private Mono<AccessTokenParameter> createEmptyTokenAccessParameter() {
    return Mono.fromSupplier(() -> AccessTokenParameter.builder()
        .build());
  }

  private ValidatePrivilegeClientRequest createRequest(RoleEnum[] roleEnums, PermissionEnum[] permissionEnums) {
    return ValidatePrivilegeClientRequest.builder()
        .roles(Arrays.asList(roleEnums))
        .permissions(Arrays.asList(permissionEnums))
        .build();
  }

  //  private boolean validate(MustAuthenticated mustAuthenticated, AccessTokenParameter param) {
  //    return Arrays.stream(mustAuthenticated.userRole())
  //        .anyMatch(userType -> userType.equals(param.getRole())) && StringUtils.isNotBlank(param.getUserId()) &&
  //        StringUtils.isNotBlank(param.getCompanyId());
  //  }
}
