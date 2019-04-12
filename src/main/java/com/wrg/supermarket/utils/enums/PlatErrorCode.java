package com.wrg.supermarket.utils.enums;

/**
 * 平台统一错误码枚举
 *
 * @author bigsm
 */
public enum PlatErrorCode {

    SUCCESS("9999", "成功"),
    /**
     * 参数无效
     */
    PARAM_INVAILD("3001", "参数无效"),

    /**
     * 数据库操作失败
     */
    DBOPERATION_ERROR("3002", "数据库操作失败"),

    /**
     * 锁表失败
     */
    DBLOCKING_FAILED("3003", "锁表失败"),

    /**
     * 数据状态不一致
     */
    DATASTATUS_ERROR("3004", "数据状态不一致"),

    /**
     * RPC接口调用失败
     */
    RPCINVOKE_ERROR("3005", "RPC调用失败"),

    /**
     * 文件操作失败
     */
    FILEOPERATOR_ERROR("3006", "文件操作失败"),
    FILEUPLOAD_ERROR("3007","附件上传失败"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("3100", "系统异常,请联系管理员"),

    FILE_NOT_FOUND("4301","未找到指定文件"),

    FILE_EXEC_FAILED("4302","文件操作失败"),

    FILE_UPLOAD_FAILED("4303","文件上传失败"),

    TEMPLATE_CONFIG_DATA_ERROR("4304","待模版渲染的配置数据格式有误"),

    TEMPLATE_COMPILE_ERROR("4305","模版渲染失败"),

    TEMPLATE_ARCHIVE_ERROR("4306","活动模版资源打包失败"),

    TEMPLATE_PUBLISH_ERROR("4307","活动模版资源发布失败");

    /**
     * 代码
     */
    private String code;
    /**
     * 描述
     */
    private String desc;

    /**
     * @param code
     * @param desc
     */
    private PlatErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return
     */
    public static PlatErrorCode getByCode(String code) {
        for (PlatErrorCode param : values()) {
            if (param.getCode().equals(code)) {
                return param;
            }
        }
        return null;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
