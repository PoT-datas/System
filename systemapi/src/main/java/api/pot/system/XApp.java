package api.pot.system;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

public class XApp {
    private static XApp xApp;
    private static Context context;

    private static Handler uiHandler;

    public XApp(Context context) {
        this.context = context;
    }

    public static XApp with(Context cxt){
        if(xApp==null) xApp = new XApp(cxt);
        context = cxt;
        uiHandler = new Handler();
        return xApp;
    }

    public static XApp killApp() {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        return xApp;
    }

    public static XApp leaveApp(boolean kill) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);

        //and kill
        if(kill) killApp();

        return xApp;
    }

    public static void removeCallbacksAndMessages(Object token){}
    public static void removeCallbacks(Runnable runnable){}

    public static boolean run(Runnable runnable){
        return run(0, runnable);
    }

    public static boolean run(long delais, long period, Runnable... runnables){
        if(uiHandler==null||runnables==null||runnables.length==0) return false;
        int k=0;
        for(Runnable runnable : runnables){
            run(delais+k*period, runnable);
            k++;
        }
        return true;
    }

    public static boolean run(long delais, Runnable runnable){
        try {
            if(uiHandler==null) return false;
            uiHandler.postDelayed(runnable, delais);
            return true;
        }catch (Exception e){}
        return false;
    }

    public static void runThread(Runnable runnable){
        new Thread(runnable).start();
    }

    public static void runThread(final long delais, final Runnable runnable){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sleep(delais);
                new Thread(runnable).start();
            }
        });
    }

    public static void sleep(long duration){
        try {
            Thread.sleep(duration);
        } catch (Exception e) {}
    }
}
