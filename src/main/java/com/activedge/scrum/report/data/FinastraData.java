package com.activedge.scrum.report.data;

import com.poiji.annotation.ExcelCell;
import lombok.Data;

@Data
public class FinastraData {
    @ExcelCell(0)
    private String tlmCode;
    @ExcelCell(1)
    private String accountName;
    @ExcelCell(2)
    private String ledgerAccount;
    @ExcelCell(3)
    private String itemKey;
    @ExcelCell(4)
    private String sign;
    @ExcelCell(5)
    private Long type;
    @ExcelCell(6)
    private String valueDate;
    @ExcelCell(7)
    private String postDate;
    @ExcelCell(8)
    private String escalationLevel;
    @ExcelCell(9)
    private int age;
    @ExcelCell(10)
    private int lifespan;
    @ExcelCell(11)
    private int ageAfterLifespan;
    @ExcelCell(12)
    private String currency;
    @ExcelCell(13)
    private String amount;
    @ExcelCell(14)
    private String checkBalance;
    @ExcelCell(15)
    private String ref1;
    @ExcelCell(16)
    private String narration;
    @ExcelCell(17)
    private String applicant;
    @ExcelCell(18)
    private String correspBank;
    @ExcelCell(19)
    private String beneficiary;
    @ExcelCell(20)
    private String expirationDate;
    @ExcelCell(21)
    private String originTranId;
    @ExcelCell(22)
    private String originUserId;
    @ExcelCell(23)
    private String owner;
    @ExcelCell(24)
    private String comment;
}
