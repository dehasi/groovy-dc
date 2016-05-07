package decompiler.utils;


import decompiler.ObjectType;
import decompiler.holders.FieldHolder;
import decompiler.holders.MethodHolder;
import decompiler.visitors.AVisitor;
import decompiler.visitors.FVisitor;
import decompiler.visitors.MVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CVisitorUtils {

    public static final String TRAIT_ANNOTATION = "@org.codehaus.groovy.transform.trait.Traits$Implemented";
    public static final String ABSTRACT = "abstract";

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

    public static StringBuilder toStringMethods(List<MethodHolder> methodHolders,
                                                Map<String, MVisitor> annoattion, ObjectType type) {
        StringBuilder sb = new StringBuilder();
        for (MethodHolder method : methodHolders) {
            sb.append(toStringMethod(method, annoattion.get(method.name + method.signature), type));
        }
        return sb;
    }

    public static StringBuilder toStringMethod(MethodHolder method, MVisitor mVisitor, ObjectType type) {
        StringBuilder sb = new StringBuilder();

        if (method.name.contains("init>")) {
            return sb;
        }
        method.parsedBody = ((method.access & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT) ? "" : createMethodBody(type);

        if (method.parent == ObjectType.TRAIT) {

            if (isTraint(method.parsedAnnotations)) {
                method.parsedBody = createMethodBody(type);
                int start = method.parsedModifiers.indexOf(ABSTRACT);
                method.parsedModifiers.replace(start, start + ABSTRACT.length(), ParserUtils.EMPTY_STRING);
            }
        }
        if (mVisitor == null) return method.toStringBuilder();
        Set<Map.Entry<StringBuilder, AVisitor>> entries = mVisitor.getMethodAnnotationsMap().entrySet();
        method.parsedAnnotations = createAnnotations(entries);
        for (int i = 0; i < method.parsedArgs.length; ++i) {
            if (mVisitor.getParameters().get(i) == null) continue;
            for (AVisitorEntry entry : mVisitor.getParameters().get(i)) {
                StringBuilder annt = toStringAnnt(entry.name, entry.aVisitor);


                method.parsedArgs[i] = annt.append(' ').append(method.parsedArgs[i]).toString();
            }
        }
        return method.toStringBuilder();
    }

    private static boolean isTraint(StringBuilder annt) {
        if (annt == null) return false;
        if (annt.toString().contains(TRAIT_ANNOTATION)) {
            int start = annt.indexOf(TRAIT_ANNOTATION);
            int length = TRAIT_ANNOTATION.length();
            annt.replace(start, start + length, ParserUtils.EMPTY_STRING);
            return true;
        }
        return false;
    }

    private static StringBuilder createAnnotations(Set<Map.Entry<StringBuilder, AVisitor>> entries) {
        StringBuilder sb = new StringBuilder();
        if (entries == null) return sb;
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

    private static String createMethodBody(ObjectType type) {
        switch (type) {
            case INTERFACE:
                return ParserUtils.EMPTY_STRING;
            case TRAIT:
            case CLASS:
                return "{throw new UnsupportedOperationException(\"I can't parse body\");}";
            default:
                throw new UnsupportedOperationException("I can parse only interface");
        }
    }

    private static String processModifiers(ObjectType type) {
        return null;
    }
}
