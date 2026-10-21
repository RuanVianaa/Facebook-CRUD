
package model.utils;

public class GerarHash {
    public static void main(String[] args) {
        String hash = PasswordEncryptor.hashPassword("1234");
        System.out.println(hash);
    }
}
