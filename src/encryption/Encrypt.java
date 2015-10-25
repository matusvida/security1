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

    public static File encrypt(int cipherMode, File inputFile, SecretKey key) throws Exception{


            String outputFileName;
            Cipher cipher = Cipher.getInstance("AES");
            if(cipherMode == Cipher.ENCRYPT_MODE) {
                 outputFileName = "encrypted_" + inputFile.getName();
            }
            else{
                outputFileName = "decrypted_" + inputFile.getName();
            }

            File outputFile = new File(inputFile.getAbsolutePath().substring(0,inputFile.getAbsolutePath().length()-inputFile.getName().length())+outputFileName);
        try {
            cipher.init(cipherMode, key);

            FileInputStream inFile = new FileInputStream(inputFile);
            FileOutputStream outFile = new FileOutputStream(outputFile);
            AlgorithmParameters params = cipher.getParameters();

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
        return outputFile;
    }

    public static String hashComparator(File file) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        System.out.println(file.getAbsolutePath()+"<<<<<<<");
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());

        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };
        byte[] mdbytes = md.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<mdbytes.length;i++) {
            String hex=Integer.toHexString(0xff & mdbytes[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
