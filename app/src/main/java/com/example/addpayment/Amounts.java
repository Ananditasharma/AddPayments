package com.example.addpayment;

public class Amounts {
    public String paymentMode;
    public String amount;
    public String provider;
    public String transaction;

    public Amounts() {

    }

    public Amounts(String paymentMode,String amount, String provider, String transaction) {
        this.paymentMode=paymentMode;
        this.amount = amount;
        this.provider = provider;
        this.transaction = transaction;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}
