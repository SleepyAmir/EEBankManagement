package com.sleepy.eebankmanagement.model.dto;

import java.util.List;

public class UserDTO {
    public Long id;
    public String username;
    public String email;
    public String fullName;
    public List<AccountDTO> accounts;
    public List<CardDTO> cards;
    public List<LoanDTO> loans;
}