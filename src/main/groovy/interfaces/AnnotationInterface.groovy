package interfaces

import groovy.transform.NotYetImplemented
//@Resource
//@XmlRootElement
//@SimpleAnnotation(n = "SimpleAnnotationName", v= "42")
interface AnnotationInterface {
//    @XmlID
    def field

    @NotYetImplemented
    def method (
//            @NotNull
            def arg)
}
