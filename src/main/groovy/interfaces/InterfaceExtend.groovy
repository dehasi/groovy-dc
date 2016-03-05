package interfaces

import org.codehaus.groovy.runtime.GStringImpl

interface InterfaceExtend  extends EmptyInterface{
    def gs = new GStringImpl()
}