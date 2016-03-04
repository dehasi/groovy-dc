package decompiler.visitors;

import org.objectweb.asm.signature.SignatureVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SVisitor extends AbstactVisitor implements SignatureVisitor {

    public SVisitor(Map<String, List<String>> signatureMap) {
        this.signatureMap = signatureMap;
    }

    protected Map<String, List<String>> signatureMap;

    public static final String formalTypeParameter = "formalTypeParameter";
    public static final String typeVariable = "typeVariable";
    public static final String classType = "сlassType";
    public static final String typeArgument = "typeArgument";
    public static final String innerClassType = "innerClassType";
    public static final String baseType = "baseType";

    @Override
    public void visitFormalTypeParameter(String name) {
        putToMap(formalTypeParameter, name);
    }

    @Override
    public SignatureVisitor visitClassBound() {
        return this;
    }

    @Override
    public SignatureVisitor visitInterfaceBound() {
            return this;
    }

    @Override
    public SignatureVisitor visitSuperclass() {
        return this;
    }

    @Override
    public SignatureVisitor visitInterface() {
        return this;
    }

    @Override
    public SignatureVisitor visitParameterType() {
        return this;
    }

    @Override
    public SignatureVisitor visitReturnType() {
        return this;
    }

    @Override
    public SignatureVisitor visitExceptionType() {
        return this;
    }

    @Override
    public void visitBaseType(char descriptor) {
        putToMap(baseType, String.valueOf(descriptor));
    }

    @Override
    public void visitTypeVariable(String name) {
        putToMap(typeVariable, name);
    }

    @Override
    public SignatureVisitor visitArrayType() {
        return this;
    }

    @Override
    public void visitClassType(String name) {
        putToMap(classType, name);
        System.out.println("visitClassType| name:" + name );
    }

    @Override
    public void visitInnerClassType(String name) {
        putToMap(innerClassType, name);
        System.out.println("visitInnerClassType| name:" + name );
    }

    @Override
    public void visitTypeArgument() {

    }

    @Override
    public SignatureVisitor visitTypeArgument(char wildcard) {
        putToMap(typeArgument, String.valueOf(wildcard));
        System.out.println("visitTypeArgument| wildcard:" + wildcard);
        return this;
    }

    @Override
    public void visitEnd() {

    }
    public Map<String, List<String>> getSignatureMap() {
        return signatureMap;
    }

    private void putToMap(String key, String value) {
        if (!signatureMap.containsKey(key)) {
            signatureMap.put(key, new ArrayList<>());
        }
        signatureMap.get(key).add(value);
    }
}
