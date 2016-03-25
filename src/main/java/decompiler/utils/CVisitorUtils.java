package decompiler.utils;


import decompiler.holders.FieldHolder;
import decompiler.visitors.FVisitor;

import java.util.List;
import java.util.Map;

public class CVisitorUtils {
    public static StringBuilder toStringFiels(List<FieldHolder> fieldHolders, Map<String, FVisitor> annoattion) {
        StringBuilder sb = new StringBuilder();
        for (FieldHolder f : fieldHolders) {
            sb.append(toStringFiel(f, annoattion));
        }
        return sb;
    }

    public static  StringBuilder toStringFiel(FieldHolder field, Map<String, FVisitor> annoattion) {
        return field.field;
    }


}
