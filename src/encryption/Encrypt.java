package encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;

/**
 * Created by matus.vida on 20. 10. 2015.
 */
public class Encrypt{

    public Encrypt() throws Exception{

    }

    public static void encrypt(int cipherMode, File inputFile, SecretKey key) throws Exception{

        try {
            String outputFileName;
            Cipher cipher = Cipher.getInstance("AES");
            if(cipherMode == Cipher.ENCRYPT_MODE) {
                 outputFileName = inputFile.getAbsolutePath().
                        substring(0, inputFile.getName().length() -
                                inputFile.getName().length()) + "ecrypted_" + inputFile.getName();
            }
            else{
                outputFileName = inputFile.getAbsolutePath().
                        substring(0, inputFile.getName().length() -
                                inputFile.getName().length()) + "decrypted_" + inputFile.getName();
            }

            File outputFile = new File(outputFileName);
            System.out.println(inputFile.getAbsolutePath());

            cipher.init(cipherMode, key);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

//            MessageDigest md = MessageDigest.getInstance("MD5");
//            try (InputStream is = Files.newInputStream(Paths.get(inputFile.getAbsolutePath()))){
//                DigestInputStream dis = new DigestInputStream(is, md);
//            }
//
//            byte[] digest = md.digest();
//
//            StringBuffer sb = new StringBuffer("");
//            for (int i = 0; i < digest.length; i++) {
//                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
//            }
//
//                System.out.println(sb.toString()+inputFile.getName());

            inputStream.close();
            outputStream.close();


        }catch (Exception e){
                System.out.println("Exception");
                e.printStackTrace();
        }
    }

    public static String hashComparator(File inputFile) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(Paths.get(inputFile.getAbsolutePath()))) {
            DigestInputStream dis = new DigestInputStream(is, md);
        }

        byte[] digest = md.digest();

        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println(sb.toString() + inputFile.getName());

        return sb.toString();
    }

}
