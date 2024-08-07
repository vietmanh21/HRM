package com.example.hrm.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class ErrorVm {
    private String statusCode;
    private String title;
    private String detail;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    private List<String> fieldErrors;

    public ErrorVm(String statusCode, String title, String detail) {
        this(statusCode, title, detail, new ArrayList<String>());
    }

    public ErrorVm(String statusCode, String title, String detail, List<String> fieldErrors) {
        this.statusCode = statusCode;
        this.title = title;
        this.detail = detail;
        this.fieldErrors = fieldErrors;
    }
}
