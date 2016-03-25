package decompiler.utils;


import decompiler.visitors.AVisitor;

public class AVisitorEntry {
    public  StringBuilder name;
    public AVisitor aVisitor;

    public AVisitorEntry(StringBuilder name, AVisitor aVisitor) {
        this.name = name;
        this.aVisitor = aVisitor;
    }

    public AVisitorEntry() {
    }
}
