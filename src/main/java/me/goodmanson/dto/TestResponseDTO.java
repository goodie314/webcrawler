package me.goodmanson.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by u6062536 on 2/6/2018.
 */
public class TestResponseDTO {

    private String checkName;
    private String url;

    private List<String> successMessages;
    private List<String> infoMessages;
    private List<String> errorMessages;

    public TestResponseDTO(String checkName, String url) {
        this.checkName = checkName;
        this.url = url;
        this.successMessages = new ArrayList<>();
        this.infoMessages = new ArrayList<>();
        this.errorMessages = new ArrayList<>();
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getSuccessMessages() {
        return successMessages;
    }

    public void setSuccessMessages(List<String> successMessages) {
        this.successMessages = successMessages;
    }

    public void addSuccess(String message) {
        this.successMessages.add(message);
    }

    public List<String> getInfoMessages() {
        return infoMessages;
    }

    public void setInfoMessages(List<String> infoMessages) {
        this.infoMessages = infoMessages;
    }

    public void addInfo(String message) {
        this.infoMessages.add(message);
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void addError(String message) {
        this.errorMessages.add(message);
    }

    public String toJSON() {
        ObjectMapper mapper;

        mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException j) {
            j.printStackTrace();
            return "";
        }
//        boolean startOfList = true;
//        StringBuilder builder = new StringBuilder("{");
//        builder.append("\"url\":\"").append(this.url).append("\",");
//        builder.append("\"successMessages\":[");
//        for (String message : this.successMessages) {
//            if (startOfList) {
//                builder.append("\"").append(message).append("\"");
//                startOfList = false;
//            }
//            else {
//                builder.append(",\"").append(message).append("\"");
//            }
//        }
//        builder.append("],");
//        startOfList = true;
//
//        builder.append("\"infoMessages\":[");
//        for (String message : this.infoMessages) {
//            if (startOfList) {
//                builder.append("\"").append(message).append("\"");
//                startOfList = false;
//            }
//            else {
//                builder.append(",\"").append(message).append("\"");
//            }
//        }
//        builder.append("],");
//        startOfList = true;
//
//        builder.append("\"errorMessages\":[");
//        for (String message : this.errorMessages) {
//            if (startOfList) {
//                builder.append("\"").append(message).append("\"");
//                startOfList = false;
//            }
//            else {
//                builder.append(",\"").append(message).append("\"");
//            }
//        }
//        builder.append("]}");
//        return builder.toString();
    }
}
