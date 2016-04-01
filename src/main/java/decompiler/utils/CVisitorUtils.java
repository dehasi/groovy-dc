package decompiler.utils;


import decompiler.holders.FieldHolder;
import decompiler.holders.MethodHolder;
import decompiler.visitors.AVisitor;
import decompiler.visitors.FVisitor;
import decompiler.visitors.MVisitor;

import java.util.List;
import java.util.Map;

public class CVisitorUtils {

    public static StringBuilder toStringFields(List<FieldHolder> fieldHolders, Map<String, FVisitor> annoattion) {
        StringBuilder sb = new StringBuilder();
        for (FieldHolder f : fieldHolders) {
            sb.append(toStringField(f, annoattion.get(f.name)));
        }
        return sb;
    }

    public static StringBuilder toStringField(FieldHolder field, FVisitor fVisitor) {
        if (fVisitor == null) {
            return field.field;
        }
        StringBuilder sb = new StringBuilder();
        fVisitor.getFieldAnnotationsMap().entrySet();
        for (Map.Entry<StringBuilder, AVisitor> entry : fVisitor.getFieldAnnotationsMap().entrySet()) {
            StringBuilder key = entry.getKey();
            AVisitor value = entry.getValue();
            StringBuilder annt = toStringAnnt(key, value);
            sb.append(annt);
        }
        return sb.append(field.field);
    }

    public static StringBuilder toStringMethods(List<MethodHolder> methodHolders, Map<String, MVisitor> annoattion) {
        StringBuilder sb = new StringBuilder();
        for (MethodHolder method : methodHolders) {
            sb.append(toStringMethod(method, annoattion.get(method.name + method.signature)));
        }
        return sb;
    }

    public static StringBuilder toStringMethod(MethodHolder methodHolders, MVisitor mVisitor) {
        StringBuilder sb = new StringBuilder();


        return sb;
    }

    public static StringBuilder toStringAnnts(List<StringBuilder> annts, Map<StringBuilder, AVisitor> body) {
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
        return annt.append(body).append('\n');
    }
}
