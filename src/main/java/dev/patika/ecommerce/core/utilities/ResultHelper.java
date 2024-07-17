package dev.patika.ecommerce.core.utilities;

import dev.patika.ecommerce.core.result.Result;
import dev.patika.ecommerce.core.result.ResultData;

public class ResultHelper {
    public static <T> ResultData<T> created(T data) {
        return new ResultData<>(true, Message.CREATED, "201", data);
    }

    public static <T> ResultData<T> validateError(T data) {
        return new ResultData<>(false, Message.VALIDATE_ERROR, "400", data);
    }

    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(false, Message.OK, "200", data);
    }

    public static Result notFoundError(String msg) {
        return new Result(false, msg, "404");
    }
}
