package fr.cg95.sso.cas.principal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.jasig.cas.authentication.principal.Credentials;

public class UsernamePasswordAuthorityCredentials implements Credentials {

    /** Unique ID for serialization. */
    private static final long serialVersionUID = -8343864967200862794L;

    private String username;
    private String password;
    private String authority;

    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(final String password) {
        this.password = password;
    }

    public final String getUsername() {
        return this.username;
    }

    public final void setUsername(final String userName) {
        this.username = userName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    public final String toString() {
        return new ToStringBuilder(this).append("userName", this.username)
            .append("authority", this.authority)
            .toString();
    }

    public final boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public final int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
  
}
