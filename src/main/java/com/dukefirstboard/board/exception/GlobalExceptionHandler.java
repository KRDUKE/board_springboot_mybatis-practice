package com.dukefirstboard.board.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(IOException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "파일 처리 중 오류가 발생했습니다: " + ex.getMessage());
        return mav;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "잘못된 입력입니다: " + ex.getMessage());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "오류가 발생했습니다: " + ex.getMessage());
        return mav;
    }
}