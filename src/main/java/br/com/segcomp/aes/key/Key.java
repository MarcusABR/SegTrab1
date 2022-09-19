package br.com.segcomp.aes.key;

import br.com.segcomp.aes.block.Block;

import java.security.SecureRandom;
    /** Test keys:
     *
     *  For the key 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00, the expanded key is:
     *
     * 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
     * 62 63 63 63 62 63 63 63 62 63 63 63 62 63 63 63
     * 9b 98 98 c9 f9 fb fb aa 9b 98 98 c9 f9 fb fb aa
     * 90 97 34 50 69 6c cf fa f2 f4 57 33 0b 0f ac 99
     * ee 06 da 7b 87 6a 15 81 75 9e 42 b2 7e 91 ee 2b
     * 7f 2e 2b 88 f8 44 3e 09 8d da 7c bb f3 4b 92 90
     * ec 61 4b 85 14 25 75 8c 99 ff 09 37 6a b4 9b a7
     * 21 75 17 87 35 50 62 0b ac af 6b 3c c6 1b f0 9b
     * 0e f9 03 33 3b a9 61 38 97 06 0a 04 51 1d fa 9f
     * b1 d4 d8 e2 8a 7d b9 da 1d 7b b3 de 4c 66 49 41
     * b4 ef 5b cb 3e 92 e2 11 23 e9 51 cf 6f 8f 18 8e
     *
     * For the key ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff, the expanded key is:
     *
     * ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff
     * e8 e9 e9 e9 17 16 16 16 e8 e9 e9 e9 17 16 16 16
     * ad ae ae 19 ba b8 b8 0f 52 51 51 e6 45 47 47 f0
     * 09 0e 22 77 b3 b6 9a 78 e1 e7 cb 9e a4 a0 8c 6e
     * e1 6a bd 3e 52 dc 27 46 b3 3b ec d8 17 9b 60 b6
     * e5 ba f3 ce b7 66 d4 88 04 5d 38 50 13 c6 58 e6
     * 71 d0 7d b3 c6 b6 a9 3b c2 eb 91 6b d1 2d c9 8d
     * e9 0d 20 8d 2f bb 89 b6 ed 50 18 dd 3c 7d d1 50
     * 96 33 73 66 b9 88 fa d0 54 d8 e2 0d 68 a5 33 5d
     * 8b f0 3f 23 32 78 c5 f3 66 a0 27 fe 0e 05 14 a3
     * d6 0a 35 88 e4 72 f0 7b 82 d2 d7 85 8c d7 c3 26
     *
     * For the key 00 01 02 03 04 05 06 07 08 09 0a 0b 0c 0d 0e 0f, the expanded key is:
     *
     * 00 01 02 03 04 05 06 07 08 09 0a 0b 0c 0d 0e 0f
     * d6 aa 74 fd d2 af 72 fa da a6 78 f1 d6 ab 76 fe
     * b6 92 cf 0b 64 3d bd f1 be 9b c5 00 68 30 b3 fe
     * b6 ff 74 4e d2 c2 c9 bf 6c 59 0c bf 04 69 bf 41
     * 47 f7 f7 bc 95 35 3e 03 f9 6c 32 bc fd 05 8d fd
     * 3c aa a3 e8 a9 9f 9d eb 50 f3 af 57 ad f6 22 aa
     * 5e 39 0f 7d f7 a6 92 96 a7 55 3d c1 0a a3 1f 6b
     * 14 f9 70 1a e3 5f e2 8c 44 0a df 4d 4e a9 c0 26
     * 47 43 87 35 a4 1c 65 b9 e0 16 ba f4 ae bf 7a d2
     * 54 99 32 d1 f0 85 57 68 10 93 ed 9c be 2c 97 4e
     * 13 11 1d 7f e3 94 4a 17 f3 07 a7 8b 4d 2b 30 c5
     *
     * For the key 49 20 e2 99 a5 20 52 61 64 69 6f 47 61 74 75 6e, the expanded key is:
     *
     * 49 20 e2 99 a5 20 52 61 64 69 6f 47 61 74 75 6e
     * da bd 7d 76 7f 9d 2f 17 1b f4 40 50 7a 80 35 3e
     * 15 2b cf ac 6a b6 e0 bb 71 42 a0 eb 0b c2 95 d5
     * 34 01 cc 87 5e b7 2c 3c 2f f5 8c d7 24 37 19 02
     * a6 d5 bb b1 f8 62 97 8d d7 97 1b 5a f3 a0 02 58
     * 56 a2 d1 bc ae c0 46 31 79 57 5d 6b 8a f7 5f 33
     * 1e 6d 12 c2 b0 ad 54 f3 c9 fa 09 98 43 0d 56 ab
     * 89 dc 70 d8 39 71 24 2b f0 8b 2d b3 b3 86 7b 18
     * 4d fd dd b5 74 8c f9 9e 84 07 d4 2d 37 81 af 35
     * 5a 84 4b 2f 2e 08 b2 b1 aa 0f 66 9c 9d 8e c9 a9
     * 75 59 98 71 5b 51 2a c0 f1 5e 4c 5c 6c d0 85 f5
    **/

public class Key {

    private Block key;


    public Key(int keyLength){

        byte[] bytes = {(byte) 0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        this.key = new Block(bytes);
        //this.key = new Block(new byte[keyLength]);
        //SecureRandom sr = new SecureRandom();
        //sr.nextBytes(key.getBlock());
    }

    public Block getKey() {
        return key;
    }

    public void setKey(Block key) {
        this.key = key;
    }
}
