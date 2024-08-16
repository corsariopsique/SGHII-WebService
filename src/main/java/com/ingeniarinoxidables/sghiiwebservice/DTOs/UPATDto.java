package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import java.util.Collection;

public class UPATDto {

        private Collection<Authority> authorities;
        private Object details;
        private boolean authenticated;
        private String principal;
        private Object credentials;
        private String name;

    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }

    public Object getDetails() {
            return details;
        }

        public void setDetails(Object details) {
            this.details = details;
        }

        public boolean isAuthenticated() {
            return authenticated;
        }

        public void setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
        }

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public Object getCredentials() {
            return credentials;
        }

        public void setCredentials(Object credentials) {
            this.credentials = credentials;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

}
