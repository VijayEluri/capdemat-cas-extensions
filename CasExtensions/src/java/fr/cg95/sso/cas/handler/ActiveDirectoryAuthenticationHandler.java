package fr.cg95.sso.cas.handler;

import java.util.Hashtable;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.AuthenticationHandler;
import org.jasig.cas.authentication.principal.Credentials;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import fr.cg95.sso.cas.principal.UsernamePasswordAuthorityCredentials;

public class ActiveDirectoryAuthenticationHandler implements AuthenticationHandler {

    private static Logger log = 
        Logger.getLogger(ActiveDirectoryAuthenticationHandler.class);

    private static final Class SUPPORTED_CLASS = UsernamePasswordAuthorityCredentials.class;

    // La branche qui contiens les users.
    private String userProvider="ou=people";
    
    // Admin DN
    private String adminDn = "cn=admin,dc=localdomain";
    
    // Admin password
    private String adminPassword="aqw";
    
    // @ du serveur ldap
    private String ldapUrl = "ldap://localhost:389/";

    private String ditRoot = "dc=localdomain";

    private boolean authenticateUser(String dn, String password) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.SECURITY_PRINCIPAL, dn);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, ldapUrl);
        
        InitialContext context=null;
        try {
            context = new InitialContext(env);
            // if context successfully initiated, that's ok
            return true;
        } catch (NamingException e) {
            log.error("authenticateUser() failed for user " + dn + " : ", e);
        } finally {
            if (context!=null)
                try {
                    context.close();
                } catch (NamingException e) {
                    log.error("authenticateUser() error while closing LDAP connection : ", e);
                }
        }
        return false;
    }
    
    private SearchResult fetchDn(String uid) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.SECURITY_PRINCIPAL,adminDn);
        env.put(Context.SECURITY_CREDENTIALS,adminPassword);
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, ldapUrl);
        
        // an LDAP context is required in order to do lookups directly on this context.
        // either, Active Directory will not allow us to perform searches on a "derivated" context
        LdapContext context = null;
        try {
            context = new InitialLdapContext(env, null);
            
            String userBaseBranch = userProvider + "," + ditRoot;
            SearchControls searchControl = new SearchControls();
            searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = "(&(objectClass=user)(sAMAccountName=" + uid + "))";
            NamingEnumeration namingEnumeration = 
                context.search(userBaseBranch,searchFilter,searchControl);
            if (namingEnumeration.hasMore()) {
                SearchResult result = (SearchResult) namingEnumeration.next();
                return result;
            } else {
                log.error("fetchDn() user with uid " + uid + " does not exist");
            }
        } catch (NamingException e) {
            log.error("fetchDn() error while requesting directory, " 
                    + "check your configuration (mainly adminDn et adminPassword)", e);
        } finally {
            if (context!=null)
                try {
                    context.close();
                } catch (NamingException e) {
                    log.error("fetchDn() error while closing connection to directory", e);
                }
        }
        
        return null;
    }
    
    public boolean authenticate(Credentials credentials) throws AuthenticationException {
        UsernamePasswordAuthorityCredentials myCredentials =
            (UsernamePasswordAuthorityCredentials) credentials;
        SearchResult result = fetchDn(myCredentials.getUsername());
        if (result == null)
            return false;
        String userDn = result.getNameInNamespace();
        if (userDn == null)
            return false;
        if (!authenticateUser(userDn, myCredentials.getPassword()))
            return false;
        StringBuffer credentialsBuffer = new StringBuffer();
        credentialsBuffer.append("username=").append(myCredentials.getUsername());
        if (myCredentials.getAuthority() != null && !myCredentials.getAuthority().equals(""))
            credentialsBuffer.append(";localAuthority=").append(myCredentials.getAuthority());
        Attributes attributes = result.getAttributes();
        try {
            if (attributes.get("givenName") != null)
                credentialsBuffer.append(";firstName=").append(attributes.get("givenName").get());
            if (attributes.get("sn") != null)
                credentialsBuffer.append(";lastName=").append(attributes.get("sn").get());
            if (attributes.get("memberOf") != null) {
                NamingEnumeration<?> groups = attributes.get("memberOf").getAll();
                while (groups.hasMore()) {
                    Object object = groups.nextElement();
                    String longGroup = (String) object;
                    // groups are full DNs, we are only interested in group name (ie CN)
                    longGroup = longGroup.substring(longGroup.indexOf("CN=") + 3);
                    longGroup = longGroup.substring(0, longGroup.indexOf(','));
                    credentialsBuffer.append(";group=").append(longGroup);
                }
            }
        } catch (NamingException ne) {
            log.error("authenticate() naming exception : " + ne.getMessage());
            ne.printStackTrace();
        }
        myCredentials.setUsername(credentialsBuffer.toString());
        return true;
    }

    public boolean supports(Credentials credentials) {

        return credentials != null
            && (SUPPORTED_CLASS.equals(credentials.getClass())
                    || (SUPPORTED_CLASS.isAssignableFrom(credentials.getClass())));
    }

    public void setAdminDn(String adminDn) {
        this.adminDn = adminDn;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setLdapUrl(String ldapUrl) {
        this.ldapUrl = ldapUrl;
    }

    public void setUserProvider(String userProvider) {
        this.userProvider = userProvider;
    }

    public void setDitRoot(String ditRoot) {
        this.ditRoot = ditRoot;
    }
}
