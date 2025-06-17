package lt.cartwise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import lt.cartwise.auth.AuthDto;
import lt.cartwise.auth.AuthService;
import lt.cartwise.auth.RefreshToken;
import lt.cartwise.auth.SignupRequest;
import lt.cartwise.enums.Role;
import lt.cartwise.exceptions.DuplicateEntryException;
import lt.cartwise.security.JwtUtils;
import lt.cartwise.user.dto.UserDto;
import lt.cartwise.user.entities.User;
import lt.cartwise.user.mappers.UserMapper;
import lt.cartwise.user.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserMapper userMapper;
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testRefreshToken() {
        // Arrange
        String oldToken = "old-refresh-token";
        String newRefreshTokenValue = "new-refresh-token";
        String email = "test@example.com";
        String jwtToken = "jwt-token";

        User mockUser = new User();
        mockUser.setEmail(email);

        RefreshToken mockNewRefreshToken = Mockito.mock(RefreshToken.class);
        Mockito.when(mockNewRefreshToken.getUser()).thenReturn(mockUser);
        Mockito.when(mockNewRefreshToken.getToken()).thenReturn(newRefreshTokenValue);

        UserDto userDto = new UserDto();

        AuthService spyService = Mockito.spy(authService);
        Mockito.doReturn(mockNewRefreshToken).when(spyService).rotateToken(oldToken);

        Mockito.when(jwtUtils.generateToken(email)).thenReturn(jwtToken);
        Mockito.when(userMapper.toDto(mockUser)).thenReturn(userDto);

        // Act
        AuthDto result = spyService.refreshToken(oldToken);

        // Assert
        assertEquals(jwtToken, result.getToken());
        assertEquals(newRefreshTokenValue, result.getRefreshToken());
        assertEquals(userDto, result.getUser());
    }
    
    @Test
    void signUp_throwsDuplicateEntryException_ifEmailExists() {
        // Arrange
        SignupRequest request = new SignupRequest("test@example.com", "Test User", "password123");
        Mockito.when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(new User()));

        // Act & Assert
        DuplicateEntryException ex = assertThrows(DuplicateEntryException.class, () -> authService.signUp(request));
        assertTrue(ex.getMessage().contains(request.email()));

        Mockito.verify(userRepository, never()).save(any());
    }

    @Test
    void signUp_savesUserSuccessfully_ifEmailNotExists() {
        // Arrange
        SignupRequest request = new SignupRequest("new@example.com", "New User", "password123");
        Mockito.when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");

        // Act
        authService.signUp(request);

        // Assert
        Mockito.verify(userRepository).save(argThat(user ->
            user.getEmail().equals(request.email()) &&
            user.getName().equals(request.name()) &&
            user.getPassword().equals("encodedPassword") &&
            user.isActive() == true &&
            user.getRole() == Role.USER
        ));
    }
}
