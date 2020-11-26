package com.javelwilson.nyammingsdb;

import com.javelwilson.nyammingsdb.entity.AuthorityEntity;
import com.javelwilson.nyammingsdb.entity.RoleEntity;
import com.javelwilson.nyammingsdb.entity.UserEntity;
import com.javelwilson.nyammingsdb.repository.AuthorityRepository;
import com.javelwilson.nyammingsdb.repository.RoleRepository;
import com.javelwilson.nyammingsdb.repository.UserRepository;
import com.javelwilson.nyammingsdb.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;

@Component
public class SetUp {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    private String email = "javelawilson@gmail.com";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("From application ready event...");

        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        RoleEntity roleUser = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
        RoleEntity roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

        if(roleAdmin == null) return;

        if (userRepository.findByEmail(email) != null) return;

        UserEntity adminUser = new UserEntity();
        adminUser.setFirstName("Javel");
        adminUser.setLastName("Wilson");
        adminUser.setEmail(email);
        adminUser.setEmailVerificationStatus(true);
        adminUser.setUserId(utils.generateUserId(30));
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
        adminUser.setRoles(Arrays.asList(roleAdmin));

        userRepository.save(adminUser);

    }

    @Transactional
    public AuthorityEntity createAuthority(String name) {
        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);

        }

        return authority;
    }

    @Transactional
    public RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }

        return role;
    }
}
