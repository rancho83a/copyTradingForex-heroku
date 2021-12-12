package forex.copytradingforex.service.impl;

import forex.copytradingforex.model.entity.RoleEntity;
import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.model.entity.enums.RoleEnum;
import forex.copytradingforex.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@ExtendWith(MockitoExtension.class)
class CopyTradingForexUserDetailsServiceImplTest {

    private CopyTradingForexUserDetailsServiceImpl serviceToTest;
    private UserEntity testUser;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void init(){
        //ARRANGE
        serviceToTest = new CopyTradingForexUserDetailsServiceImpl(mockUserRepository);

        RoleEntity master = new RoleEntity().setRole(RoleEnum.MASTER);
        RoleEntity trader = new RoleEntity().setRole(RoleEnum.TRADER);
        RoleEntity investor = new RoleEntity().setRole(RoleEnum.INVESTOR);

        testUser = new UserEntity();
        testUser.setUsername("master")
                .setPassword("12345")
                .setEmail("master@copytradingforex.com")
                .setRoles(List.of(master, trader, investor));
    }
    @Test
    void testUsernameNotFoundException(){

        Assertions.assertThrows(
                UsernameNotFoundException.class,
                ()->serviceToTest.loadUserByUsername("invalid_username")
        );
    }

    @Test
    void testUsernameFound(){
        //Arrange

        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        //Act
        var actual = serviceToTest.loadUserByUsername(testUser.getUsername());


        //Assert
        Assertions.assertEquals(actual.getUsername(), testUser.getUsername());

        String actualRoles = actual.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        String expected = "ROLE_INVESTOR, ROLE_MASTER, ROLE_TRADER";

        Assertions.assertEquals(actualRoles, expected);

    }



}