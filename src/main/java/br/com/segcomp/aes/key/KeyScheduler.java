package br.com.segcomp.aes.key;

import br.com.segcomp.aes.block.Block;
import br.com.segcomp.aes.key.Key;

public interface KeyScheduler {
    public Block[] schedule(Key key) throws Exception;
}
