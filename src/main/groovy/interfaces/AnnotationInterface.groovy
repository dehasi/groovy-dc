package interfaces

import com.sun.istack.internal.NotNull
import groovy.transform.NotYetImplemented

import javax.annotation.Resource
import javax.xml.bind.annotation.XmlID
import javax.xml.bind.annotation.XmlRootElement

@Resource
@XmlRootElement
interface AnnotationInterface {
    @XmlID
    def field

    @NotYetImplemented
    def method (@NotNull def arg)
}
