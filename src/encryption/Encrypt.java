package encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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

            inputStream.close();
            outputStream.close();


        }catch (Exception e){
                System.out.println("Exception");
                e.printStackTrace();
        }
    }


}
