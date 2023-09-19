package vn.funix.fx22541.asm04.service;

import vn.funix.fx22541.asm04.model.Account;

public interface ITransfer  {
    void transfer(String receivingAccount, double amount);
}
