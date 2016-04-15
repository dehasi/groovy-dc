package traits


trait FullTrait {
    int t;

    void implementedFunc() {
        int s = 42;
        ++s;
        return;
    }
    abstract void notImplementedFunc();

    private def whoAmI() { this }
}