package br.com.segcomp.rsa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MGF1 {
    private final MessageDigest DIGEST;

    public MGF1(MessageDigest DIGEST) {
        this.DIGEST = DIGEST;
    }

    public String turnToHexcode(byte[] base){
        try {
            StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < base.length; i++) {
				String hex = Integer.toHexString(0xff & base[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    public byte[] generateMask(byte[] mgfSeed, int maskLen)  {
        int hashCount = (maskLen + this.DIGEST.getDigestLength() - 1)
                        / this.DIGEST.getDigestLength();
    
        byte[] mask = new byte[0];


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        for (int i=0;i<hashCount;i++) {
            this.DIGEST.update(mgfSeed);
            this.DIGEST.update(new byte[3]);
            this.DIGEST.update((byte)i);
            byte[] hash = this.DIGEST.digest();
            try {
                outputStream.write(hash);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    
        mask = outputStream.toByteArray();
        byte[] output = new byte[maskLen];
        System.arraycopy(mask, 0, output, 0, output.length);
        return output;
      }

      public byte[] generateMaska(byte[] mgfSeed, int maskLen) {
        int hashCount = (maskLen + this.DIGEST.getDigestLength() - 1)
                        / this.DIGEST.getDigestLength();
    
        byte[] mask;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
    
        for (int i=0;i<hashCount;i++) {
          this.DIGEST.update(mgfSeed);
          this.DIGEST.update(new byte[3]);
          this.DIGEST.update((byte)i);
          byte[] hash = this.DIGEST.digest();
    
            try {
                outputStream.write(hash);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mask =outputStream.toByteArray();
    
        byte[] output = new byte[maskLen];
        System.arraycopy(mask, 0, output, 0, output.length);
        return output;
      }

    
}
