package com.company;

public class Worker {

    private int serviceTime;
    private Customer[] custList;
    private int ID;

    Worker(int id) {
        serviceTime = 5;
        custList = new Customer[0];
        ID = id;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public Customer[] getCustList() { return custList; }

    public int getCustCount() {
        return custList.length;
    }

    public void addCustomer(Customer cust) {
        Customer[] temp = new Customer[custList.length + 1];
        for (int i = 0; i < custList.length; i++) temp[i] = custList[i];
        temp[temp.length - 1] = cust;
        custList = temp;
    }

    public void removeCustomer() {
        if (custList.length > 0) {
            if (custList.length != 1) {
                Customer[] temp = new Customer[custList.length - 1];
                for (int i = 1; i < temp.length + 1; i++) temp[i - 1] = custList[i];
                custList = temp;
            } else custList = new Customer[0];
        }
    }
}
