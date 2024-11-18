package mariana.thePotteryPlace.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

public class JWTGenerateToken {
    private static final String SECRET = "secret";  // Your secret key
    private static final long EXPIRATION_TIME = 86400000L;  // 1 day in milliseconds

    public static String generateToken(String subject) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);  // Use the secret key to sign the token
        return JWT.create()
                .withSubject(subject)  // Set the subject (e.g., username or user ID)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Set expiration time
                .sign(algorithm);  // Sign the token
    }

    public static void main(String[] args) {
        // Example: generate a token for a user
        String token = generateToken("test-user@example.com");
        System.out.println("Generated Token: " + token);
    }
}