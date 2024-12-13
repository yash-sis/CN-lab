import java.math.BigInteger;
import java.util.Random;
import java.io.*;

public class RSA {
    private BigInteger p, q, N, phi, e, d;
    private final int bitlength = 100;

    public RSA() {
        Random r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength / 2, r);

        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0) e = e.add(BigInteger.ONE);
        d = e.modInverse(phi);
    }

    public byte[] encrypt(byte[] message) {
        return new BigInteger(message).modPow(e, N).toByteArray();
    }

    public byte[] decrypt(byte[] message) {
        return new BigInteger(message).modPow(d, N).toByteArray();
    }

    public static void main(String[] args) throws IOException {
        RSA rsa = new RSA();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the plain text: ");
        String input = reader.readLine();

        byte[] encrypted = rsa.encrypt(input.getBytes());
        System.out.println("\nEncrypted: " + new String(encrypted));
        System.out.println("Decrypted: " + new String(rsa.decrypt(encrypted)));
    }
}
