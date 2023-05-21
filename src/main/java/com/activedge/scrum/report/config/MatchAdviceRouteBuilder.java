//package com.activedge.matchadvice.config;
//
//import com.activedge.matchadvice.utility.MatchAdviceLoader;
//import org.apache.camel.builder.RouteBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MatchAdviceRouteBuilder extends RouteBuilder {
//    private MatchAdviceLoader processor;
//    @Autowired
//    public void setProcessor(MatchAdviceLoader processor) {
//        this.processor = processor;
//    }
//
//    @Override
//    public void configure() throws Exception {
////        from("{{match.advice.route.source}}")
////                .process(processor)
////                .to("{{match.advice.route.destination}}");
//    }
//}
