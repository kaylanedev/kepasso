package util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {
	// Método para criptografar a senha em SHA-256
    public static String hashSenha(String senhaLimpa) {
        if (senhaLimpa == null || senhaLimpa.isEmpty()) {
            return "";
        }
        
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = algorithm.digest(senhaLimpa.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString(); // Retorna o hash de 64 caracteres
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha", e);
        }
    }
}
