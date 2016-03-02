package decompiler.holdersParser;

import decompiler.ASMParser;
import decompiler.ObjectType;
import decompiler.ParserUtils;
import decompiler.classStructure.AnnotationHolder;
import decompiler.classStructure.FieldHolder;
import decompiler.classStructure.HeadHolder;
import decompiler.classStructure.MethodHolder;


public class HolderParserImpl implements  HolderParser {

    ASMParser asmParser;
    @Override
    public StringBuilder parseField(FieldHolder fieldHolder) {
        return null;
    }

    @Override
    public StringBuilder parseMethod(MethodHolder methodHolder) {
        return null;
    }

    @Override
    public StringBuilder parseHeader(HeadHolder headHolder) {
        ObjectType type = ParserUtils.getType(headHolder.getAccess());
        asmParser = ParserUtils.getParser(headHolder.getAccess());

        return asmParser.parseHeader(headHolder.getVersion(),
                headHolder.getAccess(),headHolder.getName(),headHolder.getSignature(),
                headHolder.getSuperName(),headHolder.getInterfaces());
    }

    @Override
    public StringBuilder parseAnnotation(AnnotationHolder annotationHolder) {
        return null;
    }
}
