package encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by matus.vida on 20. 10. 2015.
 */
public class Encrypt{



    public Encrypt() throws Exception{

    }

    public static void encrypt() throws Exception{
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecretKey key = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            byte[] text = "nieco".getBytes("UTF-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] textEncrypted = cipher.doFinal(text);

            String s = new String(textEncrypted);
            System.out.println(s);


        }catch (Exception e){
                System.out.println("Exception");
        }
    }
}
