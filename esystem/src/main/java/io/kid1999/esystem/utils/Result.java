package io.kid1999.esystem.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kid1999
 * @date 2020/9/30 20:34
 * @title 后端返回数据对象
 */

@Getter
@Setter
public class Result<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("返回值")
	private int code;
	@ApiModelProperty("返回信息")
	private String message;
	@ApiModelProperty("业务数据")
	private T data;

	public Result() {
	}

	public Result(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public Result(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Result success(T data){
		this.data = data;
		this.code = 200;
		this.message = "success";
		return this;
	}

	public Result success(){
		this.code = 200;
		this.message = "success";
		return this;
	}

	public Result failed(){
		this.code = 400;
		this.message = "failed";
		return this;
	}

	public Result failed(String message){
		this.code = 400;
		this.message = message;
		return this;
	}

	public Result failedByUnauthorized(){
		this.code = 401;
		this.message = "failed";
		return this;
	}

	public Result failedByNotFound(){
		this.code = 403;
		this.message = "failed";
		return this;
	}

	@Override
	public String toString() {
		return "Result{" +
			"code=" + code +
			", message='" + message + '\'' +
			", data=" + data +
			'}';
	}
}
