package com.biocar.utils;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

/**
 * @author DeSen Xu
 * @date 2021-11-17 22:11
 */
public class EsUtils {
    private EsUtils() {}

    public static boolean checkFail(IndexResponse indexResponse) {
        return indexResponse.getResult().getOp() != DocWriteResponse.Result.CREATED.getOp();
    }

    public static boolean checkFail(DeleteResponse deleteResponse) {
        return deleteResponse.getResult().getOp() != DocWriteResponse.Result.DELETED.getOp();
    }


    public static boolean checkFail(UpdateResponse updateResponse) {
        return updateResponse.getResult().getOp() != DocWriteResponse.Result.UPDATED.getOp();
    }


}
