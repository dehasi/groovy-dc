package interfaces

import org.codehaus.groovy.runtime.GStringImpl

interface FullInterface {
    def field = new GStringImpl()
    int anInt = 42
    Integer anInteger
    Object object
    def noArgsMehot()
    def oneArgMethid(alpha)
    void voidMethod() throws Exception, RuntimeException
}
