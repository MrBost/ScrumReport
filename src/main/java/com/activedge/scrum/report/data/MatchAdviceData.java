package com.activedge.scrum.report.data;

import com.poiji.annotation.ExcelCell;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MatchAdviceData {
    @ExcelCell(0)
    private String tlmCode;
    @ExcelCell(1)
    private String owner;
    @ExcelCell(2)
    private String accountName;
    @ExcelCell(3)
    private String ledgerAccount;
    @ExcelCell(4)
    private int solId;
    @ExcelCell(5)
    private String cluster;
    @ExcelCell(6)
    private String itemKey;
    @ExcelCell(7)
    private String sign;
    @ExcelCell(8)
    private String type;
    @ExcelCell(9)
    private String valueDate;
    @ExcelCell(10)
    private String postDate;
    @ExcelCell(11)
    private String escalationLevel;
    @ExcelCell(12)
    private int age;
    @ExcelCell(13)
    private int lifespan;
    @ExcelCell(14)
    private int ageAfterLifespan;
    @ExcelCell(15)
    private String currency;
    @ExcelCell(16)
    private String amount;
    @ExcelCell(17)
    private String checkBalance;
    @ExcelCell(18)
    private String ref1;
    @ExcelCell(19)
    private String narration;
    @ExcelCell(20)
    private String remarks;
    @ExcelCell(21)
    private String originTranId;
    @ExcelCell(22)
    private String originUserId;
    @ExcelCell(23)
    private String comment;
    @ExcelCell(24)
    private String probableMatch;
    private String modifiedBy;
    private String modified;
    private String filename;
    private Number chkBal;
    private String user;
    private Number amountNum;
}
