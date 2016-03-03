package decompiler.visitors;

import org.objectweb.asm.signature.SignatureVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rafa on 27.02.2016.
 */
public class SVisitor extends AbstactVisitor implements SignatureVisitor {
    protected Map<String, List<String>> signatureMap = new HashMap<>();

    public static final String formalTypeParameter = "formalTypeParameter";
    public static final String typeVariable = "typeVariable";

    @Override
    public void visitFormalTypeParameter(String name) {
        putToMap(formalTypeParameter, name);
        System.out.println("formalTypeParameter| name:" + name );
    }

    @Override
    public SignatureVisitor visitClassBound() {
        return new SVisitor();
    }

    @Override
    public SignatureVisitor visitInterfaceBound() {
        return new SVisitor();
    }

    @Override
    public SignatureVisitor visitSuperclass() {
        return new SVisitor();
    }

    @Override
    public SignatureVisitor visitInterface() {
        return new SVisitor();
    }

    @Override
    public SignatureVisitor visitParameterType() {
        return new SVisitor();
    }

    @Override
    public SignatureVisitor visitReturnType() {
        return new SVisitor();
    }

    @Override
    public SignatureVisitor visitExceptionType() {
        return new SVisitor();
    }

    @Override
    public void visitBaseType(char descriptor) {
        putToMap("visitBaseType", String.valueOf(descriptor));
        System.out.println("visitBaseType| descriptor:" + descriptor);
    }

    @Override
    public void visitTypeVariable(String name) {
        putToMap(typeVariable, name);
        System.out.println("typeVariable| name:" + name );
    }

    @Override
    public SignatureVisitor visitArrayType() {
        return new SVisitor();
    }

    @Override
    public void visitClassType(String name) {
        String classType = "сlassType";
        putToMap(classType, name);
        System.out.println("visitClassType| name:" + name );

    }

    @Override
    public void visitInnerClassType(String name) {
        putToMap("visitInnerClassType", name);
        System.out.println("visitInnerClassType| name:" + name );
    }

    @Override
    public void visitTypeArgument() {

    }

    @Override
    public SignatureVisitor visitTypeArgument(char wildcard) {
        putToMap("visitTypeArgument", String.valueOf(wildcard));
        System.out.println("visitTypeArgument| wildcard:" + wildcard);
        return new SVisitor();
    }

    @Override
    public void visitEnd() {

    }
    public Map<String, List<String>> getSignatureMap() {
        return signatureMap;
    }
    protected void putToMap(String key, String value) {
        if (!signatureMap.containsKey(key)) {
            signatureMap.put(key, new ArrayList<>());
        }
        signatureMap.get(key).add(value);
    }
}
