import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @ClassName: ClassLoaderDemo1
 * @Description: TODO
 * @Author: qulingxiao
 * @Date: 2020/8/16 15:38
 * @Version: v1.0
 */
public class ClassLoaderDemo1 {

    public static void main(String[] args) throws Exception {
        File file = new File("d:/");
        URI uri = file.toURI();
        URL url = uri.toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});

        System.out.println("父类加载器："+ classLoader.getParent());

        Class<?> aClass = classLoader.loadClass("com.myClassLoader.Demo");
//        Class<?> bClass = classLoader.loadClass("idea_workspace.myclassloader.Demo.myClassLoader.Demo");
        Object o = aClass.newInstance();
    }
}
