package com.marshalchen.common.commonUtils.jsonUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

/**
 * Meta Json Reader of HashMap
 */
public class JsonHashMapReader extends JsonMetaReader<HashMap> {

    public List<HashMap> parse(String jsonString) throws IOException {
        StringReader strReader = new StringReader(jsonString);
        List<HashMap> objs = read(strReader);
        return objs;
    }


}
