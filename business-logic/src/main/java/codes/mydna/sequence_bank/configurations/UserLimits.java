package codes.mydna.sequence_bank.configurations;

import codes.mydna.auth.common.enums.Role;
import codes.mydna.auth.common.models.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class UserLimits {

    @Inject
    private ConfigUserLimits config;

    public int getMaxDnaLength(User user) {

        if (user == null)
            return -1;

        List<Role> roles = user.getRoles();

        if (roles.contains(Role.ADMIN) || roles.contains(Role.PRO_USER))
            return config.getProMaxDnaLength();

        if (roles.contains(Role.USER))
            return config.getRegMaxDnaLength();

        return config.getGuestMaxDnaLength();
    }

    public int getMaxEnzymeLength(User user) {

        if (user == null)
            return -1;

        List<Role> roles = user.getRoles();

        if (roles.contains(Role.ADMIN) || roles.contains(Role.PRO_USER))
            return config.getProMaxEnzymeLength();

        if (roles.contains(Role.USER))
            return config.getRegMaxEnzymeLength();

        return config.getGuestMaxEnzymeLength();
    }

    public int getMaxGeneLength(User user) {

        if (user == null)
            return -1;

        List<Role> roles = user.getRoles();

        if (roles.contains(Role.ADMIN) || roles.contains(Role.PRO_USER))
            return config.getProMaxGeneLength();

        if (roles.contains(Role.USER))
            return config.getRegMaxGeneLength();

        return config.getGuestMaxGeneLength();
    }

}
