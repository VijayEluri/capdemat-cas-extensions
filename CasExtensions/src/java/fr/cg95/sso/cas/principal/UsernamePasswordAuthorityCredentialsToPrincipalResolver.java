/*
 * Copyright 2005 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.uportal.org/license.html
 */
package fr.cg95.sso.cas.principal;

import org.apache.log4j.Logger;

import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.CredentialsToPrincipalResolver;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.SimplePrincipal;

/**
 * Implementation of CredentialsToPrincipalResolver for Credentials based on
 * UsernamePasswordAuthorityCredentials when a SimplePrincipal (username only) is
 * sufficient.
 * <p>
 * Implementation extracts the username from the Credentials provided and
 * constructs a new SimplePrincipal with the unique id set to the username.
 * </p>
 * 
 * @author Scott Battaglia, Benoit Orihuela
 * @see org.jasig.cas.authentication.principal.SimplePrincipal
 */
public final class UsernamePasswordAuthorityCredentialsToPrincipalResolver implements
    CredentialsToPrincipalResolver {

    /** Logging instance. */
    private static Logger log = 
        Logger.getLogger(UsernamePasswordAuthorityCredentialsToPrincipalResolver.class);

    /**
     * Constructs a SimplePrincipal from the username provided in the
     * credentials.
     * 
     * @param credentials the Username and Password provided as credentials.
     * @return an instance of the principal where the id is the username.
     */
    public Principal resolvePrincipal(final Credentials credentials) {
        final UsernamePasswordAuthorityCredentials myCredentials = 
            (UsernamePasswordAuthorityCredentials) credentials;

        log.debug("Creating SimplePrincipal for ["
                + myCredentials.getUsername() + "]");

        return new SimplePrincipal(myCredentials.getUsername());
    }

    /**
     * Return true if Credentials are UsernamePasswordAuthorityCredentials, false
     * otherwise.
     */
    public boolean supports(final Credentials credentials) {
        return credentials != null
            && UsernamePasswordAuthorityCredentials.class.isAssignableFrom(credentials
                .getClass());
    }
}
