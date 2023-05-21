package com.activedge.scrum.report.service;

import com.activedge.scrum.report.data.FinastraData;
import com.activedge.scrum.report.data.MatchAdviceData;
import com.activedge.scrum.report.data.SummaryData;
import com.activedge.scrum.report.util.FinastraExportUtil;
import com.activedge.scrum.report.data.ConsolidatedData;
import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinastraService {
    @Value("${file.report}")
    private String filePath;
    @Value("${file.output}")
    private String outputFile;
    protected XSSFWorkbook wb;
    Workbook wkbk;
    List<MatchAdviceData> newList = new ArrayList<>();
    List<MatchAdviceData> errorList = new ArrayList<>();
    List<SummaryData> datay = new ArrayList<>();
    List<FinastraData> finastraList = new ArrayList<>();
    boolean output;
    String comment = "";
    String errorFilename = "";
    int i = 0;
    List<FinastraData> data = new ArrayList<>();
    List<Double> tb = new LinkedList<Double>();

    public boolean processor() throws IOException {
        final List<ConsolidatedData> consolidatedData = new ArrayList<>();
        Path path = Paths.get(filePath);
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
                    data = Poiji.fromExcel(file, FinastraData.class, options);
                } catch (EmptyFileException e) {
                    System.out.println("File is empty " + e.getLocalizedMessage());
                }

                if (!data.isEmpty()) {
                    FinastraData theader = data.get(0);
                    if (StringUtils.isNotBlank(theader.getTlmCode()) && !theader.getTlmCode().equalsIgnoreCase("TLM CODE")) {
                        System.out.println("NO HEADER");
                        finastraList = processData(data, file);
                    } else {
                        List<FinastraData> datax = new ArrayList<>();
                        System.out.println("There's a HEADER");
                        PoijiOptions optionss = PoijiOptions.PoijiOptionsBuilder.settings().build();
                        try {
                            datax = Poiji.fromExcel(file, FinastraData.class, optionss);
                        } catch (EmptyFileException e) {
                            System.out.println("File is empty " + e.getLocalizedMessage());
                        }
                        finastraList = processData(datax, file);
                    }
                }
                output = finastraReport(finastraList, outputFile + "\\" + today() +"_"+ filename+"_retag.xlsx");
            }
        });

        return output;
    }

    private List<FinastraData> processData(List<FinastraData> data, File file) {
        List<FinastraData> items = new ArrayList<>();
        items = data.stream().map(e->{
            FinastraData finastra = new FinastraData();
            finastra.setItemKey(e.getItemKey());
            finastra.setApplicant(e.getApplicant());
            finastra.setCorrespBank(e.getCorrespBank());
            finastra.setExpirationDate(formatDate(e.getExpirationDate()));
            finastra.setBeneficiary(e.getBeneficiary());
            return finastra;
        }).collect(Collectors.toList());

        return items;
    }


    public String today() {
        LocalDate date = LocalDate.now();
        String today = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        return today;
    }

    public boolean finastraReport(List<FinastraData> items, String fileName) {
        File file = null;

        Workbook workbook = (new FinastraExportUtil())
                .exportExcel(
                        new String[]{"TRAN ID", "APPLICANT", "CENTRE", "EXPIRATION DATE", "BENEFICIARY NAME"},
                        items, 1000000, "Output");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(fileName));
            System.out.println("Writing to file.......");
            workbook.write(fos);
            fos.flush();
            System.out.println("File written successfully!!!!!!!");
            return true;
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
        return false;
    }

    public String formatDate(String today){
        LocalDate ldate = null;
        String formattedDate = "";
        try{
            DateTimeFormatter fmtt = DateTimeFormatter.ofPattern("dd-MMM-yy");
            fmtt = fmtt.withLocale(Locale.US);
            ldate = LocalDate.parse(today, fmtt);
            formattedDate = ldate.format(DateTimeFormatter.BASIC_ISO_DATE);
        }catch(DateTimeParseException e){
            try{
                DateTimeFormatter fmtt = DateTimeFormatter.ofPattern("dd MMM yyyy");
                fmtt = fmtt.withLocale(Locale.US);
                ldate = LocalDate.parse(today, fmtt);
                formattedDate = ldate.format(DateTimeFormatter.BASIC_ISO_DATE);
            }catch(DateTimeParseException ex){
                try{
                    DateTimeFormatter fmtt = DateTimeFormatter.ofPattern("MMM dd yyyy");
                    fmtt = fmtt.withLocale(Locale.US);
                    ldate = LocalDate.parse(today, fmtt);
                    formattedDate = ldate.format(DateTimeFormatter.BASIC_ISO_DATE);
                }catch(DateTimeParseException exc){
                    try{
                        DateTimeFormatter fmtt = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
                        fmtt = fmtt.withLocale(Locale.US);
                        ldate = LocalDate.parse(today, fmtt);
                        formattedDate = ldate.format(DateTimeFormatter.BASIC_ISO_DATE);
                    }catch(DateTimeParseException exce){
                        try{
                            DateTimeFormatter fmtt = DateTimeFormatter.ofPattern("M/dd/yyyy");
                            fmtt = fmtt.withLocale(Locale.US);
                            ldate = LocalDate.parse(today, fmtt);
                            formattedDate = ldate.format(DateTimeFormatter.BASIC_ISO_DATE);
                        }catch(DateTimeParseException exception){
                            System.out.println(exception.getLocalizedMessage());
                        }
                    }
                }
            }
        }

        return formattedDate;
    }

}
