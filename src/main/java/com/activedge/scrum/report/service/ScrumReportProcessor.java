package com.activedge.scrum.report.service;

import com.activedge.scrum.report.config.properties.FileProperties;
import com.activedge.scrum.report.data.*;
import com.activedge.scrum.report.util.OutputExportUtil;
import com.activedge.scrum.report.util.ScrumOutputExportUtil;
import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.apache.commons.lang3.math.NumberUtils.max;

@Slf4j
@Service
public class ScrumReportProcessor {
    @Autowired
    private FileProperties fileProperties;
    List<SquadData> squadData = new ArrayList<>();
    @Value("${offset.days}")
    private int offsetDays;
    @Value("${pv.value}")
            private int pv;
    List<ReportData> reportDataList = new ArrayList<>();
    List<OutputData> items = new ArrayList<>();
    List<OutputData> spiItems = new ArrayList<>();
    BigDecimal spiAverage = BigDecimal.ZERO;
    List<SpiData> spiData = new ArrayList<>();
    List<SpiData> spiList = new ArrayList<>();
    BigDecimal bal = BigDecimal.ZERO;
    BigDecimal spiSquad = BigDecimal.ZERO;
    SimpleDateFormat original = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat target = new SimpleDateFormat("dd-MMM-yyyy");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dtfc = DateTimeFormatter.ofPattern("MM/d/yyyy");
    DateTimeFormatter dtfm = DateTimeFormatter.ofPattern("M/d/yyyy");
    DateTimeFormatter dtfd = DateTimeFormatter.ofPattern("M/dd/yyyy");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/MM/yy");
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MMM-yy");
    DateTimeFormatter dfA = DateTimeFormatter.ofPattern("d-MMM-yy");
    List<OutputDataDTO> outputData = new ArrayList<>();
    List<StateData> stateData = new ArrayList<>();
    List<DodData> dodData = new ArrayList<>();
    DecimalFormat formatter = new DecimalFormat("0.0");
    DecimalFormat spiFormatter = new DecimalFormat("0.00");

    public void squadDataProcessor() throws IOException {
        AtomicReference<List<SquadData>> squad = new AtomicReference<>();
        Path path = Paths.get(fileProperties.getStaticData());
        File[] ff = path.toFile().listFiles();
        Arrays.asList(ff).stream().forEach(file -> {
            String filename = file.getName();
            String fileNameExtension = FilenameUtils.getExtension(filename);
            log.info("filename is: {}", filename);
            if (file.isFile() && file.length() != 0 && fileNameExtension.equalsIgnoreCase("xlsx")) {
                PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(0).build();
                try {
                    squad.set(Poiji.fromExcel(file, SquadData.class, options));
                } catch (EmptyFileException e) {
                    log.info("File is empty: {}", e.getLocalizedMessage());
                }
                log.info("static data count: {}", squad.get().size());
            }
        });
        List<ReportData> reportData = reportDataProcessor();
        List<StateData> stateData = stateDataProcessor();
        List<DodData> dodData = dodDataProcessor();
        processor(squad.get(),reportData,stateData ,dodData);
    }

    public List<StateData> stateDataProcessor() throws IOException {
        Path path = Paths.get(fileProperties.getStaticData());
        File[] ff = path.toFile().listFiles();
        Arrays.asList(ff).stream().forEach(file -> {
            String filename = file.getName();
            String fileNameExtension = FilenameUtils.getExtension(filename);
            log.info("filename is: {}", filename);
            if (file.isFile() && file.length() != 0 && filename.equalsIgnoreCase("Static Data.xlsx")) {
                PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(1).build();
                try {
                    stateData = Poiji.fromExcel(file, StateData.class, options);
                } catch (EmptyFileException e) {
                    log.info("File is empty: {}", e.getLocalizedMessage());
                }
                log.info("state data count: {}", stateData.size());
            }
        });
        return stateData;
    }

    public List<DodData> dodDataProcessor() throws IOException {
        Path path = Paths.get(fileProperties.getStaticData());
        File[] ff = path.toFile().listFiles();
        Arrays.asList(ff).stream().forEach(file -> {
            String filename = file.getName();
            String fileNameExtension = FilenameUtils.getExtension(filename);
            log.info("filename is: {}", filename);
            if (file.isFile() && file.length() != 0 && filename.equalsIgnoreCase("Static Data.xlsx")) {
                PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(2).build();
                try {
                    dodData = Poiji.fromExcel(file, DodData.class, options);
                } catch (EmptyFileException e) {
                    log.info("File is empty: {}", e.getLocalizedMessage());
                }
                log.info("dod data count: {}", dodData.size());
            }
        });
        return dodData;
    }

    public List<ReportData> reportDataProcessor() throws IOException {
        AtomicReference<List<ReportData>> reportData = new AtomicReference<>();
        Path path = Paths.get(fileProperties.getStaticData());
        File[] ff = path.toFile().listFiles();
        Arrays.asList(ff).stream().forEach(file -> {
            String filename = file.getName();
            String fileNameExtension = FilenameUtils.getExtension(filename);
            log.info("filename is: {}", filename);
            if (file.isFile() && file.length() != 0 && filename.equalsIgnoreCase("Raw.xlsx")) {
                PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();
                try {
                    reportData.set(Poiji.fromExcel(file, ReportData.class, options));
                } catch (EmptyFileException e) {
                    log.info("File is empty: {}", e.getLocalizedMessage());
                }
                log.info("report data count: {}", reportData.get().size());
            }
        });
        if (reportData.get().size() > 0) {
            reportDataList = reportData.get().stream().map(a -> {
                ReportData data = new ReportData();
                String itPath = a.getIterationPath();
                String iteration = StringUtils.substringAfterLast(itPath, "\\");
                String sprint = "";
                if(StringUtils.isNotBlank(iteration)){
                    sprint = StringUtils.substringBefore(iteration, "Sprint").trim();
                }
                if (StringUtils.isNotBlank(sprint) && sprint.endsWith("-")) {
                    sprint = sprint.substring(0, sprint.length() - 1);
                }
                data.setSquad(sprint.trim());
                String iterationPath = StringUtils.substringAfter(iteration, sprint);
                if (StringUtils.isNotBlank(iterationPath) && iterationPath.startsWith("-")) {
                    iterationPath = iterationPath.trim().replace("-", "");
                }
                data.setIterationPath(iterationPath);
                data.setActivatedDate(a.getActivatedDate());
                data.setAssignedTo(a.getAssignedTo());
                String createdDate = String.valueOf(a.getCreatedDate());
                if (StringUtils.isNotBlank(createdDate)) {
                    createdDate = createdDate.split(",")[0];
                }
                data.setCreatedDate(createdDate);
                data.setState(a.getState());
                String dod = a.getPbiDod();
                if(StringUtils.isBlank(dod)){
                    dod = "None";
                }else{
                    dod = a.getPbiDod();
                }
                data.setPbiDod(dod);
                data.setTitle(a.getTitle());
                data.setId(a.getId());
                return data;
            }).collect(Collectors.toList());
        }

        return reportDataList;
    }

    public void processor(List<SquadData> squad, List<ReportData> reportData, List<StateData> stateData, List<DodData> dodData) {
        String filename = fileProperties.getOutput() + "/CTO Weekly Project Report_" + today() + ".xlsx";
        List<OutputData> reportState = new ArrayList<>();
        List<OutputData> reportSquad = new ArrayList<>();
        reportSquad = reportData.stream().map(it -> {
            OutputData data = new OutputData();
            squad.stream().forEach(report -> {
                data.setId(it.getId());
                data.setTitle(it.getTitle());
                String cDate = it.getCreatedDate();
                String formattedCreatedDate="";
                try{
                    LocalDate localCreatedDate = LocalDate.parse(cDate,dtf);
                    formattedCreatedDate = localCreatedDate.format(df);
                    data.setCreatedDate(LocalDate.parse(formattedCreatedDate,df));
                }catch (DateTimeParseException ex){
                    try{
                        LocalDate localCreatedDate = LocalDate.parse(cDate,dateTimeFormatter);
                        formattedCreatedDate = localCreatedDate.format(df);
                        data.setCreatedDate(LocalDate.parse(formattedCreatedDate,df));
                    }catch (DateTimeParseException e) {
                        try {
                            LocalDate localCreatedDate = LocalDate.parse(cDate, dtfc);
                            formattedCreatedDate = localCreatedDate.format(df);
                            data.setCreatedDate(LocalDate.parse(formattedCreatedDate, df));
                        } catch (DateTimeParseException exe) {
                            try {
                                LocalDate localCreatedDate = LocalDate.parse(cDate, dtfm);
                                formattedCreatedDate = localCreatedDate.format(df);
                                data.setCreatedDate(LocalDate.parse(formattedCreatedDate, df));
                            } catch (DateTimeParseException exc) {
                                try {
                                    LocalDate localCreatedDate = LocalDate.parse(cDate, dtfd);
                                    formattedCreatedDate = localCreatedDate.format(df);
                                    data.setCreatedDate(LocalDate.parse(formattedCreatedDate, df));
                                } catch (DateTimeParseException exce) {
                                    exce.printStackTrace();
                                }
                            }
                        }
                    }
                }
                data.setState(it.getState());
                data.setDod(it.getPbiDod());
                data.setIterationPath(it.getIterationPath());
                if (it.getSquad().trim().equals(report.getSquad().trim())) {
                    data.setSquadTeam(report.getSquadTeam());
                    String activatedDate = report.getActivatedDate();
                    String formattedActivatedDate = "";
                    String formattedExpectedEndDate = "";
                    String expectedEndDate = report.getExpectedEndDate();
                    try{
                       LocalDate localActivatedDate = LocalDate.parse(activatedDate,df);
                        formattedActivatedDate = localActivatedDate.format(df);
                        data.setActivatedDate(LocalDate.parse(formattedActivatedDate,df));
                    }catch (DateTimeParseException ex){
                        try{
                            LocalDate localActivatedDate = LocalDate.parse(activatedDate,dfA);
                            formattedActivatedDate = localActivatedDate.format(dfA);
                            data.setActivatedDate(LocalDate.parse(formattedActivatedDate,dfA));
                        }catch (DateTimeParseException e) {
                            e.getLocalizedMessage();
                        }
                    }
                    try{
                        LocalDate localExpectedEndDate = LocalDate.parse(expectedEndDate,df);
                        formattedExpectedEndDate = localExpectedEndDate.format(df);
                        data.setExpectedEndDate(LocalDate.parse(formattedExpectedEndDate,df));
                    }catch (DateTimeParseException ex){
                        try{
                            LocalDate localExpectedEndDate = LocalDate.parse(expectedEndDate,dfA);
                            formattedExpectedEndDate = localExpectedEndDate.format(dfA);
                            data.setExpectedEndDate(LocalDate.parse(formattedExpectedEndDate,dfA));
                        }catch (DateTimeParseException e) {
                            e.getLocalizedMessage();
                        }
                    }
                    data.setAssignedTo(report.getAssignedTo());
                    data.setSquad(report.getSquad());
                    data.setSNo(report.getSNo());
                }
                else{
                    data.setSquad(it.getSquad());
                }
            });
            return data;
        }).collect(Collectors.toList());

        reportState = reportSquad.stream().map(it->{
            OutputData data = new OutputData();
            stateData.stream().forEach(state->{
                data.setId(it.getId());
                data.setTitle(it.getTitle());
                data.setCreatedDate(it.getCreatedDate());
                data.setState(it.getState());
                data.setDod(it.getDod());
                data.setIterationPath(it.getIterationPath());
                data.setSquadTeam(it.getSquadTeam());
                data.setExpectedEndDate(it.getExpectedEndDate());
                data.setActivatedDate(it.getActivatedDate());
                long daysBetween = 0;
                long durationInSprint = 0;
                LocalDate localActivatedDate = it.getActivatedDate();
                LocalDate sprintDate = LocalDate.now().plusDays(1).plusDays(offsetDays);
                Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                        || date.getDayOfWeek() == DayOfWeek.SUNDAY;

                if(localActivatedDate != null){
                    daysBetween = DAYS.between(localActivatedDate, sprintDate);
                }
                List<LocalDate> workdays = Stream.iterate(localActivatedDate, date -> date.plusDays(1))
                        .limit(daysBetween)
                        .filter(isWeekend.negate()).collect(Collectors.toList());
                if(!workdays.isEmpty()){
                    durationInSprint = workdays.size();
                }
                data.setDurationInSprint(durationInSprint);
                data.setAssignedTo(it.getAssignedTo());
                data.setSquad(it.getSquad());
                data.setSNo(it.getSNo());
                String stateV = it.getState() != null ? it.getState().trim() : "";
                String state2 = state.getState() !=null ? state.getState().trim() : "";

                if(stateV.equals(state2)){
                    data.setStateValue(state.getScore());
                }
            });
            return data;
        }).collect(Collectors.toList());

        items = reportState.stream().map(it->{
            OutputData data = new OutputData();
            dodData.stream().forEach(dod->{
                data.setId(it.getId());
                data.setTitle(it.getTitle());
                data.setCreatedDate(it.getCreatedDate());
                data.setState(it.getState());
                data.setDod(it.getDod());
                data.setIterationPath(it.getIterationPath());
                data.setSquadTeam(it.getSquadTeam());
                data.setActivatedDate(it.getActivatedDate());
                data.setExpectedEndDate(it.getExpectedEndDate());
                data.setAssignedTo(it.getAssignedTo());
                data.setSquad(it.getSquad());
                data.setSNo(it.getSNo());
                data.setStateValue(it.getStateValue());
                data.setDurationInSprint(it.getDurationInSprint());
                double durationInSprint = data.getDurationInSprint();
                data.setDurationInSprintWks(Double.parseDouble(formatter.format(durationInSprint/7)));
                if(data.getDod().equals(dod.getDod())){
                    data.setStateDod(dod.getScore());
                }
                double dodValue = data.getStateDod();
                double stateValue = data.getStateValue();
                double ratio = stateValue/dodValue;
                if(ratio>1){
                    ratio = 1.0/1.0;
                }
                try{
                    data.setRatio(Double.parseDouble(formatter.format(ratio)));
                }catch (NumberFormatException ex){
                   log.error("Error formatting: {}",ex.getLocalizedMessage());
                }
                double disw = data.getDurationInSprintWks();
                double pbi = pv / disw;
                if(pbi >1){
                    pbi = 1.1;
                }
                BigDecimal spiBD = new BigDecimal(pbi).setScale(2, RoundingMode.HALF_UP);
                data.setScheduleRatio(spiBD.doubleValue());
                data.setSpi(Double.parseDouble(spiFormatter.format(data.getScheduleRatio() * data.getRatio())));
                LocalDate localCreatedDate = data.getCreatedDate();
                LocalDate today = LocalDate.now().plusDays(offsetDays);
                long pbItemAgeInDays = 0;
                long daysBetween = 0;
                Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                        || date.getDayOfWeek() == DayOfWeek.SUNDAY;
                if(localCreatedDate != null){
                    daysBetween = DAYS.between(localCreatedDate, today);
                }
                List<LocalDate> workdays = Stream.iterate(localCreatedDate, date -> date.plusDays(1))
                        .limit(daysBetween)
                        .filter(isWeekend.negate()).collect(Collectors.toList());
                if(!workdays.isEmpty()){
                    pbItemAgeInDays = workdays.size();
                }
                data.setPbiItemAgeInDays(pbItemAgeInDays);
            });
            return data;
        }).collect(Collectors.toList());
        AtomicReference<BigDecimal> spiSquad = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<String> key = new AtomicReference<>("");
        try {
            Map<String, List<OutputData>> collect = items.stream().collect(Collectors.groupingBy(OutputData::getSquad));
            collect.entrySet().forEach(a->{
                SpiData spi = new SpiData();
                key.set(a.getKey());
                spiSquad.set(extracted(a.getKey(), a.getValue()));
                spi.setKey(key.get());
                spi.setSpiPerSquad(spiSquad.get());
                spiData.add(spi);
            });
        }catch (Exception ex) {
            System.out.println("Failed: Squad cannot be blank " + ex.getLocalizedMessage());
            log.error(ex.getMessage(), ex);
        }

        spiList.addAll(spiData);
        spiItems = items.stream().map(item->{
            OutputData data = new OutputData();
            spiList.stream().forEach(sp->{
                if(item.getSquad().equals(sp.getKey()))
                   data.setSpiPerSquad(sp.getSpiPerSquad());
                data.setId(item.getId());
                data.setTitle(item.getTitle());
                data.setCreatedDate(item.getCreatedDate());
                data.setState(item.getState());
                data.setDod(item.getDod());
                data.setIterationPath(item.getIterationPath());
                data.setSquadTeam(item.getSquadTeam());
                data.setCreatedDate(item.getCreatedDate());
                data.setActivatedDate(item.getActivatedDate());
                data.setExpectedEndDate(item.getExpectedEndDate());
                data.setAssignedTo(item.getAssignedTo());
                data.setSquad(item.getSquad());
                data.setSNo(item.getSNo());
                data.setRatio(item.getRatio());
                data.setPbiItemAgeInDays(item.getPbiItemAgeInDays());
                data.setStateValue(item.getStateValue());
                data.setDurationInSprint(item.getDurationInSprint());
                data.setDurationInSprintWks(item.getDurationInSprintWks());
                data.setStateDod(item.getStateDod());
                data.setScheduleRatio(item.getScheduleRatio());
                data.setSpi(item.getSpi());
            });
            return data;
        }).collect(Collectors.toList());


        Collections.sort(spiItems, new SortBySNo());
        consolidatedReport(spiItems, filename);
    }

    private BigDecimal extracted(String key, List<OutputData> value) {
        List<BigDecimal> netSpi = new ArrayList<>();
        value.stream().forEach(v->{
            bal = BigDecimal.valueOf(v.getSpi());
            System.out.println("spi "+bal);
            netSpi.add(bal);
        });
        spiSquad = netSpi.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        spiAverage = spiSquad.divide(new BigDecimal(netSpi.size()), RoundingMode.HALF_UP);
        System.out.println("key squad "+ key+" avgSPI "+spiAverage);
        return spiAverage;
    }

    public void consolidatedReport(List<OutputData> items, String fileName) {
        System.out.println("consolidated items count " + items.size());

        try (Workbook workbook = (new ScrumOutputExportUtil(pv))
                .exportExcel(
                        new String[]{"S/N", "Squad", "Assigned To", "Squad Team", "Iteration Path/Sprint(s)", "ID", "Work Item Type",
                                "Title", "Created Date", "Activated Date", "Expected End Date", "Duration in Sprint (wks)", "State", "DOD", "State (Value)",
                                "State (DoD)", "Ratio(State/DOD)", "Schedule Ratio", "SPI", "SPI per Squad", "Duration in Sprint", "PBI Item Age in days"},
                        items, "AgileScrumPBI."); FileOutputStream fos = new FileOutputStream(new File(fileName))) {
            System.out.println("Writing to file.......");
            workbook.write(fos);

            fos.flush();
            System.out.println("File written successfully!!!!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void outputReport(List<OutputDataDTO> items, String fileName) {
        System.out.println("consolidated items count " + items.size());

        try (Workbook workbook = (new OutputExportUtil())
                .exportExcel(
                        new String[]{"S/N", "Squad", "Assigned To", "Squad Team", "Iteration Path/Sprint(s)", "ID", "Work Item Type",
                                "Title", "Created Date", "Activated Date", "Expected End Date", "Duration in Sprint (wks)", "State", "DOD", "State (Value)",
                                "State (DoD)", "Ratio(State/DOD)", "Schedule Ratio", "SPI", "SPI per Squad", "Duration in Sprint", "PBI Item Age in days"},
                        items, "AgileScrumPBI."); FileOutputStream fos = new FileOutputStream(new File(fileName))) {
            System.out.println("Writing to file.......");
            workbook.write(fos);

            fos.flush();
            System.out.println("File written successfully!!!!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String today() {
        LocalDate date = LocalDate.now();
        String today = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        return today;
    }
}
