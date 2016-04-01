package interfaces

import org.codehaus.groovy.runtime.GStringImpl

interface FullInterface {
    def field = new GStringImpl()
    int anInt = 42
    int[] anArray
    Integer anInteger
    Object object
    def noArgsMehot()
    def oneArgMethod(alpha)
    def arrayMethod(int[] alpha)
    void voidMethod() throws Exception, RuntimeException
}
