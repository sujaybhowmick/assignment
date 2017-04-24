package com.sentifi.assignment.controllers;


import com.sentifi.assignment.exceptions.DataFormatException;
import com.sentifi.assignment.exceptions.ResourceNotFoundException;
import com.sentifi.assignment.exceptions.ServiceException;
import com.sentifi.assignment.model.RestErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Sujay on 4/18/17.
 */
public abstract class AbstractController implements ApplicationEventPublisherAware {

    private static final String NEWLINE = "\n";

    protected final Logger log = LoggerFactory.getLogger(AbstractController.class);

    protected ApplicationEventPublisher eventPublisher;

    protected static final String DEFAULT_PAGE_SIZE = "100";
    protected static final String DEFAULT_PAGE_NUM = "0";

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public
    @ResponseBody
    RestErrorInfo handleDataStoreException(ServiceException ex, WebRequest request, HttpServletResponse response) {
        log.info("Converting Data Store exception to RestResponse : " + ex.getMessage());

        return new RestErrorInfo(ex, "Internal Server Error");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public
    @ResponseBody
    RestErrorInfo handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletResponse response) {
        log.info("ResourceNotFoundException handler:" + ex.getMessage());
        return new RestErrorInfo(ex, "Sorry I couldn't find it.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataFormatException.class)
    public
    @ResponseBody
    RestErrorInfo handleDataStoreException(DataFormatException ex, WebRequest request, HttpServletResponse response) {
        log.info("Converting Data Store exception to RestResponse : " + ex.getMessage());

        return new RestErrorInfo(ex, "Bad Request");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public String getServerStatusInfo(String status) {

        StringBuilder responseBuilder = new StringBuilder(
                "{ " + "\"status\" : \"" + status + "\" " + " } ");
        return responseBuilder.toString();
    }

    public String getCallBackServerStatusInfo(String status) {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<!DOCTYPE html>").append(NEWLINE);
        responseBuilder.append("<head>").append(NEWLINE);
        responseBuilder.append("<meta charset=").append("\"UTF-8\"").append(">\n");
        responseBuilder.append("<meta http-equiv=").append("\"Access-Control-Allow-Origin\"").append(" content=").append("\"*\"").append(">\n");
        responseBuilder.append("<title>DocuSign Callback Page</title>").append(NEWLINE);
        responseBuilder.append("</head>").append(NEWLINE);
        responseBuilder.append("<body>").append(NEWLINE).append("</body>").append(NEWLINE);
        responseBuilder.append("<script>").append(NEWLINE);
        responseBuilder.append("parent.parentMethod('").append(status).append("');").append(NEWLINE);
        responseBuilder.append("</script>").append(NEWLINE);
        responseBuilder.append("</html>");
        return responseBuilder.toString();
    }

}