package com.tps.tpi;

import org.junit.After;
import org.junit.Before;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 3:21:12 PM
 * Responsibility:
 */
public abstract class AbstractTest extends AbstractJUnit4SpringContextTests {
    @Before
    public void createSecureChannel() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            GrantedAuthority[] auths = new GrantedAuthority[2];
            auths[0] = new GrantedAuthorityImpl("ROLE_ADMINISTRATOR");
            auths[1] = new GrantedAuthorityImpl("ROLE_USER");

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("test", "test", auths);
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }

    @After
    public void destroySecureChannel() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
