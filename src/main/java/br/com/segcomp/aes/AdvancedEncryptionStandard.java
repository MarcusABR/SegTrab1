package br.com.segcomp.aes;

import br.com.segcomp.aes.block.Block;
import br.com.segcomp.aes.enums.KeyLength;
import br.com.segcomp.aes.key.Key;

abstract class AdvancedEncryptionStandard {

    KeyLength keyLength;
    Key key;
    Block[] expandedKey;

    abstract void encrypt(Block block);

    abstract void decrypt(Block block);
}