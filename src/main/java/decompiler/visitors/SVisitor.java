package decompiler.visitors;

import org.objectweb.asm.signature.SignatureVisitor;

/**
 * Created by Rafa on 27.02.2016.
 */
public class SVisitor extends AbstactVisitor implements SignatureVisitor {
    @Override
    public void visitFormalTypeParameter(String name) {
        putToMap("visitFormalTypeParameter", name);
        System.out.println("visitFormalTypeParameter| name:" + name );
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
        putToMap("visitTypeVariable", name);
        System.out.println("visitTypeVariable| name:" + name );
    }

    @Override
    public SignatureVisitor visitArrayType() {
        return new SVisitor();
    }

    @Override
    public void visitClassType(String name) {
        putToMap("visitClassType", name);
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

}
