package decompiler.utils;


import decompiler.holders.FieldHolder;
import decompiler.visitors.AVisitor;
import decompiler.visitors.FVisitor;

import java.util.List;
import java.util.Map;

public class CVisitorUtils {

    public static StringBuilder toStringFiels(List<FieldHolder> fieldHolders, Map<String, FVisitor> annoattion) {
        StringBuilder sb = new StringBuilder();
        for (FieldHolder f : fieldHolders) {
            sb.append(toStringFiel(f, annoattion.get(f.name)));
        }
        return sb;
    }

    public static StringBuilder toStringFiel(FieldHolder field, FVisitor fVisitor) {
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
