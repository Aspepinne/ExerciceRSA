package com.aspepinne;

import java.math.BigInteger;

public class Encryption {

    static String encrypt(String message, KeyPair key) {
        return new BigInteger(message.getBytes()).modPow(key.getKey(), key.getN()).toString();
    }

    static String decrypt(String message, KeyPair key) {
        return new String((new BigInteger(message)).modPow(key.getKey(), key.getN()).toByteArray());
    }
}