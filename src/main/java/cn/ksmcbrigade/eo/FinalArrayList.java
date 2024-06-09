package cn.ksmcbrigade.eo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.UnaryOperator;

public final class FinalArrayList<T> extends ArrayList<T> {
    @Override
    public boolean remove(Object o) {
        if(!getPacket().contains(EntityProtection.class.getPackageName())) return false;
        System.out.println("Change!");
        return super.remove(o);
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if(!getPacket().contains(EntityProtection.class.getPackageName())) return;
        System.out.println("Change!");
        super.removeRange(fromIndex, toIndex);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if(!getPacket().contains(EntityProtection.class.getPackageName())) return false;
        System.out.println("Change!");
        return super.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        if(!getPacket().contains(EntityProtection.class.getPackageName())) return;
        System.out.println("Change!");
        super.replaceAll(operator);
    }

    @Override
    public void clear() {
        if(!getPacket().contains(EntityProtection.class.getPackageName())) return;
        System.out.println("Change!");
        super.clear();
    }

    protected String getPacket(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String callerClassName = null;
        for (int i = 1; i < stackTraceElements.length; i++) {
            String className = stackTraceElements[i].getClassName();
            if (!className.startsWith("java.") && !className.startsWith("sun.") && !className.startsWith("jdk.")) {
                callerClassName = className;
                break;
            }
        }
        if (callerClassName == null) {
            return "Unknown";
        }
        String packageName = callerClassName.replaceAll("\\$.*", "");
        return packageName.substring(0, packageName.lastIndexOf('.'));
    }
}
