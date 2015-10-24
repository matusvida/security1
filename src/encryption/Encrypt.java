package encryption;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
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
                 outputFileName = "encrypted_" + inputFile.getName();
            }
            else{
                outputFileName = "decrypted_" + inputFile.getName();
            }

            File outputFile = new File(inputFile.getAbsolutePath().substring(0,inputFile.getAbsolutePath().length()-inputFile.getName().length())+outputFileName);

            cipher.init(cipherMode, key);

            System.out.println(key);

            FileInputStream inFile = new FileInputStream(inputFile);
            FileOutputStream outFile = new FileOutputStream(outputFile);
            AlgorithmParameters params = cipher.getParameters();
            FileOutputStream ivOutFile = new FileOutputStream("iv.enc");
            //byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            //ivOutFile.write(iv);
            ivOutFile.close();

            //file encryption
            byte[] input = new byte[64];
            int bytesRead;

            while ((bytesRead = inFile.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null)
                    outFile.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null)
                outFile.write(output);

            inFile.close();
            outFile.flush();
            outFile.close();

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
