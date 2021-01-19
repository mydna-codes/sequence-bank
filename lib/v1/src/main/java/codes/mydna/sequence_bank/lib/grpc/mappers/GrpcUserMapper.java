package codes.mydna.sequence_bank.lib.grpc.mappers;

import codes.mydna.auth.common.enums.Role;
import codes.mydna.auth.common.models.User;
import codes.mydna.lib.grpc.CommonProto;

import java.util.List;
import java.util.stream.Collectors;

public class GrpcUserMapper {

    private static List<String> toGrpcRoles(List<Role> roles){
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    private static List<Role> fromGrpcRoles(List<String> roles){
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toList());
    }

    public static CommonProto.User toGrpcUser(User user){

        if(user == null)
            return CommonProto.User.newBuilder()
                    .setIsNull(true)
                    .build();

        return CommonProto.User.newBuilder()
                .setIsNull(false)
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .addAllRoles(toGrpcRoles(user.getRoles()))
                .build();
    }

    public static User fromGrpcUser(CommonProto.User grpcUser){

        if(grpcUser.getIsNull())
            return null;

        User user = new User();
        user.setId(grpcUser.getId());
        user.setUsername(grpcUser.getUsername());
        user.setEmail(grpcUser.getEmail());
        user.setRoles(fromGrpcRoles(grpcUser.getRolesList()));
        return user;
    }

}
