package decompiler.visitors;

public class AbstractParser {

    StringBuilder parseInterfaceField (int access, String name, String desc, String signature, Object value) {
        StringBuilder sb = new StringBuilder();

        sb.append(name);
        return sb;
    }

    StringBuilder parseValue(Object value) {
        StringBuilder sb = new StringBuilder();
        if (value == null) {
            return sb;
        }
        return sb;
    }
}
