package decompiler.visitors;

import org.objectweb.asm.AnnotationVisitor;

import java.util.ArrayList;
import java.util.List;

public class AVisitor extends AnnotationVisitor {
    List<String> bf = new ArrayList<>();
    public AVisitor(int api) {
        super(api);
    }

    public AVisitor(int api, AnnotationVisitor av) {
        super(api, av);
    }

    @Override
    public void visit(String name, Object value) {
        String x = "visit| name : " + name + " value" + value;
//        System.err.println(x);
        //TODO value has diff types
        bf.add(name + " = " + parseValue(value)) ;
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        System.err.println("visitEnum|  name: "  + name  + "desc: " + desc + " value" + value);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        System.err.println("visitAnnotation| name: "  + name  + "desc: " + desc);
        return this;
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        System.err.println("visitArray| name : " + name);
        return this;
    }

    @Override
    public void visitEnd() {

    }

    public List<String> getBf() {
        return bf;
    }

    private String parseValue (Object val) {
        if (val.getClass() == String.class) {
            return "\"" + val + "\"";
        }
        return val + ""; //FACEPALM
    }
}
