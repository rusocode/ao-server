package com.ao.service;

import java.util.BitSet;

public class CharacterIndexManager {

    private static final int MAX_CHARACTERS = 10000;
    private final BitSet usedIndexes = new BitSet(MAX_CHARACTERS + 1);

    public synchronized int assignCharIndex() {
        // Busca el primer indice disponible (empezando desde 1, no 0)
        for (int i = 1; i <= MAX_CHARACTERS; i++) {
            if (!usedIndexes.get(i)) {
                usedIndexes.set(i);
                return i;
            }
        }
        throw new RuntimeException("No CharIndex available!");
    }

    public synchronized void freeCharIndex(int charIndex) {
        if (charIndex > 0 && charIndex <= MAX_CHARACTERS)
            usedIndexes.clear(charIndex);
    }

}