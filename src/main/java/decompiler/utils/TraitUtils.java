package decompiler.utils;


import decompiler.holders.ClassHolder;
import decompiler.holders.FieldHolder;

import static decompiler.utils.ParserUtils.getNameWithoutPackage;

public class TraitUtils {

    public static ClassHolder processTrait(ClassHolder trait ){
        extractFields(trait);


        return trait;
    }

    private static void extractFields(ClassHolder trait) {
        ClassHolder fieldHelper = getFieldHelper(trait);
        if (fieldHelper == null) return;
        for (FieldHolder f : fieldHelper.fields) {
            f.field.replace(f.field.indexOf("$"),f.field.indexOf("__")+2, "");
            trait.fields.add(f);
        }

    }

    private static String clearName(String fieldName, String traitName) {
        traitName = getNameWithoutPackage(traitName);
        fieldName = fieldName.substring(fieldName.indexOf(traitName) + traitName.length()+2);
        return fieldName;
    }

    private static ClassHolder getFieldHelper(ClassHolder trait) {
        ClassHolder fieldHelper = null;
        String targetName = getNameWithoutPackage(trait.header.name);
        for (ClassHolder ch : trait.inner) {
            if (ch.header.name.contains(targetName + "$Trait$FieldHelper")){
                fieldHelper = ch;
                break;
            }

        }
        return fieldHelper;
    }
}
