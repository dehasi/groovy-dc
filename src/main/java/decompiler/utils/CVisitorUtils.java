package decompiler.utils;


import decompiler.holders.FieldHolder;
import decompiler.holders.MethodHolder;
import decompiler.visitors.AVisitor;
import decompiler.visitors.FVisitor;
import decompiler.visitors.MVisitor;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CVisitorUtils {

    public static StringBuilder toStringFields(List<FieldHolder> fieldHolders, Map<String, FVisitor> annoattion) {
        StringBuilder sb = new StringBuilder();
        for (FieldHolder f : fieldHolders) {
            sb.append(toStringField(f, annoattion.get(f.name)));
        }
        return sb;
    }

    private static StringBuilder toStringField(FieldHolder field, FVisitor fVisitor) {
        if (fVisitor == null) return field.field;
        return
                new StringBuilder()
                        .append(createAnnotations(fVisitor.getFieldAnnotationsMap().entrySet()))
                        .append(field.field);
    }

    public static StringBuilder toStringMethods(List<MethodHolder> methodHolders, Map<String, MVisitor> annoattion) {
        StringBuilder sb = new StringBuilder();
        for (MethodHolder method : methodHolders) {
            sb.append(toStringMethod(method, annoattion.get(method.name + method.signature)));
        }
        return sb;
    }

    public static StringBuilder toStringMethod(MethodHolder method, MVisitor mVisitor) {
        StringBuilder sb = new StringBuilder();

        if (method.name.contains("init>")) {
            return sb;
        }
        Set<Map.Entry<StringBuilder, AVisitor>> entries = mVisitor.getMethodAnnotationsMap().entrySet();
        method.parsedAnnotations = createAnnotations(entries);

        mVisitor.getParameters();
        return method.toStringBuilder();
    }

    private static StringBuilder createAnnotations(Set<Map.Entry<StringBuilder, AVisitor>> entries) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<StringBuilder, AVisitor> entry : entries) {
            StringBuilder key = entry.getKey();
            AVisitor value = entry.getValue();
            StringBuilder annt = toStringAnnt(key, value);
            sb.append(annt);
        }
        return sb;
    }

    public static StringBuilder toStringAnntotations(List<StringBuilder> annts, Map<StringBuilder, AVisitor> body) {
        StringBuilder sb = new StringBuilder();
        for (StringBuilder annt : annts) {
            sb.append(toStringAnnt(annt, body.get(annt)));
        }
        return sb;
    }

    private static StringBuilder toStringAnnt(StringBuilder annt, AVisitor aVisitor) {
        if (aVisitor == null) {
            return annt;
        }
        List<String> bf = aVisitor.getBf();
        StringBuilder body = new StringBuilder();
        if (bf.size() > 0) {
            body.append('(');
            for (String s : bf) {
                body.append(s).append(',');
            }
            body.setLength(body.length() - 1);
            body.append(')');
        }
        return annt.append('\t').append(body).append('\n');
    }
}
