package com.pigx.engine.core.definition.domain.view.datatables;

import java.util.List;


public class DataTableUtils {

    public static final String ECHO = "sEcho";
    public static final String DISPLAY_START = "iDisplayStart";
    public static final String DISPLAY_LENGTH = "iDisplayLength";
    public static final String QUERY_JSON = "queryJson";
    public static final String DATA = "data";

    public static DataTableResult parseDataTableParameter(List<DataTableParameter> params) {

        String sEcho = null;
        String jsonString = null;
        int iDisplayStart = 0;
        int iDisplayLength = 0;
        for (DataTableParameter param : params) {
            if (param.getName().equals(ECHO)) {
                sEcho = param.getValue().toString();
            }
            if (param.getName().equals(DISPLAY_START)) {
                iDisplayStart = (int) param.getValue();
            }
            if (param.getName().equals(DISPLAY_LENGTH)) {
                iDisplayLength = (int) param.getValue();
            }
            if (param.getName().equals(QUERY_JSON)) {
                jsonString = param.getValue().toString();
            }
        }

        return new DataTableResult(sEcho, iDisplayStart, iDisplayLength, jsonString);
    }
}
