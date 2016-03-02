package decompiler.converters;

import decompiler.classStructure.ClassHolder;
import decompiler.classStructure.FieldHolder;
import decompiler.classStructure.MethodHolder;
import decompiler.holdersParser.HolderParser;
import decompiler.holdersParser.HolderParserImpl;

/**
 * Created by Rafa on 02.03.2016.
 */
public class ClassHolder2StringBuilderConverter {

    private HolderParser parser = new HolderParserImpl();

    public StringBuilder toDestination(ClassHolder classHolder) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(parser.parseHeader(classHolder.getHead()));

        for (FieldHolder field : classHolder.getFields()) {
            stringBuilder.append(parser.parseField(field));
        }
        for (MethodHolder method : classHolder.getMethods()) {
            stringBuilder.append(parser.parseMethod(method));
        }

        return stringBuilder.append("}");
    }

    public ClassHolder toSource(StringBuilder stringBuilder) {
        throw new UnsupportedOperationException("there is not toSource converter yey");
    }
}
