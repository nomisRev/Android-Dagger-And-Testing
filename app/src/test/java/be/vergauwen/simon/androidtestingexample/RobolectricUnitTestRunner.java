package be.vergauwen.simon.androidtestingexample;

//public class CustomTestRunner extends RobolectricTestRunner {
//
//    public CustomTestRunner(Class<?> testClass) throws InitializationError {
//        super(testClass);
//    }
//
//    @Override
//    protected AndroidManifest getAppManifest(Config config) {
//        String appRoot = "../path/to/app/src/main/";
//        String manifestPath = appRoot + "AndroidManifest.xml";
//        String resDir = appRoot + "res";
//        String assetsDir = appRoot + "assets";
//        AndroidManifest manifest = createAppManifest(Fs.fileFromPath(manifestPath),
//            Fs.fileFromPath(resDir),
//            Fs.fileFromPath(assetsDir));
//
//        manifest.setPackageName("com.my.package.name");
//        // Robolectric is already going to look in the  'app' dir ...
//        // so no need to add to package name
//        return manifest;
//    }
//}