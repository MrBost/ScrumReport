//package com.activedge.matchadvice.service;
//
//import com.activedge.matchadvice.data.MatchAdviceData;
//import com.activedge.matchadvice.data.SummaryData;
//import com.poiji.bind.Poiji;
//import com.poiji.option.PoijiOptions;
//import org.apache.camel.Exchange;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.math.BigDecimal;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class MatchAdviceProcessor {
//
//    @Value("${summary.path}")
//    private String summaryFilePath;
//    List<MatchAdviceData> data = new ArrayList<>();
//    List<SummaryData> sData = new ArrayList<>();
//    List<MatchAdviceData> consolidatedList = new ArrayList<>();
//    List<MatchAdviceData> listt = new ArrayList<>();
////    List<BigDecimal> netBalance = new LinkedList<>();
////    BigDecimal netTotal = BigDecimal.ZERO;
//    Double db = Double.valueOf(0.00);
//    List<Double> tb = new LinkedList<Double>();
//    String chkBal;
//    String comment;
//    List<MatchAdviceData> dataList = new ArrayList<>();
//    List<MatchAdviceData> errorList = new ArrayList<>();
//
//    public void processor(Exchange exchange) {
//        File file = exchange.getIn().getBody(File.class);
//
//            System.out.printf("filename %s", file.getName()).println();
//            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().headerCount(0).build();
//            data = Poiji.fromExcel(file, MatchAdviceData.class, options);
//            MatchAdviceData tHeader = data.get(0);
//            if (!tHeader.getTlmCode().equalsIgnoreCase("TLM CODE")) {
//                List<BigDecimal> netBalance = new LinkedList<>();
//                List<Double> totalbal = new LinkedList<>();
//                System.out.println("NO HEADER");
//                data.stream().forEach(d ->
//                {
//                    String sign = d.getSign();
//                    if (sign.contains("D")) {
//                        chkBal = "-" + d.getCheckBalance().replaceAll("[()]", "");
//                    } else {
//                        chkBal = d.getCheckBalance();
//                    }
//                    if (chkBal.contains(",")) {
//                        chkBal = chkBal.replace(",", "");
//                    }
//
////                Double db = Double.parseDouble(chkBal);
////                totalbal.add(db);
//                    BigDecimal total = BigDecimal.valueOf(Double.parseDouble(chkBal));
//                    netBalance.add(total);
//
//                    System.out.printf("%s :: %d :: %s",
//                            d.getAccountName(), d.getItemKey(), d.getCheckBalance()).println();
//                });
////            Double netTotal = tb.stream().mapToDouble(Double::doubleValue).sum();
//                BigDecimal netTotal = netBalance.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
//                System.out.println("no header balance " + netTotal );
//                System.out.println("check " + netTotal.doubleValue());
//                Double chh = netTotal.doubleValue();
//
//                if (chh.equals(0.0)) {
//                    System.out.println("processing summary file..");
//                    List<MatchAdviceData> pData = processSummaryFile(data);
//                    pData.stream().forEach(p -> {
//                        if (p.getFilename().equalsIgnoreCase(file.getName())) {
////                            System.out.printf("I found a match...%s::%s::%s",p.getFilename(),p.getModifiedBy(),p.getModified()).println();
//                            comment = p.getModifiedBy().concat("-").concat(p.getModified());
//                            System.out.println(comment);
//                        }
//                    });
//                    dataList = data.stream().map(d -> {
//                        MatchAdviceData consol = new MatchAdviceData();
//                        consol.setComment(comment);
//                        consol.setTlmCode(d.getTlmCode());
//                        consol.setAccountName(d.getAccountName());
//                        consol.setLedgerAccount(d.getLedgerAccount());
//                        consol.setItemKey(d.getItemKey());
//                        consol.setSign(d.getSign());
//                        consol.setAmount(d.getAmount());
//                        return consol;
//                    }).collect(Collectors.toList());
//                    dataList.forEach(c -> System.out.printf("I found a match...%s::%s::%s",
//                            c.getTlmCode(), c.getLedgerAccount(), c.getComment()).println());
////                    consolidatedReport(consolidatedData,filePath+"/Consolidated Output.xlsx", comment);
//                } else {
//                    System.out.println("Error in check balance");
//                    List<MatchAdviceData> pData = processSummaryFile(data);
//                    pData.stream().forEach(p -> {
//                        if (p.getFilename().equalsIgnoreCase(file.getName())) {
//                            comment = p.getModifiedBy();
//                            System.out.println(comment);
//                            errorList.add(p);
//
//                        }
//                    });
//                    errorList.forEach(c -> System.out.printf("I found an error match...%s::%s",
//                            c.getModifiedBy(), c.getFilename()).println());
//                }
//                consolidatedList.addAll(dataList);
////                if (!consolidatedList.isEmpty()) {
////                    consolidatedList.forEach(c -> System.out.printf("I found a consolidated match...%s::%s::%s",
////                            c.getTlmCode().trim(), c.getLedgerAccount(), c.getComment()).println());
////                }
//            } else {
//                List<BigDecimal> nb = new LinkedList<>();
//                List<Double> totalBal = new LinkedList<>();
//                System.out.println("There's HEADER");
//                PoijiOptions optionss = PoijiOptions.PoijiOptionsBuilder.settings().build();
//                List<MatchAdviceData> datax = Poiji.fromExcel(file, MatchAdviceData.class, optionss);
//                datax.forEach(d ->
//                {
//                    String sign = d.getSign();
//                    if (sign.contains("D")) {
//                        chkBal = "-" + d.getCheckBalance().replaceAll("[()]", "");
//                    } else {
//                        chkBal = d.getCheckBalance();
//                    }
//                    if (chkBal.contains(",")) {
//                        chkBal = chkBal.replace(",", "");
//                    }
////                Double db = Double.parseDouble(chkBal);
////                totalBal.add(db);
//                    BigDecimal total = BigDecimal.valueOf(Double.parseDouble(chkBal));
//                    nb.add(total);
//                    System.out.printf("%s :: %d :: %s",
//                            d.getAccountName(), d.getItemKey(), d.getCheckBalance()).println();
//
//                });
////            Double nt = tb.stream().mapToDouble(Double::doubleValue).sum();
//                BigDecimal nt = nb.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
//                System.out.println("header balance " + nt);
//                Double chh = nt.doubleValue();
//                if (chh.equals(0.0)) {
//                    System.out.println("processing summary file..");
//                    List<MatchAdviceData> pData = processSummaryFile(data);
//                    pData.stream().forEach(p -> {
//                        if (p.getFilename().equalsIgnoreCase(file.getName())) {
////                            System.out.printf("I found a match...%s::%s::%s",p.getFilename(),p.getModifiedBy(),p.getModified()).println();
//                            comment = p.getModifiedBy().concat("-").concat(p.getModified());
//                            System.out.println(comment);
//                        }
//                    });
//                    dataList = data.stream().map(d -> {
//                        MatchAdviceData consol = new MatchAdviceData();
//                        consol.setComment(comment);
//                        consol.setTlmCode(d.getTlmCode());
//                        consol.setAccountName(d.getAccountName());
//                        consol.setLedgerAccount(d.getLedgerAccount());
//                        consol.setItemKey(d.getItemKey());
//                        consol.setSign(d.getSign());
//                        consol.setAmount(d.getAmount());
//                        return consol;
//                    }).collect(Collectors.toList());
//                    dataList.forEach(c -> System.out.printf("I found a match...%s::%s::%s",
//                            c.getTlmCode(), c.getLedgerAccount(), c.getComment()).println());
////                    consolidatedReport(consolidatedData,filePath+"/Consolidated Output.xlsx", comment);
//                } else {
//                    System.out.println("Error in check balance");
//                    List<MatchAdviceData> pData = processSummaryFile(data);
//                    pData.stream().forEach(p -> {
//                        if (p.getFilename().equalsIgnoreCase(file.getName())) {
//                            comment = p.getModifiedBy();
//                            System.out.println(comment);
//                            errorList.add(p);
//
//                        }
//                    });
//                    errorList.forEach(c -> System.out.printf("I found an error match...%s::%s",
//                            c.getModifiedBy(), c.getFilename()).println());
//                }
//                consolidatedList.addAll(dataList);
////                if (!consolidatedList.isEmpty()) {
////                    consolidatedList.forEach(c -> System.out.printf("I found a consolidated match...%s::%s::%s",
////                            c.getTlmCode().trim(), c.getLedgerAccount(), c.getComment()).println());
////                }
//            }
//        if (!consolidatedList.isEmpty()) {
//            consolidatedList.forEach(c -> System.out.printf("I found a consolidated match...%s::%s::%s",
//                    c.getTlmCode().trim(), c.getLedgerAccount(), c.getComment()).println());
//        }
//
//    }
//
////    public List<MatchAdviceData> checkBalance(BigDecimal netBalance, File file){
////        if(netBalance.equals(0)){
////            System.out.println("processing summary file..");
////            List<MatchAdviceData> pData = processSummaryFile(data);
////            pData.stream().forEach(p -> {
////                if (p.getFilename().equalsIgnoreCase(file.getName())) {
//////                            System.out.printf("I found a match...%s::%s::%s",p.getFilename(),p.getModifiedBy(),p.getModified()).println();
////                    comment = p.getModifiedBy().concat("-").concat(p.getModified());
////                    System.out.println(comment);
////                }
////            });
////            dataList = data.stream().map(d -> {
////                MatchAdviceData consol = new MatchAdviceData();
////                consol.setComment(comment);
////                consol.setTlmCode(d.getTlmCode());
////                consol.setAccountName(d.getAccountName());
////                consol.setLedgerAccount(d.getLedgerAccount());
////                consol.setItemKey(d.getItemKey());
////                consol.setSign(d.getSign());
////                consol.setAmount(d.getAmount());
////                return consol;
////            }).collect(Collectors.toList());
////            dataList.forEach(c->System.out.printf("I found a match...%s::%s::%s",
////                    c.getTlmCode(),c.getLedgerAccount(),c.getComment()).println());
//////                    consolidatedReport(consolidatedData,filePath+"/Consolidated Output.xlsx", comment);
////            return dataList;
////        }else{
////            System.out.println("Error in check balance");
////            List<MatchAdviceData> pData = processSummaryFile(data);
////            pData.stream().forEach(p -> {
////                if (p.getFilename().equalsIgnoreCase(file.getName())) {
////                    comment = p.getModifiedBy();
////                    System.out.println(comment);
////                    errorList.add(p);
////
////                }
////            });
////            errorList.forEach(c->System.out.printf("I found an error match...%s::%s",
////                    c.getModifiedBy(),c.getFilename()).println());
////            return errorList;
////        }
////
////    }
//
//    public List<MatchAdviceData> processSummaryFile(List<MatchAdviceData> cdata){
//        Path path = Paths.get(summaryFilePath);
//        File[] ff = path.toFile().listFiles();
//
//
//        Arrays.asList(ff).stream().forEach(file->{
//            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();
//            sData = Poiji.fromExcel(file, SummaryData.class, options);
////            data.stream().forEach(d->System.out.printf("%s::%s::%s",d.getName(),d.getModifiedBy(),d.getModified()).println());
//        });
//
//        cdata = sData.stream().map(d->{
//            MatchAdviceData consol = new MatchAdviceData();
//            consol.setModifiedBy(d.getModifiedBy());
//            consol.setModified(d.getModified());
//            consol.setFilename(d.getName());
//            return consol;
//        }).collect(Collectors.toList());
////        cdata.forEach(a-> System.out.println(a.getModifiedBy()));
//        return cdata;
//    }
//
//}
