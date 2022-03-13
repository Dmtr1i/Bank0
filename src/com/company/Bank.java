package com.company;

public class Bank implements Runnable {

    private int numWorkers;
    private float money;
    private Worker[] workers;
    private int customerPeriod;
    private long startTime;
    private long workTime = 60000;
    private int countWorkerStart = -1;

    Bank() {
        money = 500;
        numWorkers = 5;
        workers = new Worker[numWorkers];
        customerPeriod = 3;
        collectWorkers();
    }

    public int getNumWorkers() {
        return numWorkers;
    }

    private void collectWorkers() {
        for (int i = 0; i < workers.length; i++) workers[i] = new Worker(i);
    }

    // Method, that founds worker with smallest queue
    private int getFreeWorkerID() {
        int workerId = 0;
        int workerList = 10000000;
        for (int i = 0; i < workers.length; i++) {
            if (workers[i].getCustCount() == 0) return i;
            if (workers[i].getCustCount() < workerList) {
                workerId = i;
                workerList = workers[i].getCustCount();
            }
        }
        return workerId;
    }

    // Method, that makes operations with money
    private synchronized void workWithMoney(int countWorker) {
        if (workers[countWorker].getCustList()[0].getCustMoney() > 0) {
            money += workers[countWorker].getCustList()[0].getCustMoney();
            System.out.println("Работник №" + countWorker + " выполнил операцию на " + workers[countWorker].getCustList()[0].getCustMoney() + " рублей, остаток в банке: " + money + ", заполняем оставшиеся документы!\n\n");
        }
        else {
            if (money + workers[countWorker].getCustList()[0].getCustMoney() > 0) {
                money += workers[countWorker].getCustList()[0].getCustMoney();
                System.out.println("Работник №" + countWorker + " выполнил операцию на " + workers[countWorker].getCustList()[0].getCustMoney() + " рублей, остаток в банке: " + money + ", заполняем оставшиеся документы!\n\n");
            }
            else System.out.println("В банке не достаточно денег!\n\n");
        }
    }

    private void customerGenerator() {
        while (System.currentTimeMillis() - workTime < startTime) {
            Customer cust = new Customer();
            int workerID = getFreeWorkerID();
            workers[workerID].addCustomer(cust);
            System.out.println("Работнику №" + workerID + " добавлен клиент: сумма операции: " + cust.getCustMoney() + ", время обслуживания: " + cust.getServiceTime() + ", его очередь равна " + workers[workerID].getCustCount() + " человек\n\n");
            try { Thread.sleep(customerPeriod * 1000); } catch (InterruptedException e) { System.out.println("Error in customerGenerator()"); }
        }
        System.out.println("Банк прекращает прием клиентов!");
    }

    private void workWithCustomer(int countWorker) {
        System.out.println("Работник №" + countWorker + " вышел на работу!");
        while (System.currentTimeMillis() - workTime < startTime) {
            if (workers[countWorker].getCustList().length != 0) {
                System.out.println("Работник №" + countWorker + " начал работу с клиентом! Заполняем необходимые документы!\n\n");
                long tempTime = System.currentTimeMillis();
                try {
                    Thread.sleep(workers[countWorker].getCustList()[0].getServiceTime() * 1000);
                } catch (InterruptedException e) {
                    System.out.println("Error in workers[" + countWorker + "]\n\n");
                }
                System.out.println("Работник №" + countWorker + " заполнил необходимые документы, выполняем перевод!\n\n");
                workWithMoney(countWorker);
                System.out.println("Работник №" + countWorker + " прекратил работу с клиентом, время выполнения составило: " + (int)(System.currentTimeMillis() - tempTime) % 1000 + " секунд\n\n");
                workers[countWorker].removeCustomer();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Error in waiting customer\n\n");
            }
        }
        System.out.println("Работник №" + countWorker + " прекратил работу, в скором времени банк закроется!");
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        synchronized (this) {
            if (countWorkerStart == -1) System.out.println("\nНачата работа банка!\n");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Error in start work\n\n");
            }
            countWorkerStart++;
        }
        if (countWorkerStart < numWorkers) workWithCustomer(countWorkerStart);
        else customerGenerator();
    }
}
