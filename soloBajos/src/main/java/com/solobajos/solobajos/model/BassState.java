package com.solobajos.solobajos.model;

public enum BassState {
    NEW, USED;

    public static boolean contains(String text) {
        try {
            BassState.valueOf(text);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}