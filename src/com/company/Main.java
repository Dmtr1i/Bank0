package com.company;

public class Main {

    public static void main(String[] args) {
        Bank wtb = new Bank();
        for (int i = 0; i < wtb.getNumWorkers() + 1; i++) {
            Thread a = new Thread(wtb);
            a.start();
        }
    }
}
