package decompiler.holders;

import decompiler.ObjectType;

import static decompiler.utils.ParserUtils.EMPTY_STRING_BUILDER;

public class MethodHolder {
    public ObjectType parent;
    public boolean skip;

    public int access;
    public String name;
    public String desc;
    public String signature;
    public String[] exceptions;

    public StringBuilder parsedAnnotations;
    public StringBuilder parsedModifiers;
    public String parsedGenericDeclaration;
    public String parsedReturnValue;
    public String[] parsedArgs;
    public StringBuilder parsedExceiptions;
    public String parsedBody;

    public StringBuilder toStringBuilder() {
        if (skip) return EMPTY_STRING_BUILDER;
        return new StringBuilder()
                .append(parsedAnnotations)
                .append(modifiersIfNeed())
                .append(parsedGenericDeclaration)
                .append(parsedReturnValue)
                .append(' ')
                .append(name)
                .append('(')
                .append(createArgs())
                .append(')')
                .append(parsedExceiptions)
                .append(parsedBody)
                .append('\n');
    }

    private StringBuilder modifiersIfNeed() {
        if (parent == ObjectType.CLASS) {
            return parsedModifiers;
        } else return EMPTY_STRING_BUILDER;
    }
    private StringBuilder createArgs() {
        if (parsedArgs.length == 0 ) return EMPTY_STRING_BUILDER;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parsedArgs.length; i++) {
            sb.append(parsedArgs[i]).append(',').append(' ');
        }
        sb.setLength(sb.length() - 2);
        return sb;
    }
}
