package com.ao.service.user;

import com.ao.model.user.ConnectedUser;
import com.ao.network.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserServiceImplTest {

    @Mock
    private Connection connection;

    private UserServiceImpl userService;
    private ConnectedUser user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl();
        user = new ConnectedUser(connection);
    }

    @Test
    void getConnectedUsers_initially_returnsEmptySet() {
        assertThat(userService.getConnectedUsers()).isEmpty();
    }

    @Test
    void logIn_addsUserToConnectedSet() {
        userService.logIn(user);

        assertThat(userService.getConnectedUsers()).containsExactly(user);
    }

    @Test
    void logOut_removesUserFromConnectedSet() {
        userService.logIn(user);
        userService.logOut(user);

        assertThat(userService.getConnectedUsers()).isEmpty();
    }

    @Test
    void isLoggedIn_returnsTrueAfterLogIn() {
        userService.logIn(user);

        assertThat(userService.isLoggedIn(user)).isTrue();
    }

    @Test
    void isLoggedIn_returnsFalseBeforeLogIn() {
        assertThat(userService.isLoggedIn(user)).isFalse();
    }

    @Test
    void isLoggedIn_returnsFalseAfterLogOut() {
        userService.logIn(user);
        userService.logOut(user);

        assertThat(userService.isLoggedIn(user)).isFalse();
    }

    @Test
    void getConnectedUsers_returnsImmutableSnapshot() {
        userService.logIn(user);
        Set<ConnectedUser> snapshot = userService.getConnectedUsers();

        assertThatThrownBy(() -> snapshot.add(new ConnectedUser(connection)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void getConnectedUsers_snapshotDoesNotReflectSubsequentLogOut() {
        userService.logIn(user);
        Set<ConnectedUser> snapshot = userService.getConnectedUsers();

        userService.logOut(user);

        assertThat(snapshot).containsExactly(user);
        assertThat(userService.getConnectedUsers()).isEmpty();
    }

}