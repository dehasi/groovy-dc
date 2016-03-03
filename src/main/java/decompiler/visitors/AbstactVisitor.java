package decompiler.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstactVisitor {
    protected StringBuilder buffer = new StringBuilder();
    protected Map<String, List<String>> signatureMap = new HashMap<>();
    public StringBuilder getBuffer() {
        return buffer;
    }

    public Map<String, List<String>> getSignatureMap() {
        return signatureMap;
    }
    protected void putToMap(String key, String value) {
        if (!signatureMap.containsKey(key)) {
            signatureMap.put(key, new ArrayList<String>());
        }
        List<String> strings = signatureMap.get(key);
        strings.add(value);
        signatureMap.put(key, strings);
    }
}
