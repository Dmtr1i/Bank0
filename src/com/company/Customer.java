package com.company;

public class Customer {

    private int custMoney;
    private int serviceTime;

    Customer() {
        custMoney = (int)(Math.random() * 500 - 250);
        serviceTime = (int)(Math.random() * 10 + 5);
    }

    public int getCustMoney() {
        return custMoney;
    }

    public int getServiceTime() {
        return serviceTime;
    }
}
