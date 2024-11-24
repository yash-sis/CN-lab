import java.math.BigInteger;
import java.util.Random;
import java.io.*;

public class RSA {
    BigInteger p, q, N, phi, e, d;
    int bitlength = 100;
    Random r;

    // Constructor for RSA key generation
    public RSA() {
        r = new Random();
        // Generate two large random primes p and q
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);

        // Compute N = p * q
        N = p.multiply(q);

        // Compute φ(N) = (p-1) * (q-1)
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Choose e such that 1 < e < φ and gcd(e, φ) = 1
        e = BigInteger.probablePrime(bitlength / 2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e = e.add(BigInteger.ONE);
        }

        // Compute d such that e * d ≡ 1 (mod φ)
        d = e.modInverse(phi);
    }

    // Encrypt the message
    public byte[] encrypt(byte[] message) {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    // Decrypt the message
    public byte[] decrypt(byte[] message) {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }

    // Convert byte array to string
    private static String bytesToString(byte[] encrypted) {
        StringBuilder test = new StringBuilder();
        for (byte b : encrypted) {
            test.append(Byte.toString(b));
        }
        return test.toString();
    }

    public static void main(String[] args) throws IOException {
        RSA rsa = new RSA();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Input plaintext
        System.out.print("Enter the plain text: ");
        String teststring = reader.readLine();

        // Encrypt
        byte[] encrypted = rsa.encrypt(teststring.getBytes());
        System.out.println("\nEncrypted String: " + new String(encrypted));
        System.out.println("Encrypted String in Bytes: " + bytesToString(encrypted));

        // Decrypt
        byte[] decrypted = rsa.decrypt(encrypted);
        System.out.println("\nDecrypted String: " + new String(decrypted));
        System.out.println("Decrypted String in Bytes: " + bytesToString(decrypted));
    }
}

/*
Enter the plain text: HelloWorld

Encrypted String: 06????Y?+?↨??{l?♠?C?f??
Encrypted String in Bytes: 4854-85-1363-4789-6343-6823-20-16123108-1066-11467-18-97-118102-35-57

Decrypted String: HelloWorld
Decrypted String in Bytes: 7210110810811187111114108100
*/