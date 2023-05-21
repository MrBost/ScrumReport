//package com.activedge.matchadvice.utility;
//
//import com.activedge.matchadvice.service.MatchAdviceProcessor;
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.apache.commons.io.FilenameUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//
//@Component
//public class MatchAdviceLoader implements Processor {
//    private MatchAdviceProcessor processor;
//    @Autowired
//    public void setProcessor(MatchAdviceProcessor processor) {
//        this.processor = processor;
//    }
//
//    @Override
//    public void process(Exchange exchange) throws Exception {
//        File file = exchange.getIn().getBody(File.class);
//        String extension  = FilenameUtils.getExtension(file.getName());
//        if(extension.contains("xlsx")){
//            processor.processor(exchange);
//        }
//    }
//}
