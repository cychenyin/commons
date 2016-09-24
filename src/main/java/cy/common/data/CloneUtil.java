
package cy.common.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CloneUtil {

    /**
     * copy properties from object to object; simulate to clone, but what the different is object exists before copy.
     *
     * @param from be copied
     * @param to copy to
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void copy(Object from, Object to) {
        Class fromType = from.getClass();
        Class toType = to.getClass();

        Field[] fromFields = fromType.getDeclaredFields();
        if (fromType.equals(toType) || fromType.isAssignableFrom(toType)) {
            try {
                for (Field field : fromFields) {

                    String fieldName = field.getName();
                    String stringLetter = fieldName.substring(0, 1).toUpperCase();

                    // 获得相应属性的getXXX和setXXX方法名称
                    String getName = "get" + stringLetter + fieldName.substring(1);
                    String setName = "set" + stringLetter + fieldName.substring(1);

                    // 获取相应的方法
                    Method getMethod = fromType.getMethod(getName, new Class[] {});
                    Method setMethod = toType.getMethod(setName, new Class[] { field.getType() });
                    // 调用源对象的getXXX（）方法
                    Object value = getMethod.invoke(from, new Object[] {});
                    // System.out.println(fieldName + " :" + value);

                    // 调用拷贝对象的setXXX（）方法
                    setMethod.invoke(to, new Object[] { value });
                }
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 在这个类里面存在有copy（）方法，根据指定的方法的参数去 构造一个新的对象的拷贝 并将他返回
     *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object deepClone(Object obj)
                    throws IllegalArgumentException, SecurityException, InstantiationException,
                    IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        // 获得对象的类型
        Class classType = obj.getClass();
        // System.out.println("该对象的类型是："+classType.toString());

        // 通过默认构造方法去创建一个新的对象，getConstructor的视其参数决定调用哪个构造方法
        Object objectCopy = classType.getConstructor(new Class[] {}).newInstance(new Object[] {});

        // 获得对象的所有属性
        Field[] fields = classType.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            // 获取数组中对应的属性
            Field field = fields[i];

            String fieldName = field.getName();
            String stringLetter = fieldName.substring(0, 1).toUpperCase();

            // 获得相应属性的getXXX和setXXX方法名称
            String getName = "get" + stringLetter + fieldName.substring(1);
            String setName = "set" + stringLetter + fieldName.substring(1);

            // 获取相应的方法
            Method getMethod = classType.getMethod(getName, new Class[] {});
            Method setMethod = classType.getMethod(setName, new Class[] { field.getType() });

            // 调用源对象的getXXX（）方法
            Object value = getMethod.invoke(obj, new Object[] {});
            // System.out.println(fieldName + " :" + value);

            // 调用拷贝对象的setXXX（）方法
            setMethod.invoke(objectCopy, new Object[] { value });
        }
        return objectCopy;
    }

}
