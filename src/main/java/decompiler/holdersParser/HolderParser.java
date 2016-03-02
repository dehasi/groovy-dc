package decompiler.holdersParser;

import decompiler.classStructure.AnnotationHolder;
import decompiler.classStructure.FieldHolder;
import decompiler.classStructure.HeadHolder;
import decompiler.classStructure.MethodHolder;

public interface HolderParser {
    StringBuilder parseField(FieldHolder fieldHolder);
    StringBuilder parseMethod(MethodHolder methodHolder);
    StringBuilder parseHeader(HeadHolder headHolder);
    StringBuilder parseAnnotation(AnnotationHolder annotationHolder);
}
