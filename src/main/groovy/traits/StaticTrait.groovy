package traits

trait StaticTrait <T> {
    static T field;
    static final T field2;
    static T impl() {null}
    abstract static T nimpl();
}