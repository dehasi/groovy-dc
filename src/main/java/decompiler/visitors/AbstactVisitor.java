package decompiler.visitors;

public abstract class AbstactVisitor {
    protected StringBuilder buffer = new StringBuilder();
    public StringBuilder getBuffer() {
        return buffer;
    }
}
