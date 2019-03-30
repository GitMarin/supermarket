package com.wrg.supermarket.utils.exception;


import com.wrg.supermarket.utils.enums.PlatErrorCode;

/**
 * 营销平台统一异常类
 *
 * @author bigsm
 */
public class MkplatException extends TinyplatCommonException {

    private static final long serialVersionUID = -5784947283467577384L;

    private PlatErrorCode errorCode;

    public MkplatException(PlatErrorCode code) {
        super(code.getCode(), code.getDesc());

        this.errorCode = code;
    }

    public MkplatException(PlatErrorCode code, String message) {
        super(code.getCode(), message);
        this.errorCode = code;
    }

    public PlatErrorCode getErrorCode() {
        return errorCode;
    }
}
