package org.kpaas.sidecar.portal.api.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.Data;
import org.cloudfoundry.client.lib.org.codehaus.jackson.map.ObjectMapper;
import org.container.platform.api.common.CommonUtils;
import org.container.platform.api.common.Constants;
import org.container.platform.api.common.MessageConstant;
import org.container.platform.api.common.model.CommonStatusCode;
import org.container.platform.api.common.model.ResultStatus;
import org.container.platform.api.exception.CommonStatusCodeException;
import org.container.platform.api.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (CommonStatusCodeException e){
            setErrorResponse(response, e);
        }catch (Exception e){
            setErrorResponse(response, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, Exception ex){
        ObjectMapper objectMapper = new ObjectMapper();
        //if (ex.getClass().isInstance(CommonStatusCodeException.class)){
        if (ex instanceof CommonStatusCodeException){
            ResultStatus resultStatus = null;
            for (CommonStatusCode code : CommonStatusCode.class.getEnumConstants()) {
                if (((CommonStatusCodeException) ex).getErrorCode().contains(Integer.toString(code.getCode()))) {
                    resultStatus = new ResultStatus(Constants.RESULT_STATUS_FAIL, code.getMsg(),
                            code.getCode(), code.getMsg());
                    response.setStatus(CommonStatusCode.UNAUTHORIZED.getCode());
                }
            }
            if (resultStatus == null){
                resultStatus = new ResultStatus(Constants.RESULT_STATUS_FAIL, "An unknown error occurred",
                        Integer.parseInt(ex.getMessage()), "An unknown error occurred");
            }
            try{
                response.getWriter().write(objectMapper.writeValueAsString(resultStatus));
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            ErrorMessage errorMessage;
            if (ex.getMessage().contains("404")) {
                errorMessage = new ErrorMessage(Constants.RESULT_STATUS_FAIL, CommonStatusCode.NOT_FOUND.getMsg(), HttpStatus.NOT_FOUND.value(), CommonStatusCode.NOT_FOUND.getMsg());
            }else {
                errorMessage = new ErrorMessage(Constants.RESULT_STATUS_FAIL, CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR.value(), CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
            }
            try{
                response.getWriter().write(objectMapper.writeValueAsString(errorMessage));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    private void setErrorResponse(HttpServletResponse response){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(CommonStatusCode.UNAUTHORIZED.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(CommonStatusCode.UNAUTHORIZED.getCode(), CommonStatusCode.UNAUTHORIZED.getCode(), "Sidecar.whoami "+CommonStatusCode.UNAUTHORIZED.getMsg());
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Data
    public static class ErrorResponse{
        private final Integer httpStatusCode;
        private final Integer status;
        private final String resultMessage;
    }
}
