package decompiler.visitors;

import org.objectweb.asm.signature.SignatureVisitor;

/**
 * Created by Rafa on 27.02.2016.
 */
public class SVisitor implements SignatureVisitor {
    @Override
    public void visitFormalTypeParameter(String s) {

    }

    @Override
    public SignatureVisitor visitClassBound() {
        return null;
    }

    @Override
    public SignatureVisitor visitInterfaceBound() {
        return null;
    }

    @Override
    public SignatureVisitor visitSuperclass() {
        return null;
    }

    @Override
    public SignatureVisitor visitInterface() {
        return null;
    }

    @Override
    public SignatureVisitor visitParameterType() {
        return null;
    }

    @Override
    public SignatureVisitor visitReturnType() {
        return null;
    }

    @Override
    public SignatureVisitor visitExceptionType() {
        return null;
    }

    @Override
    public void visitBaseType(char c) {

    }

    @Override
    public void visitTypeVariable(String s) {

    }

    @Override
    public SignatureVisitor visitArrayType() {
        return null;
    }

    @Override
    public void visitClassType(String s) {

    }

    @Override
    public void visitInnerClassType(String s) {

    }

    @Override
    public void visitTypeArgument() {

    }

    @Override
    public SignatureVisitor visitTypeArgument(char c) {
        return null;
    }

    @Override
    public void visitEnd() {

    }
}
