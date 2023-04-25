package com.example.elasticsearchdemo.tool;

import cn.hutool.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.Serial;
import java.util.HashMap;

/**
 * 数据结果封装体 M
 *
 * @author  **.**
 */

@Tag(name = "响应信息主体")
public class RespBean extends HashMap<String, Object> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";





    /**
     * 初始化一个M对象
     */
    public RespBean() {
    }

    public static RespBean failed() {
        return RespBean.error();
    }


    @Override
    public RespBean put(String key, Object value) {
         super.put(key, value);
         return  this;
    }

    /**
     * 初始化一个M对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public RespBean(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个M对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public RespBean(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (!(data == null)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回默认成功消息
     *
     * @return 成功消息
     */
    public static RespBean success() {
        return RespBean.success("操作成功");
    }



    public static RespBean ok(Object data) {

        return  RespBean.success(data);
    }


    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static RespBean success(String msg) {
        return RespBean.success(msg, null);
    }

    /**
     * 返回成功数据
     *
     * @param data 数据对象
     * @return
     */
    public static RespBean success(Object data) {
        return RespBean.success("操作成功", data);
    }

    /**
     * 初始化一个返回成功消息AjaxResult对象
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static RespBean success(String msg, Object data) {
        return new RespBean(HttpStatus.HTTP_OK, msg, data);
    }

    /**
     * 返回默认错误消息
     *
     * @return
     */
    public static RespBean error() {
        return RespBean.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static RespBean error(String msg) {
        return RespBean.error(msg, null);
    }

    /**
     * 返回错误数据
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static RespBean error(int code, String msg) {
        return new RespBean(code, msg, null);
    }

    /**
     * 初始化一个返回错误消息AjaxResult对象
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static RespBean error(String msg, Object data) {
        return new RespBean(HttpStatus.HTTP_BAD_REQUEST, msg, data);
    }

}
