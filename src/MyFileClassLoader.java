import javafx.scene.control.cell.TextFieldListCell;

import java.io.*;

/**
 * @ClassName: MyFileClassLoader
 * @Description: 1.继承ClassLoader方法
 *               2.覆盖findClass方法
 * @Author: qulingxiao
 * @Date: 2020/8/16 16:09
 * @Version: v1.0
 */
public class MyFileClassLoader extends ClassLoader{

    private String directory;

    /**
     * 指定文件目录
     * @param dir
     */
    public MyFileClassLoader(String dir){
        directory = dir;
    }

    /**
     * 指定文件目录和父类加载器
     * @param dir
     * @param parent
     */
    public MyFileClassLoader(String dir, ClassLoader parent){
        super(parent);
        directory = dir;
    }

    /**
     * 1.类名转化为目录
     *
     * @param name
     * @return
     */
    protected Class<?> findClass(String name) {
        //类名转化为目录
        String file = directory + File.separator + name.replace(".", File.separator) + ".class";
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        //构建输入流
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //构建字节输出流
        baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while (true) {
            try {
                if (!((len = in.read(buffer)) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            baos.write(buffer, 0, len);
        }

        //取到字节码的二进制数据
        byte data[] = baos.toByteArray();
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, data, 0, data.length);
    }

    public static void main(String[] args) throws Exception {
        MyFileClassLoader classLoader = new MyFileClassLoader("d:/");
        Class clazz = classLoader.loadClass("com.myClassLoader.Demo");
        clazz.newInstance();
    }
}
