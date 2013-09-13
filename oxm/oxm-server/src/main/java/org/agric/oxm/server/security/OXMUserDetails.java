package org.agric.oxm.server.security;
import java.util.Collection;

import org.agric.oxm.model.User;
import org.springframework.security.core.GrantedAuthority;

public class OXMUserDetails extends org.springframework.security.core.userdetails.User{

    private static final long serialVersionUID = 1L;
    private User systemUser;

    public OXMUserDetails(User user, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
	super(user.getUsername(), user.getPassword(), enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked,
				authorities);

	systemUser = user;
    }

    public String getSalt(){
        return systemUser.getSalt();
    }

    public User getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(User systemUser) {
        this.systemUser = systemUser;
    }
    
}
