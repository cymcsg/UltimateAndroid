package com.fss.commons.commonUtils.jsonUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cym on 14-7-15.
 */
public class JsonHashMapReader extends JsonMetaReader<HashMap> {

    public List<HashMap> parse(String jsonString) throws IOException {
        StringReader strReader = new StringReader(jsonString);
        List<HashMap> objs = read(strReader);
        return objs;
    }


}
