package com.activedge.scrum.report;

import com.activedge.scrum.report.config.properties.FileProperties;
import com.activedge.scrum.report.service.ScrumReportProcessor;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ScrumReportApplication implements CommandLineRunner {

	private static final int NON_REPTNG_COLS = 4;
	@Autowired
	private FileProperties fileProperties;
	@Autowired
	private ScrumReportProcessor processor;
	private int cores = Runtime.getRuntime().availableProcessors();
	protected Sheet sh;

	public static void main(String[] args) {
		SpringApplication.run(ScrumReportApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Consolidator service started");
		processor.squadDataProcessor();
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
////		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
//		LocalDate ld = LocalDate.parse("19/04/2023",dtf).minusDays(3);
////		String format = ld.format(df);
//		System.out.println(ld);
//		LocalDate today = LocalDate.now().plusDays(2);
//		System.out.println(today);
//		long between = DAYS.between(ld, today);
//		System.out.println(between);

	}


	private void extracted() throws IOException {
		ExecutorService executorService = Executors.newFixedThreadPool(cores);
		Future<Long> futureSquad = executorService.submit(new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				return 0L;
			}
		});
		Future<Long> futureState = executorService.submit(new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				return 0L;
			}
		});
		Future<Long> futureDod = executorService.submit(new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				return 0L;
			}
		});
		while(!futureSquad.isDone()&&!futureState.isDone()&&!futureDod.isDone()){
			;
		}
	}
}
