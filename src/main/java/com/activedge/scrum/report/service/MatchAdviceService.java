package com.activedge.scrum.report.service;

import com.activedge.scrum.report.config.properties.FileProperties;
import com.activedge.scrum.report.data.ConsolidatedData;
import com.activedge.scrum.report.data.FailedData;
import com.activedge.scrum.report.data.MatchAdviceData;
import com.activedge.scrum.report.data.SummaryData;
import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchAdviceService {
//    @Value("${file.report}")
//    private String filePath;
    @Value("${file.static-data}")
    private String summaryFilePath;
    @Autowired
    private FileProperties fileProperties;
    @Value("${file.output}")
    private String outputFile;
    protected XSSFWorkbook wb;
    Workbook wkbk;
    List<MatchAdviceData> newList = new ArrayList<>();
    List<MatchAdviceData> errorList = new ArrayList<>();
    List<MatchAdviceData> failedList = new ArrayList<>();
    List<MatchAdviceData> consolidatedList = new ArrayList<>();
    List<SummaryData> datay = new ArrayList<>();
    String comment = "";
    String errorFilename = "";
    int i = 0;
    String chkbal = "";
    List<BigDecimal> totalBal = new LinkedList<BigDecimal>();
    BigDecimal total = BigDecimal.ZERO;
    List<MatchAdviceData> data = new ArrayList<>();
    List<Double> tb = new LinkedList<Double>();
    Double db = Double.valueOf(0.00);
    Double totalBalance = 0.00;
    DecimalFormat df = new DecimalFormat("##,###,###.##");

    public boolean processor() throws IOException {

        final List<ConsolidatedData> consolidatedData = new ArrayList<>();
        Path path = Paths.get(fileProperties.getReport());
        File[] ff = path.toFile().listFiles();
        System.out.println(Arrays.stream(ff).count());

        Arrays.asList(ff).stream().forEach(file -> {
            String filename = file.getName();
            String fileNameExtension = FilenameUtils.getExtension(filename);
            if (file.isFile() && file.length() != 0 && fileNameExtension.equalsIgnoreCase("xlsx")) {
                System.out.printf("filename %d :: %s", i++, filename).println();
                FileInputStream inputStream = null;

                PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().headerCount(0).build();
                try {
                    data = Poiji.fromExcel(file, MatchAdviceData.class, options);
                } catch (EmptyFileException e) {
                    System.out.println("File is empty " + e.getLocalizedMessage());
                }

                if (!data.isEmpty()) {
                    MatchAdviceData theader = data.get(0);
                    if (StringUtils.isNotBlank(theader.getTlmCode()) && !theader.getTlmCode().equalsIgnoreCase("TLM CODE")) {
                        System.out.println("NO HEADER");
                        processData(data, file);
                    } else {
                        List<MatchAdviceData> datax = new ArrayList<>();
                        System.out.println("There's a HEADER");
                        PoijiOptions optionss = PoijiOptions.PoijiOptionsBuilder.settings().build();
                        try {
                            datax = Poiji.fromExcel(file, MatchAdviceData.class, optionss);
                        } catch (EmptyFileException e) {
                            System.out.println("File is empty " + e.getLocalizedMessage());
                        }
                        processData(datax, file);
                    }
                }
            }

        });
        if (!consolidatedList.isEmpty()) {
            consolidatedList.stream().forEach(c -> {
                if (StringUtils.isNotBlank(c.getTlmCode()) && !c.getTlmCode().equalsIgnoreCase("TLM CODE")) {
                    System.out.printf("I found a consolidated match...%s::%s::%s",
                            c.getTlmCode().trim(), c.getLedgerAccount(), c.getUser()).println();
                }
            });
        }
        failedList.addAll(errorList);
        if (!failedList.isEmpty()) {
            System.out.println("failed count::" + failedList.size());
            failedList.forEach(c -> System.out.printf("I found an error match...%s::%s",
                    c.getModifiedBy(), c.getFilename()).println());
        }


        List<MatchAdviceData> output = consolidatedList
                .stream()
                .filter(f -> StringUtils.isNotBlank(f.getTlmCode()) && !"TLM CODE".equalsIgnoreCase(f.getTlmCode()) && StringUtils.isNotBlank(f.getLedgerAccount())).collect(Collectors.toList());
        if (!output.isEmpty() || !failedList.isEmpty()) {
            consolidatedReport(output, failedList, outputFile + "\\" + today() + "_Consolidated Match Advice.xlsx");
            return true;
        }
        newList = null;
        errorList = null;
        return false;
    }


    public List<MatchAdviceData> processSummaryFile() {
        Path path = Paths.get(summaryFilePath);
        File[] ff = path.toFile().listFiles();
        Arrays.asList(ff).stream().forEach(file -> {
            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();
            datay = Poiji.fromExcel(file, SummaryData.class, options);
        });

        List<MatchAdviceData> cdata = datay.stream().map(d -> {
            MatchAdviceData cosol = new MatchAdviceData();
            cosol.setModifiedBy(d.getModifiedBy());
            cosol.setModified(d.getModified());
            cosol.setFilename(d.getName());
            return cosol;
        }).collect(Collectors.toList());
        return cdata;
    }

    public String today() {
        LocalDate date = LocalDate.now();
        String today = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        return today;
    }

    public void consolidatedReport(List<MatchAdviceData> items, List<MatchAdviceData> failedItems, String fileName) {
        File file = null;
        List<ConsolidatedData> dataList = items.stream().map(d -> {
            ConsolidatedData data = new ConsolidatedData();
            data.setAccountName(d.getAccountName());
            data.setItemKey(d.getItemKey());
            Number amt = d.getAmountNum();

            if (amt != null) {
                data.setAmount(amt);
            }
            data.setSign(d.getSign());
            data.setComment(d.getUser());
            return data;
        }).collect(Collectors.toList());

        List<FailedData> failedList = failedItems.stream().map(f -> {
            FailedData fdata = new FailedData();
            fdata.setUser(f.getModifiedBy());
            return fdata;
        }).collect(Collectors.toList());

        System.out.println("consolidated items count " + items.size());
        Workbook workbook = null;
//                (new ConsolidatedExportUtil())
//                .exportExcel(
//                        new String[]{"TLM CODE", "OWNER", "ACCT NAME", "LEDGER ACCT", "SOL ID", "CLUSTER", "ITEM KEY", "SIGN", "TYPE", "VAL DATE",
//                                "POSTDATE", "ESCALATION LEVEL", "AGE", "LIFE SPAN", "AGE AFTER LIFE SPAN", "CURRENCY", "AMOUNT", "CHECK BALANCE", "REF 1",
//                                "NARRATION", "REMARKS", "ORIGIN TRAN ID", "ORIGIN USER ID", "COMMENT", "PROBABLE MATCH", "MODIFIED BY"},
//                        new String[]{"ACCOUNT NAME", "ITEM ID", "AMOUNT", "AMOUNT SIGN", "COMMENT"},
//                        new String[]{"MODIFIED BY"},
//                        items, dataList, failedList, 1000000, "Consolidated Output", "Final", "Failed list");

        FileOutputStream fos = null;
        try {
            file = new File(fileName);
            fos = new FileOutputStream(new File(fileName));
            System.out.println("Writing to file.......");
            workbook.write(fos);

            fos.flush();
            System.out.println("File written successfully!!!!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
            }
        }

    }

    public void processData(List<MatchAdviceData> dataItems, File file) {
        System.out.println(dataItems.size() + " line items in file " + file.getName());
        List<BigDecimal> netBalance = new LinkedList<>();
        dataItems.stream().forEach(d ->
        {
            String sign = d.getSign();
            String cb = d.getCheckBalance();
            if (StringUtils.isNotBlank(sign) && sign.contains("D") && StringUtils.isNotBlank(cb) && cb.contains("(")) {
                chkbal = "-" + d.getCheckBalance().replaceAll("[()]", "");
            } else if (StringUtils.isNotBlank(sign)) {
                chkbal = d.getCheckBalance();
            } else {
                chkbal = "";
            }
            if (StringUtils.isNotBlank(chkbal) && chkbal.contains(",")) {
                chkbal = chkbal.replace(",", "");
            }
            if (StringUtils.isNotBlank(chkbal)) {
                BigDecimal total = BigDecimal.ZERO;
                try {
                    total = BigDecimal.valueOf(Double.parseDouble(chkbal));
                } catch (NumberFormatException e) {
                    System.out.println("Error check balance " + e.getLocalizedMessage() + " in file " + file.getName() + " ");
                    total = null;
                }
                if (total != null) {
                    netBalance.add(total);
                }
            }
        });
        BigDecimal netTotal = BigDecimal.ZERO;
        if (!netBalance.isEmpty()) {
            try {
                netTotal = netBalance.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                System.out.println("balance " + netTotal);
            } catch (NumberFormatException e) {
                System.out.println("Error net balance in file " + file + " " + e.getLocalizedMessage());
            }

            Double chh = netTotal.doubleValue();
            if (chh.equals(0.0)) {
                newList = new ArrayList<>();
                System.out.println("process summary file..");
                List<MatchAdviceData> pdata = processSummaryFile();
                pdata.stream().forEach(p -> {
                    if (p.getFilename().equalsIgnoreCase(file.getName())) {
                        comment = p.getModifiedBy().concat("-").concat(p.getModified());
                        System.out.println(comment);
                    }
                });
                //filter data containing header
                List<MatchAdviceData> filteredList = data
                        .stream()
                        .filter(f -> !"TLM CODE".equalsIgnoreCase(f.getTlmCode())).collect(Collectors.toList());
                newList = filteredList.stream().map(d -> {
                    MatchAdviceData consol = new MatchAdviceData();
                    consol.setUser(comment);
                    consol.setTlmCode(d.getTlmCode());
                    consol.setAccountName(d.getAccountName());
                    consol.setLedgerAccount(d.getLedgerAccount());
                    consol.setSolId(d.getSolId());
                    consol.setCluster(d.getCluster());
                    consol.setItemKey(d.getItemKey());
                    consol.setSign(d.getSign());
                    consol.setType(d.getType());
                    consol.setValueDate(d.getValueDate());
                    consol.setPostDate(d.getPostDate());
                    consol.setEscalationLevel(d.getEscalationLevel());
                    consol.setAge(d.getAge());
                    consol.setLifespan(d.getLifespan());
                    consol.setAgeAfterLifespan(d.getAgeAfterLifespan());
                    consol.setCurrency(d.getCurrency());
                    Number amt = null;
                    String amount = d.getAmount();
                    String signedAmount = "";

                    if (StringUtils.isNotBlank(amount) && amount.contains("(")) {
                        signedAmount = "-" + amount.replaceAll("[()]", "");
                    } else {
                        signedAmount = amount;
                    }
                    if (StringUtils.isNotBlank(signedAmount) && signedAmount.contains(",")) {
                        signedAmount = signedAmount.replace(",", "");
                    }
                    try {
                        if (StringUtils.isNotBlank(signedAmount)) {
                            amt = df.parse(signedAmount);
                            consol.setAmountNum(amt);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String sign = d.getSign();
                    String checkBal = "";
                    String cb = d.getCheckBalance();
                    if (StringUtils.isNotBlank(sign) && sign.contains("D") && StringUtils.isNotBlank(cb) && cb.contains("(")) {
                        checkBal = "-" + cb.replaceAll("[()]", "");
                    } else {
                        checkBal = cb;
                    }
                    if (StringUtils.isNotBlank(checkBal) && checkBal.contains(",")) {
                        checkBal = checkBal.replace(",", "");
                    }
                    Number chkAmt = null;
                    try {
                        if (StringUtils.isNotBlank(checkBal)) {
                            chkAmt = df.parse(checkBal);
                        }
                    } catch (ParseException e) {
                        System.out.println("Failed: error check balance " + e.getLocalizedMessage() + " in file " + file.getName());
                    }
                    consol.setChkBal(chkAmt);
                    consol.setRef1(d.getRef1());
                    consol.setNarration(d.getNarration());
                    consol.setRemarks(d.getRemarks());
                    consol.setOriginTranId(d.getOriginTranId());
                    consol.setOriginUserId(d.getOriginUserId());
                    consol.setComment(d.getComment());
                    consol.setProbableMatch(d.getProbableMatch());
                    consol.setOwner(d.getOwner());
                    return consol;
                }).collect(Collectors.toList());
//            newList.forEach(c -> System.out.printf("I found a match...%s::%s::%s",
//                    c.getTlmCode(), c.getLedgerAccount(), c.getComment()).println());
                consolidatedList.addAll(newList);
            } else {
                failedItems(file);
            }
        }else {
            failedItems(file);
        }
    }

    private void failedItems(File file) {
        final String[] user = {""};
        System.out.println("Error in check balance");
        List<MatchAdviceData> pData = processSummaryFile();
        pData.stream().forEach(p -> {
            if (p.getFilename().equalsIgnoreCase(file.getName())) {
                user[0] = p.getModifiedBy();
                System.out.println("error::" + user[0]);
                errorList.add(p);

            }
        });
    }
}
