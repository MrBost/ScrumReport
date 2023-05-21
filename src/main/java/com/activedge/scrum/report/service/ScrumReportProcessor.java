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
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.math.NumberUtils.max;

@Slf4j
@Service
public class ScrumReportProcessor {
    @Autowired
    private FileProperties fileProperties;
    List<SquadData> squadData = new ArrayList<>();
    List<ReportData> reportDataList = new ArrayList<>();
    List<OutputData> items = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    List<OutputDataDTO> outputData = new ArrayList<>();
    List<StateData> stateData = new ArrayList<>();
    List<DodData> dodData = new ArrayList<>();

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
            if (file.isFile() && file.length() != 0 && fileNameExtension.equalsIgnoreCase("xlsx")) {
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
            if (file.isFile() && file.length() != 0 && fileNameExtension.equalsIgnoreCase("xlsx")) {
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

    public void outputDataProcessor() throws IOException {
        String fileName = fileProperties.getOutput() + "/CTO Weekly Project.xlsx";
//        AtomicReference<List<OutputData>> output = new AtomicReference<>();
        Path path = Paths.get(fileProperties.getOutput());
        File[] ff = path.toFile().listFiles();
        Arrays.asList(ff).stream().forEach(file -> {
            String filename = file.getName();
            String fileNameExtension = FilenameUtils.getExtension(filename);
            if (file.isFile() && file.length() != 0 && filename.equals("CTO Weekly Project 20230520.xlsx") && fileNameExtension.equalsIgnoreCase("xlsx")) {
                log.info("filename is: {}", filename);
                PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(0).build();
                try {
                    outputData = Poiji.fromExcel(file, OutputDataDTO.class, options);
                } catch (EmptyFileException e) {
                    log.info("File is empty: {}", e.getLocalizedMessage());
                }
                log.info("output data count: {}", outputData.size());
            }
        });
        outputReport(outputData,fileName);
    }

    public List<ReportData> reportDataProcessor() throws IOException {
        AtomicReference<List<ReportData>> reportData = new AtomicReference<>();
        Path path = Paths.get(fileProperties.getReport());
        File[] ff = path.toFile().listFiles();
        Arrays.asList(ff).stream().forEach(file -> {
            String filename = file.getName();
            String fileNameExtension = FilenameUtils.getExtension(filename);
            log.info("filename is: {}", filename);
            if (file.isFile() && file.length() != 0 && fileNameExtension.equalsIgnoreCase("xlsx")) {
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
                String sprint = StringUtils.substringBefore(iteration, "Sprint").trim();
                if (sprint.endsWith("-")) {
                    sprint = sprint.substring(0, sprint.length() - 1);
                }
                data.setSquad(sprint.trim());
                String iterationPath = StringUtils.substringAfter(iteration, sprint);
                if (iterationPath.startsWith("-")) {
                    iterationPath = iterationPath.trim().replace("-", "");
                }
                data.setIterationPath(iterationPath);
                data.setActivatedDate(a.getActivatedDate());
                data.setAssignedTo(a.getAssignedTo());
                String createdDate = a.getCreatedDate();
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
        String filename = fileProperties.getOutput() + "/CTO Weekly Project " + today() + ".xlsx";
        List<OutputData> reportState = new ArrayList<>();
        List<OutputData> reportDod = new ArrayList<>();
        List<OutputData> reportSquad = new ArrayList<>();
        reportSquad = reportData.stream().map(it -> {
            OutputData data = new OutputData();
            squad.stream().forEach(report -> {
                data.setId(it.getId());
                data.setTitle(it.getTitle());
                data.setCreatedDate(it.getCreatedDate());
                data.setState(it.getState());
                data.setDod(it.getPbiDod());
                data.setIterationPath(it.getIterationPath());
                if (it.getSquad().trim().equals(report.getSquad().trim())) {
                    data.setSquadTeam(report.getSquadTeam());
                    data.setActivatedDate(report.getActivatedDate());
                    data.setExpectedEndDate(report.getExpectedEndDate());
                    data.setAssignedTo(report.getAssignedTo());
                    data.setSquad(report.getSquad());
                    data.setSNo(report.getSNo());
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
                data.setActivatedDate(it.getActivatedDate());
                data.setExpectedEndDate(it.getExpectedEndDate());
                data.setAssignedTo(it.getAssignedTo());
                data.setSquad(it.getSquad());
                data.setSNo(it.getSNo());
                if(it.getState().trim().equals(state.getState().trim())){
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
                if(data.getDod().equals(dod.getDod())){
                    data.setStateDod(dod.getScore());
                }
            });
            return data;
        }).collect(Collectors.toList());

        Collections.sort(items, new SortBySNo());
        consolidatedReport(items, filename);
    }

    public void consolidatedReport(List<OutputData> items, String fileName) {
        System.out.println("consolidated items count " + items.size());
        Workbook workbook = (new ScrumOutputExportUtil())
                .exportExcel(
                        new String[]{"S/N", "Squad", "Assigned To", "Squad Team", "Iteration Path/Sprint(s)", "ID", "Work Item Type",
                                "Title", "Created Date", "Activated Date", "Expected End Date", "Duration in Sprint (wks)", "State", "DOD", "State (Value)",
                                "State (DoD)", "Ratio(State/DOD)", "Schedule Ratio", "SPI", "SPI per Squad", "Duration in Sprint", "PBI Item Age in days"},
                        items, "AgileScrumPBI.");

        FileOutputStream fos = null;
        try {
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

    public void outputReport(List<OutputDataDTO> items, String fileName) {
        System.out.println("consolidated items count " + items.size());
        Workbook workbook = (new OutputExportUtil())
                .exportExcel(
                        new String[]{"S/N", "Squad", "Assigned To", "Squad Team", "Iteration Path/Sprint(s)", "ID", "Work Item Type",
                                "Title", "Created Date", "Activated Date", "Expected End Date", "Duration in Sprint (wks)", "State", "DOD", "State (Value)",
                                "State (DoD)", "Ratio(State/DOD)", "Schedule Ratio", "SPI", "SPI per Squad", "Duration in Sprint", "PBI Item Age in days"},
                        items, "AgileScrumPBI.");

        FileOutputStream fos = null;
        try {
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

    public String today() {
        LocalDate date = LocalDate.now();
        String today = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        return today;
    }
}
