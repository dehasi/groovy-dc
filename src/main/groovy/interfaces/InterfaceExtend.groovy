package interfaces

import org.codehaus.groovy.runtime.GStringImpl

/**
 * Created by Rafa on 05.03.2016.
 */
interface InterfaceExtend  extends EmptyInterface{
    def gs = new GStringImpl()
}